/**
 * A tactic that doubles healing when the champion's health is below 25%.
 */
public class LastLight extends Tactic {
    public LastLight() {
        super("Last Light", "Doubles healing when below 25% HP.");
    }

    @Override
    public int modifyHealing(int baseHeal, BattleContext context) {
        Champion c = context.wielder;
        if ((double) c.getCurrentHealth() / c.getMaxHealth() <= 0.25) {
            return baseHeal * 2;
        }
        return baseHeal;
    }
}
