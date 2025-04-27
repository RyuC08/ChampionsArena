/**
 * A tactic that doubles healing when the champion's health is below 25%.
 */
public class LastLight extends Tactic {
    /**
     * Constructor for the LastLight tactic.
     * This tactic doubles healing when the champion's health is below 25%.
     */
    public LastLight() {
        super("Last Light", "Doubles healing when below 25% HP.");
    }

    /**
     * Modifies the healing amount based on the champion's current health.
     * If the champion's health is below 25%, the healing is doubled.
     * @param baseHeal The base healing amount before modification.
     * @param context The context of the battle, including the wielder and enemy.
     * @return The modified healing amount after applying this tactic's effect.
     */
    @Override
    public int modifyHealing(int baseHeal, BattleContext context) {
        Champion c = context.wielder;
        if ((double) c.getCurrentHealth() / c.getMaxHealth() <= 0.25) {
            return baseHeal * 2;
        }
        return baseHeal;
    }
}
