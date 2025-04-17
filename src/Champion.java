import java.util.List;

public abstract class Champion {
    private final String name;
    private final int maxHealth;
    private int currentHealth;

    private final int attackPower;
    private final int defensePower;

    private final Loadout loadout;
    private final Arsenal arsenal;

    private Action lockedInAction;
    private boolean isCharging;
    private int chargeTurnsRemaining;
    private static final int MAX_STAT_TOTAL = 10;

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

    // --- Abstract methods students must implement ---
    public abstract List<Action> getActions();

    // --- Action selection ---
    public void lockInAction(Action action) {
        if (this.lockedInAction == null) {
            this.lockedInAction = action;
            this.isCharging = false;
            startCharging();
        }
    }

    public Action getLockedInAction() {
        return lockedInAction;
    }

    // --- Damage pipeline ---
    public final int takeDamage(int baseDamage, BattleContext context) {
        int modifiedDamage = baseDamage;
        // Apply attacker's attackPower
        modifiedDamage += context.attacker.getAttackPower();

        // Apply defender's defensePower
        modifiedDamage -= context.defender.getDefensePower();

        // Step 1: Attacker's modifiers modify the damage
        List<BattleModifier> attackerModifiers = context.attacker.getLoadout().getActiveModifiers();
        for (BattleModifier mod : attackerModifiers) {
            if (!mod.isExpired()) {
                int prevDamage = modifiedDamage;
                modifiedDamage = mod.modifyAttack(prevDamage, context);
                if (modifiedDamage != prevDamage) {
                    mod.effectUsed();
                }
            }
        }
    
        // Step 2: Defender's modifiers modify the damage
        List<BattleModifier> defenderModifiers = context.defender.getLoadout().getActiveModifiers();
        for (BattleModifier mod : defenderModifiers) {
            if (!mod.isExpired()) {
                int prevDamage = modifiedDamage;
                modifiedDamage = mod.modifyDefense(prevDamage, context);
                if (modifiedDamage != prevDamage) {
                    mod.effectUsed();
                }
            }
        }
    
        // Step 3: Clamp and apply damage
        int finalDamage = Math.max(1, Math.min(modifiedDamage, 25));
        this.currentHealth -= finalDamage;
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
        }

        return finalDamage;
    }
    

    public final boolean isAlive() {
        return currentHealth > 0;
    }

    // --- Charging Support ---
    public boolean isCharging() {
        return isCharging;
    }

    public void startCharging() {
        if (lockedInAction != null && lockedInAction.needsCharging() && !isCharging) {
            isCharging = true;
            chargeTurnsRemaining = lockedInAction.getChargeTurns();
        }
    }

    public Action advanceCharge() {
        if (chargeTurnsRemaining > 0) {
            chargeTurnsRemaining--;
            System.out.println(getName() + " is charging (" + chargeTurnsRemaining + " turns left)");
            return new ChargingAction();
        }

        Action action = lockedInAction;
        lockedInAction = null;
        isCharging = false;
        chargeTurnsRemaining = 0;
        return action;
    }

    public int getChargeTurnsRemaining() {
        return chargeTurnsRemaining;
    }

    // --- Getters ---
    public String getName() { return name; }
    public int getMaxHealth() { return maxHealth; }
    public int getCurrentHealth() { return currentHealth; }
    public int getAttackPower() { return attackPower; }
    public int getDefensePower() { return defensePower; }
    public Loadout getLoadout() { return loadout; }
    public Arsenal getArsenal() { return arsenal; }

    private class ChargingAction extends Action {
        public ChargingAction() {
            super("Charging");
        }

        @Override
        public void execute(BattleContext context) {
            context.getLog().addEntry(
                context.attacker, null, getName(),
                context.attacker.getName() + " is charging up.",
                context.round, BattleLog.EntryType.ACTION
            );
        }
    }
}
