/**
 * A Relic is a special type of BattleModifier that is always in effect when
 * it is equipped. Relics are not activated by the player, but provide passive
 * bonuses or effects during battle. Examples would be a "Ring of Strength" or
 * "Amulet of Protection" that increase attack or defense power, respectively.
 * Relics are automatically applied when equipped and do not require activation.
 */
public abstract class Relic extends BattleModifier {

    /**
     * Create a new Relic with the given name, description, and maximum uses.
     * @param name The name of the relic.
     * @param description The description of the relic.
     * @param maxUses The maximum number of uses for this relic.
     */
    public Relic(String name, String description, int maxUses) {
        super(name, description, maxUses);
    }

    /**
     * Create a new Relic with the given name and description.
     * @param name The name of the relic.
     * @param description The description of the relic.
     */
    public Relic(String name, String description) {
        super(name, description, 1);
    }
}
