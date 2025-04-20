/**
 * A relic that reduces all incoming damage by 2.
 */
public class StoneAmulet extends Relic {
    public StoneAmulet() {
        super("Stone Amulet", "Reduces all incoming damage by 2.");
    }

    @Override
    public int modifyDefense(int baseDamage, BattleContext context) {
        return baseDamage - 2;
    }
}
