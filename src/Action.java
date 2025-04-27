/**
 * An abstract class representing an action that can be performed in a battle.
 * This class provides a structure for defining various actions, including
 * basic attacks, defensive maneuvers, and chargeable attacks.
 * Each action has a name, a charge status, and a number of turns required to charge.
 */
public abstract class Action {
    private final String name; // Name of the action
    private final boolean isChargeable; // Indicates if the action is chargeable
    private final int chargeTurns;  // Number of turns required to charge

    /**
     * Construct an Action with a name. This action does not require charging.
     * @param name The name of the action
     */
    public Action(String name) {
        this(name, 0);
    }

    /**
     * Construct an Action with a name and the number of turns required to charge.
     * @param name The name of the action
     * @param chargeTurns The number of turns required to charge this action before it
     *                    is executed (on the following turn).
     *                    A value of 0 indicates that the action does not require
     *                    charging.
     */
    public Action(String name, int chargeTurns) {
        this.name = name;
        this.isChargeable = chargeTurns > 0;
        this.chargeTurns = chargeTurns;
    }

    /**
     * Get the name of this action.
     * @return The name of this action.
     */
    public final String getName() {
        return name;
    }

    /**
     * Check if this action requires charging before it will be executed
     * @return true if this action requires charging before it will be executed.
     */
    public final boolean needsCharging() {
        return isChargeable;
    }

    /**
     * Get the number of turns required to charge this action before it will be
     * @return The number of turns required to charge this action before it will be
     *          executed.
     */
    public final int getChargeTurns() {
        return chargeTurns;
    }

    /**
     * Executes the action in the context of a battle.
     * This method must be overridden by subclasses to define the specific
     * behavior of the action.
     * @param context The context of the battle, including the wielder and enemy.
     *                This context provides information about the current state of
     *                the battle and allows for interaction with the battle log.
     *                The context is passed to the action to allow it to modify
     *                the battle state, such as applying damage or effects to
     *                the wielder or enemy.
     */
    public abstract void execute(BattleContext context);
}
