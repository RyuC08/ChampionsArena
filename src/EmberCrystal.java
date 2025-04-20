/**
 * A relic that increases damage dealt to an opponent.
 */
public class EmberCrystal extends Relic {
    public EmberCrystal() {
        super("Ember Crystal", "Adds +2 damage to all outgoing attacks.");
    }

    @Override
    public int modifyAttack(int baseDamage, BattleContext context) {
        return baseDamage + 2;
    }
}
