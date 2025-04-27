/**
 * A gambit that heals the bearer for 10 HP and boosts their damage for 2 turns.
 */
public class AdrenalSurge extends Gambit {
    /**
     * Constructor for the AdrenalSurge gambit.
     * This gambit heals the wielder for 10 HP and boosts their damage for 2 turns.
     */
    public AdrenalSurge() {
        super("Adrenal Surge", "Heals 10 HP and boosts damage for 2 turns.", 1);
    }

    /**
     * Executes the AdrenalSurge gambit.
     * This gambit heals the wielder for 10 HP and applies a damage boost effect.
     * @param context The context of the battle, including the wielder and enemy.
     */
    @Override
    public void activate(BattleContext context) {
        super.activate(context);

        final int healed = context.wielder.heal(10, context);
        context.getLog().addEntry(
            context.wielder, null, getName(),
            context.wielder.getName() + " surges with adrenaline and heals " + healed + " HP!",
            context.round, BattleLog.EntryType.STATUS
        );

        context.wielder.getLoadout().addTemporaryModifier(new SurgeBuff());
    }
}

/**
 * A temporary modifier that adds +2 damage for 2 turns.
 */
class SurgeBuff extends TemporaryModifier {
    public SurgeBuff() {
        super("Surge Buff", "Adds +2 damage for 2 turns.", 2);
    }

    @Override
    public int modifyAttack(int baseDamage, BattleContext context) {
        return baseDamage + 2;
    }
}
