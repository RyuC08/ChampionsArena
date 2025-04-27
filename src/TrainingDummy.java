import java.util.List;
import java.util.ArrayList;

/**
 * The TrainingDummy class represents a champion that serves as a training dummy for players to practice against.
 * It has a set of actions that simulate a real battle scenario.
 * The actions include a Headbutt, a Shrug action that does nothing,
 * and a Bandaid action that heals the champion.
 * The TrainingDummy has a max health of 50, an attack power of 5, and a defense power of 5.
 */
public class TrainingDummy extends Champion {
    /**
     * The name of the TrainingDummy champion.
     */
    public static final String NAME = "Training Dummy";

    /**
     * Constructor for TrainingDummy.
     * The dummy has a max health of 50, an attack power of 5, and a defense power of 5.
     */
    public TrainingDummy() {
        super(NAME, 5, 5, 50);
    }

    /**
     * Returns a list of actions that the TrainingDummy can perform.
     * The actions include a Headbutt, a Shrug action that does nothing,
     * and a Bandaid action that heals the champion.
     * @return A list of actions available to the TrainingDummy.
     */
    @Override
    public List<Action> getActions() {
        // Start with the default Champion actions (like PlayGambit)
        List<Action> actions = new ArrayList<>(super.getActions());

        // Add this Champion's custom actions
        actions.add(new Headbutt());
        actions.add(new Shrug());
        actions.add(new Bandaid());

        return actions;
    }
}

/**
 * A sample action that the TrainingDummy can perform -- A headbutt that deals damage.
 */
class Headbutt extends Action {
    public Headbutt() {
        super("Headbutt");
    }

    /**
     * Executes the Headbutt action.
     * This action deals 8 damage to the opponent.
     * @param context The context of the battle, including the wielder and enemy.
     */
    @Override
    public void execute(BattleContext context) {
        final int baseDamage = 8;
        final int actualDamage = context.enemy.takeDamage(baseDamage, context);

        context.getLog().addEntry(
            context.wielder, context.enemy, getName(),
            context.wielder.getName() + " headbutts for " + actualDamage + " damage!",
            context.round, BattleLog.EntryType.ACTION
        );
    }
}

/**
 * A sample action that the TrainingDummy can perform -- A shrug that does nothing.
 */
class Shrug extends Action {
    public Shrug() {
        super("Shrug");
    }

    /**
     * Executes the Shrug action.
     * This action does nothing and logs a message indicating that the champion did nothing.
     * @param context The context of the battle, including the wielder and enemy.
     */
    @Override
    public void execute(BattleContext context) {
        context.getLog().addEntry(
            context.wielder, null, getName(),
            context.wielder.getName() + " shrugs and does nothing.",
            context.round, BattleLog.EntryType.ACTION
        );
    }
}

/**
 * A sample action that the TrainingDummy can perform -- A bandaid that heals the champion.
 */
class Bandaid extends Action {
    public Bandaid() {
        super("Bandaid");
    }

    /**
     * Executes the Bandaid action.
     * This action heals the champion for a fixed amount of health.
     * @param context The context of the battle, including the wielder and enemy.
     */
    @Override
    public void execute(BattleContext context) {
        final int baseHealing = 5;
        final int actualHealed = context.wielder.heal(baseHealing, context);

        context.getLog().addEntry(
            context.wielder, null, getName(),
            context.wielder.getName() + " slaps on a bandaid and heals " + actualHealed + " HP.",
            context.round, BattleLog.EntryType.ACTION
        );
    }
}
