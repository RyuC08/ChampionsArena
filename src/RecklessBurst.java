/**
 * A gambit that increases damage dealt to an opponent but reduces the wielder's defense for 2 turns.
 */
public class RecklessBurst extends Gambit {
    /**
     * Constructor for the RecklessBurst gambit.
     * This gambit deals +8 damage this turn and lowers defense by 3 for 2 turns.
     */
    public RecklessBurst() {
        super("Reckless Burst", "Deals +8 damage this turn, then lowers defense by 3 for 2 turns.", 1);
    }

    /**
     * Executes the RecklessBurst gambit.
     * This gambit deals +8 damage to the opponent and applies a defense drop effect.
     * @param context The context of the battle, including the wielder and enemy.
     */
    @Override
    public void activate(BattleContext context) {
        super.activate(context);
        context.wielder.getLoadout().addTemporaryModifier(new DefenseDrop());
    }

    /**
     * This method modifies the damage dealt by the wielder.
     * It is called when the wielder is attacking.
     * @param baseDamage The base damage before modification.
     * @param context The context of the battle, including the wielder and enemy.
     * @return The modified damage after applying this gambit's effect.
     */
    @Override
    public int modifyAttack(int baseDamage, BattleContext context) {
        return baseDamage + 8;
    }
}

/**
 * A temporary modifier that reduces the defense of the wielder after they use a RecklessBurst.
 */
class DefenseDrop extends TemporaryModifier {
    public DefenseDrop() {
        super("Reckless Penalty", "Defense reduced by 3 for 2 turns", 2);
    }

    @Override
    public int modifyDefense(int baseDamage, BattleContext context) {
        return baseDamage + 3; 
    }
}