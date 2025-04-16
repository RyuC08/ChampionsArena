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

    public Champion(String name, int attackPower, int defensePower, int maxHealth, ModifierVault vault) {
        this.name = name;
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;

        this.arsenal = new Arsenal(vault);  // Pulls 5 from vault
        this.loadout = new Loadout();
        this.isCharging = false;
        this.chargeTurnsRemaining = 0;
    }

    // --- Abstract methods students must implement ---
    public abstract List<Action> getActions();
    public abstract String getBattleCry();

    // --- Action selection ---
    public void lockInAction(Action action) {
        this.lockedInAction = action;
        this.isCharging = false;
        startCharging();
    }

    public Action getLockedInAction() {
        return lockedInAction;
    }

    // --- Damage pipeline ---
    public final int takeDamage(int baseDamage, BattleContext context) {
        int modifiedDamage = baseDamage;
    
        // Step 1: Attacker's modifiers modify the damage
        List<BattleModifier> attackerModifiers = context.attacker.getLoadout().getActiveModifiers();
        for (BattleModifier mod : attackerModifiers) {
            modifiedDamage = mod.modifyAttack(modifiedDamage, context);
        }
    
        // Step 2: Defender's modifiers modify the damage
        List<BattleModifier> defenderModifiers = context.defender.getLoadout().getActiveModifiers();
        for (BattleModifier mod : defenderModifiers) {
            modifiedDamage = mod.modifyDefense(modifiedDamage, context);
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
        if (this.lockedInAction.needsCharging() && !isCharging) {
            isCharging = true;
            chargeTurnsRemaining = this.lockedInAction.getChargeTurns();
        }
    }

    public void advanceCharge() {
        if (isCharging) {
            chargeTurnsRemaining--;
            if (chargeTurnsRemaining <= 0) {
                isCharging = false;
            }
        }
    }

    // --- Getters ---
    public String getName() { return name; }
    public int getCurrentHealth() { return currentHealth; }
    public int getAttackPower() { return attackPower; }
    public int getDefensePower() { return defensePower; }
    public Loadout getLoadout() { return loadout; }
    public Arsenal getArsenal() { return arsenal; }
}
