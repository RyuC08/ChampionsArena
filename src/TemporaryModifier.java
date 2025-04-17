/**
 * A temporary battle modifier that can be applied by an action to allow
 * effects to be applied to a character that normally could not be applied
 * by an action, such as shielding.
 * Could also be used to apply a status effect that modifies attack or defense.
 */
public abstract class TemporaryModifier extends BattleModifier {
    /**
     * Constructor for TemporaryModifier with a name and description that lasts
     * for one use.
     * @param name The name of the modifier
     * @param description The description of the modifier
     */
    public TemporaryModifier(String name, String description) {
        super(name, description, 1);
    }

    /**
     * Constructor for TemporaryModifier with a name and description and a set
     * duration.
     * @param name The name of the modifier
     * @param description The description of the modifier
     * @param duration The number of uses for this modifier
     */
    public TemporaryModifier(String name, String description, int duration) {
        super(name, description, duration);
    }
}