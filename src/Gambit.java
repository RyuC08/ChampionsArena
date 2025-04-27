/**
 * A Gambit is a special type of BattleModifier that can be activated by the player.
 * It should provide a risk/reward mechanic, where the player can choose to activate it
 * for a potential benefit, but it may also have a cost or drawback. (e.g. "Last Stand" or
 * "Weapon Overload"). Gambits are not automatically applied when equipped, but are "pocketed"
 * and can be activated as the player's action during a turn.
 */
public abstract class Gambit extends BattleModifier {
    private boolean activated = false; // Indicates if the gambit has been activated by the player
    
    /**
     * Create a new Gambit with the given name, description, and duration.
     * @param name The name of the gambit.
     * @param description The description of the gambit.
     * @param duration The duration of the gambit in turns.
     */
    public Gambit(String name, String description, int duration) {
        super(name, description, duration);  // from BattleModifier
    }

    /**
     * Check if the gambit is activated.
     * @return true if the gambit is activated, false otherwise.
     */
    public final boolean isActivated() {
        return activated;
    }

    /**
     * Activate the gambit. This method should be called when the player chooses to use the gambit.
     * A Gambit subclass may have immediate effects after being activated or may modifyAttack/modifyDefense.
     * @param context The context of the battle, including the wielder and enemy.
     */
    public void activate(BattleContext context) {
        if (activated) {
            throw new IllegalStateException("Gambit has already been activated.");
        }
        // Set the gambit as activated
        this.activated = true;
    }
}
