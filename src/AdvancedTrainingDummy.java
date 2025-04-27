import java.util.List;
import java.util.ArrayList;

/**
 * AdvancedTrainingDummy is a Champion that serves as a training dummy for players to practice against.
 * It has a set of actions that simulate a real battle scenario.
 * The actions include a Jab, a Brace action that provides a defensive buff,
 * and a Wind-Up Slam that deals significant damage.
 * The AdvancedTrainingDummy has a max health of 60, an attack power of 7, and a defense power of 3.
 * The Jab deals 5 damage, the Brace action provides a temporary defense buff against 1 attack,
 * and the Wind-Up Slam deals 20 damage after charging for 1 round.
 */
public class AdvancedTrainingDummy extends Champion {
    /**
     * The name of the AdvancedTrainingDummy champion.
     */
    public static final String NAME = "Advanced Training Dummy";

    /**
     * Constructor for AdvancedTrainingDummy.
     * The dummy has a max health of 60, an attack power of 7, and a defense power of 3.
     */
    public AdvancedTrainingDummy() {
        // Name, attackPower, defensePower, maxHealth
        super(NAME, 7, 3, 60);
    }

    /**
     * Returns a list of actions that the AdvancedTrainingDummy can perform.
     * The actions include a Jab, a Brace action that provides a defensive buff,
     * and a Wind-Up Slam that deals significant damage.
     * @return A list of actions available to the AdvancedTrainingDummy.
     */
    @Override
    public List<Action> getActions() {
        // Start with the default Champion actions (like PlayGambit)
        List<Action> actions = new ArrayList<>(super.getActions());

        // Add this Champion's custom actions
        actions.add(new Jab());
        actions.add(new Brace());
        actions.add(new WindUpSlam());
        actions.add(new PoisonDart());

        return actions;
    }
}

// Action 1: Jab — deals 5 damage
// This action is a basic attack that deals damage to the opponent.
class Jab extends Action {
    public Jab() {
        super("Jab");
    }

    /**
     * Executes the Jab action.
     * This action deals 5 damage to the opponent.
     * @param context The context of the battle, including the wielder and enemy.
     */
    @Override
    public void execute(BattleContext context) {
        final int baseDamage = 5;
        final int actual = context.enemy.takeDamage(baseDamage, context);
        context.getLog().addEntry(
            context.wielder, context.enemy, getName(),
            context.wielder.getName() + " jabs for " + actual + " damage.",
            context.round, BattleLog.EntryType.ACTION
        );
    }
}

// Action 2: Brace — no damage, provides a defensive buff
// This action allows the AdvancedTrainingDummy to brace itself, reducing damage from the next attack.
// It adds a temporary modifier to the wielder's loadout that reduces damage by 5 for one attack.
class Brace extends Action {
    public Brace() {
        super("Brace");
    }

    /**
     * Executes the Brace action.
     * This action provides a defensive buff to the wielder, reducing damage taken by 5 for one attack.
     * @param context The context of the battle, including the wielder and enemy.
     */
    @Override
    public void execute(BattleContext context) {
        context.wielder.getLoadout().addTemporaryModifier(new BraceBuff());

        context.getLog().addEntry(
            context.wielder, null, getName(),
            context.wielder.getName() + " braces defensively.",
            context.round, BattleLog.EntryType.ACTION
        );
    }
}

// Action 3: Wind-Up Slam — charges for 1 turn, then deals 20 damage
// This action simulates a powerful attack that requires charging for one turn.
// After charging, it deals 20 damage to the opponent.
class WindUpSlam extends Action {
    public WindUpSlam() {
        super("Wind-Up Slam", 1); // charges for 1 turn
    }

    /**
     * Executes the Wind-Up Slam action.
     * This action deals 20 damage to the opponent after charging for 1 turn.
     * @param context The context of the battle, including the wielder and enemy.
     */
    @Override
    public void execute(BattleContext context) {
        final int base = 20;
        final int actual = context.enemy.takeDamage(base, context);

        context.getLog().addEntry(
            context.wielder, context.enemy, getName(),
            context.wielder.getName() + " unleashes a massive slam for " + actual + " damage!",
            context.round, BattleLog.EntryType.ACTION
        );
    }
}

/**
 * BraceBuff is a temporary modifier that reduces damage taken by 5 for one attack.
 * It is applied when the AdvancedTrainingDummy uses the Brace action.
 */
class BraceBuff extends TemporaryModifier {
    public BraceBuff() {
        super("Brace Buff", "Increases defense by 5 against one attack.", 1);
    }

    /**
     * This method modifies the attack damage by reducing it by 5.
     * It is called when the wielder is defending against an attack.
     * @param baseDamage The base damage of the attack.
     * @param context The context of the battle, including the wielder and enemy.
     * @return The modified damage after applying the buff.
     */
    @Override
    public int modifyDefense(int baseDamage, BattleContext context) {
        return baseDamage - 5;
    }
}

/**
 * PoisonDart is an action that simulates a poison dart attack.
 * It deals 1 damage to the opponent and applies a poison effect that reduces healing by 50% and deals 1 damage.
 * The poison effect lasts for 5 turns.
 */
class PoisonDart extends Action {
    public PoisonDart() {
        super("Poison Dart");
    }

    /**
     * Executes the Poison Dart action.
     * This action deals 1 damage to the opponent and applies a poison effect that reduces healing by 50% for
     * 5 turns and deals 1 damage at the end of each turn.
     * @param context The context of the battle, including the wielder and enemy.
     */
    @Override
    public void execute(BattleContext context) {
        final int directDamage = 1;
        final int actual = context.enemy.takeDamage(directDamage, context);

        context.enemy.getLoadout().addTemporaryModifier(new PoisonEffect());

        context.getLog().addEntry(
            context.wielder, context.enemy, getName(),
            context.wielder.getName() + " fires a poison dart for " + actual + " damage!",
            context.round, BattleLog.EntryType.ACTION
        );
    }
}

/**
 * PoisonEffect is a temporary modifier that deals 1 damage and reduces healing by 50%.
 * It is applied when the AdvancedTrainingDummy uses a poison dart.
 */
class PoisonEffect extends TemporaryModifier {
    public PoisonEffect() {
        super("Poison", "Deals 1 damage and reduces healing by 50%", 5);
    }

    /**
     * This method modifies the healing amount by reducing it by 50%.
     * It is called when the wielder is healing.
     * @param baseHeal The base healing amount.
     * @param context The context of the battle, including the wielder and enemy.
     * @return The modified healing after applying the poison effect.
     */
    @Override
    public int modifyHealing(int baseHeal, BattleContext context) {
        return baseHeal / 2;
    }

    /**
     * This method is called at the end of each turn to apply the poison damage.
     * It deals 1 damage to the enemy and logs the action.
     * @param context The context of the battle, including the wielder (who is poisoned) and enemy.
     */
    @Override
    public void onTurnEnd(BattleContext context) {
        final int poisonDamage = 1;
        context.wielder.rawDamage(poisonDamage, context);

        context.getLog().addEntry(
            context.wielder, null, getName(),
            context.wielder.getName() + " takes " + poisonDamage + " poison damage.",
            context.round, BattleLog.EntryType.STATUS
        );
    }
}
