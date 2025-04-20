/**
 * A tactic that increases damage dealt to an opponent if they are charging.
 */
public class Chargebreaker extends Tactic {
    public Chargebreaker() {
        super("Chargebreaker", "Deals +4 damage if opponent is charging.");
    }

    @Override
    public int modifyAttack(int baseDamage, BattleContext context) {
        if (context.enemy.isCharging()) {
            return baseDamage + 4;
        }
        return baseDamage;
    }
}
