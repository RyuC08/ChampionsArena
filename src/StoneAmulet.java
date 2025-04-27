/**
 * A relic that reduces all incoming damage by 2.
 */
public class StoneAmulet extends Relic {
    /**
     * Constructor for the Stone Amulet relic.
     * This relic reduces all incoming damage by 2.
     */
    public StoneAmulet() {
        super("Stone Amulet", "Reduces all incoming damage by 2.");
    }

    /**
     * Modifies the damage taken by the wielder.
     * This method reduces the base damage by 2.
     * @param baseDamage The base damage before modification.
     * @param context The context of the battle, including the wielder and enemy.
     * @return The modified damage after applying this relic's effect.
     */
    @Override
    public int modifyDefense(int baseDamage, BattleContext context) {
        return baseDamage - 2;
    }
}
