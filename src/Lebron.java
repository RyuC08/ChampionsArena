import java.util.List;
import java.util.ArrayList;

/**
 * The Lebron class represents a champion that serves as a training dummy for players to practice against.
 * It has a set of actions that simulate a real battle scenario.
 * The actions include a Dunk and Block action,
 * and a Ice action that heals the champion.
 * The Lebron has a max health of 60, an attack power of 6, and a defense power of 4.
 */
public class Lebron extends Champion {
    /**
     * The name of the Lebron champion.
     */
    public static final String NAME = "Lebron";

    /**
     * Constructor for Lebron.
     * Lebron has a max health of 60, an attack power of 6, and a defense power of 4.
     */
    public Lebron() {
        super(NAME, 6, 4, 60);
    }

    /**
     * Returns a list of actions that the Lebron can perform.
     * The actions include a Dunk and Block action,
     * and a Ice action that heals the champion.
     * @return A list of actions available to the Lebron.
     */
    @Override
    public List<Action> getActions() {
        // Start with the default Champion actions (like PlayGambit)
        List<Action> actions = new ArrayList<>(super.getActions());

        // Add this Champion's custom actions
        actions.add(new Dunk());
        actions.add(new Block());
        actions.add(new Ice());

        return actions;
    }
}

/**
 * A Dunk that deals 23 damage after a 1 round charge.
 */
class Dunk extends Action {
    public Dunk() {
        super("Dunk", 1);
    }

    /**
     * Executes the Dunk action.
     * This action charges for a turn but deals 23 damage to the opponent.
     * @param context The context of the battle, including the wielder and enemy.
     */
    @Override
    public void execute(BattleContext context) {
        final int base = 23;
        final int actual = context.enemy.takeDamage(base, context);

        context.getLog().addEntry(
            context.wielder, context.enemy, getName(),
            context.wielder.getName() + " posterizes " + enemy.getName() + " for " + actual + " damage!",
            context.round, BattleLog.EntryType.ACTION
        );
    }
}

/**
 * A Block action that deals a instant 11 damage.
 */
class Block extends Action {
    public Block() {
        super("Block");
    }

    /*
     * This method deflects all damage for 1 turn.
     * It is called when the wielder is defending against an attack.
     * @param context The context of the battle, including the wielder and enemy.
     * @return The modified damage after blocking the attack.
     */
    @Override
    public int modifyDefense(BattleContext context) {
        return 0;
    }
}

/**
 * A sample action that Lebron can perform -- Using ice heals the champion.
 */
class Ice extends Action {
    public Ice() {
        super("Ice");
    }

    /**
     * Executes the Ice action.
     * This action heals the champion for a fixed amount of health.
     * @param context The context of the battle, including the wielder and enemy.
     */
    @Override
    public void execute(BattleContext context) {
        final int baseHealing = 10;
        final int actualHealed = context.wielder.heal(baseHealing, context);

        context.getLog().addEntry(
            context.wielder, null, getName(),
            context.wielder.getName() + " takes an ice bath to heal " + actualHealed + " HP.",
            context.round, BattleLog.EntryType.ACTION
        );
    }
}
