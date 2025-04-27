/**
 * A tactic that increases damage dealt to an opponent if they are charging.
 */
public class Chargebreaker extends Tactic {
    /**
     * Constructor for the Chargebreaker tactic.
     * This tactic deals +4 damage if the opponent is charging.
     */
    public Chargebreaker() {
        super("Chargebreaker", "Deals +4 damage if opponent is charging.");
    }

    /**
     * Modifies the damage dealt by the wielder.
     * This method adds +4 to the base damage if the opponent is charging.
     * @param baseDamage The base damage before modification.
     * @param context The context of the battle, including the wielder and enemy.
     * @return The modified damage after applying this tactic's effect.
     */
    @Override
    public int modifyAttack(int baseDamage, BattleContext context) {
        if (context.enemy.isCharging()) {
            return baseDamage + 4;
        }
        return baseDamage;
    }
}
