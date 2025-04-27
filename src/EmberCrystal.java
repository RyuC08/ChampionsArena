/**
 * A relic that increases damage dealt to an opponent.
 */
public class EmberCrystal extends Relic {
    /**
     * Constructor for the Ember Crystal relic.
     * This relic adds +2 damage to all outgoing attacks.
     */
    public EmberCrystal() {
        super("Ember Crystal", "Adds +2 damage to all outgoing attacks.");
    }

    /**
     * Modifies the damage dealt by the wielder.
     * This method adds +2 to the base damage.
     * @param baseDamage The base damage before modification.
     * @param context The context of the battle, including the wielder and enemy.
     * @return The modified damage after applying this relic's effect.
     */
    @Override
    public int modifyAttack(int baseDamage, BattleContext context) {
        return baseDamage + 2;
    }
}
