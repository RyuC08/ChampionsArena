import java.util.List;

/**
 * A Champion is a player's character in the game.
 * Each Champion has a name, health, attack power, and defense power.
 */
public abstract class Champion {
    private final String name;   // The Champion's name
    
    private final int maxHealth; // The Champion's maximum health
    private int currentHealth;   // The Champion's current health

    private static final int MAX_STAT_TOTAL = 10; // The maximum total of attackPower and defensePower
    private final int attackPower; // The Champion's attack power - adds to damage dealt
    private final int defensePower; // The Champion's defense power - reduces damage taken

    private final Loadout loadout; // The Champion's items that are currently in play
    private final Arsenal arsenal; // Items that can be swapped in and out of the loadout

    private Action lockedInAction; // The action that the Champion has locked in for this turn (or charging for a future turn)
    private boolean isCharging;    // Whether the Champion is currently charging an action
    private int chargeTurnsRemaining; // The number of turns remaining until the charge is complete

    
    /**
     * Create a Champion with the given stats. The champion's maxHealth can be at most 100.
     * The Champion's attackPower and defensePower must add up to at most MAX_STAT_TOTAL (10).
     * 
     * @param name The Champion's name
     * @param attackPower The Champion's attack power
     * @param defensePower The Champion's defense power
     * @param maxHealth The Champion's maximum health
     */
    public Champion(String name, int attackPower, int defensePower, int maxHealth) {
        this.name = name;
        
        if (attackPower + defensePower > MAX_STAT_TOTAL) {
            throw new IllegalArgumentException("Attack + Defense cannot exceed " + MAX_STAT_TOTAL);
        }

        this.attackPower = attackPower;
        this.defensePower = defensePower;

        // Clamp maxHealth to be between 1 and 100
        maxHealth = Math.max(1, Math.min(maxHealth, 100));        
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;

        this.arsenal = new Arsenal();
        this.loadout = new Loadout();
        this.isCharging = false;
        this.chargeTurnsRemaining = 0;
    }

    /**
     * @return The list of actions that this Champion can perform.
     */
    public abstract List<Action> getActions(); 

    /**
     * Set the action that this Champion has locked in to perform this turn or charge for a future turn.
     * If an action is already locked in, this method does nothing.
     */
    public void lockInAction(Action action) {
        if (this.lockedInAction == null) {
            this.lockedInAction = action;
            this.isCharging = false;
            startCharging();
        }
    }

    /** 
     * @return The action that this Champion has locked in to perform this turn or is charging for a future turn.
     */
    public Action getLockedInAction() {
        return lockedInAction;
    }

    /**
     * Apply damage to the Champion's health. The damage is modified by the attacker's attack power
     * and the defender's defense power, and then modified by any active modifiers.
     * The damage is clamped to be between 1 and 25.
     * @param baseDamage The base damage to apply
     * @param context The context of the battle, including the weilder of the attack and enemy the attack is against
     * @return The actual amount of damage applied
     */
    public final int takeDamage(int baseDamage, BattleContext context) {
        int modifiedDamage = baseDamage;

        // Apply attacker's attackPower
        modifiedDamage += context.wielder.getAttackPower();

        // Apply defender's defensePower
        modifiedDamage -= context.enemy.getDefensePower();

        // Step 1: Attacker's modifiers modify the damage
        List<BattleModifier> attackerModifiers = context.wielder.getLoadout().getActiveModifiers();
        for (BattleModifier mod : attackerModifiers) {
            if (!mod.isExpired()) {
                int prevDamage = modifiedDamage;
                modifiedDamage = mod.modifyAttack(prevDamage, context);

                if (modifiedDamage != prevDamage) {
                    mod.effectUsedThisTurn();
                }
            }
        }
    
        // Step 2: Defender's modifiers modify the damage
        List<BattleModifier> defenderModifiers = context.enemy.getLoadout().getActiveModifiers();
        for (BattleModifier mod : defenderModifiers) {
            if (!mod.isExpired()) {
                int prevDamage = modifiedDamage;
                modifiedDamage = mod.modifyDefense(prevDamage, context);

                if (modifiedDamage != prevDamage) {
                    mod.effectUsedThisTurn();
                }
            }
        }
    
        // Step 3: Clamp and apply damage
        final int finalDamage = Math.max(1, Math.min(modifiedDamage, 25));
        this.currentHealth -= finalDamage;
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
        }

        return finalDamage;
    }
    
    /**
     * Apply healing to the Champion's health. The healing is modified by any active modifiers.
     * The healing is clamped to be between 1 and the amount needed to reach maxHealth.
     * @param baseAmount The base amount of healing to apply
     * @param context The context of the battle
     * @return The actual amount of healing applied
     */
    public int heal(int baseAmount, BattleContext context) {
        int modified = baseAmount;
    
        // Apply this champion's modifiers
        for (BattleModifier mod : getLoadout().getActiveModifiers()) {
            int prevAmount = modified;
            modified = mod.modifyHealing(prevAmount, context);

            if (modified != prevAmount) {
                mod.effectUsedThisTurn();
            }
        }
    
        final int actual = Math.min(modified, maxHealth - currentHealth);
        this.currentHealth += actual;
    
        return actual;
    }

    /**
     * This method is used to apply damage directly to the champion's health.
     * It is not affected by any modifiers and should be used with caution - likely
     * just for status effects.
     * @param amount The amount of damage to apply
     * @param context The context of the battle
     * @return The actual amount of damage applied
     */
    public int rawDamage(int amount, BattleContext context) {
        int finalDamage = Math.max(1, Math.min(amount, 25));
        this.currentHealth -= finalDamage;
        if (this.currentHealth < 0) this.currentHealth = 0;
        return finalDamage;
    }
    
    /**
     * @return true if the Champion is alive (currentHealth > 0)
     */
    public final boolean isAlive() {
        return currentHealth > 0;
    }

    /**
     * @return true if the Champion is currently charging an action
     */
    public boolean isCharging() {
        return isCharging;
    }

    /**
     * Start charging the action that is currently locked in.
     * If the action is not chargeable, this method does nothing.
     */
    public void startCharging() {
        if (lockedInAction != null && lockedInAction.needsCharging() && !isCharging) {
            isCharging = true;
            chargeTurnsRemaining = lockedInAction.getChargeTurns();
        }
    }

    /**
     * Advance the charge for the action that is currently locked in. If the action
     * is not chargeable, or is done charging, the action is returned and the champion
     * no longer has a locked in action.
     * @return A null action that indicates the champion is charging up an action.
     */
    public Action advanceCharge() {
        if (chargeTurnsRemaining > 0) {
            chargeTurnsRemaining--;
            return new ChargingAction();
        }

        Action action = lockedInAction;
        lockedInAction = null;
        isCharging = false;
        chargeTurnsRemaining = 0;
        return action;
    }

    /**
     * @return The number of turns remaining until the charge is complete
     */
    public int getChargeTurnsRemaining() {
        return chargeTurnsRemaining;
    }

    /**
     * @return The Champion's name
     */
    public String getName() { return name; }

    /**
     * @return The Champion's maximum health
     */
    public int getMaxHealth() { return maxHealth; }

    /**
     * @return The Champion's current health
     */
    public int getCurrentHealth() { return currentHealth; }
    
    /**
     * @return The Champion's attack power
     */
    public int getAttackPower() { return attackPower; }

    /**
     * @return The Champion's defense power
     */
    public int getDefensePower() { return defensePower; }

    /**
     * @return The Champion's loadout - the items that are currently in play
     */
    public Loadout getLoadout() { return loadout; }
    
    /**
     * @return The Champion's arsenal - the items that can be swapped in and out of the loadout
     */
    public Arsenal getArsenal() { return arsenal; }
}

/**
 * A class representing an action that the champion is currently charging.
 * This action is used to indicate that the champion is charging up for
 * a future attack.
 */
class ChargingAction extends Action {
    public ChargingAction() {
        super("Charging");
    }

    /**
     * Logs the champion is charging up.
     * @param context The context of the battle, including the wielder (this champion) and their enemy.
     */
    @Override
    public void execute(BattleContext context) {
        context.getLog().addEntry(
            context.wielder, null, getName(),
            context.wielder.getName() + " is charging up.",
            context.round, BattleLog.EntryType.ACTION
        );
    }
}
