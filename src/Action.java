public abstract class Action {
    private final String name;
    private final boolean isChargeable;
    private final int chargeTurns;  // Number of turns required to charge

    public Action(String name) {
        this(name, 0);
    }

    public Action(String name, int chargeTurns) {
        this.name = name;
        this.isChargeable = chargeTurns > 0;
        this.chargeTurns = chargeTurns;
    }

    public final String getName() {
        return name;
    }

    public final boolean needsCharging() {
        return isChargeable;
    }

    public final int getChargeTurns() {
        return chargeTurns;
    }

    public abstract void execute(BattleContext context);
}
