/**
 * A Tactic is a special type of BattleModifier that that a player adds to their
 * loadout. It is automatically applied and provides temporary buffs or effects
 * during battle. Tactics are not activated by the player, but are always in effect
 * when equipped. Examples would be a "Shield Wall" or "Battle Cry" that
 * temporarily increase defense or attack power, respectively.
 */
public abstract class Tactic extends BattleModifier {

    /**
     * Create a new Tactic with the given name, description, and maximum uses.
     * @param name The name of the tactic.
     * @param description The description of the tactic.
     * @param maxUses The maximum number of uses for this tactic.
     */
    public Tactic(String name, String description, int maxUses) {
        super(name, description, maxUses);
    }

    /**
     * Create a new Tactic with the given name and description.
     * @param name The name of the tactic.
     * @param description The description of the tactic.
     */
    public Tactic(String name, String description) {
        super(name, description, 1);
    }
}
