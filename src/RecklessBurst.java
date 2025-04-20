/**
 * A gambit that increases damage dealt to an opponent but reduces the wielder's defense for 2 turns.
 */
public class RecklessBurst extends Gambit {
    public RecklessBurst() {
        super("Reckless Burst", "Deals +8 damage this turn, then lowers defense by 3 for 2 turns.", 1);
    }

    @Override
    public void activate(BattleContext context) {
        super.activate(context);
        context.wielder.getLoadout().addTemporaryModifier(new DefenseDrop());
    }

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