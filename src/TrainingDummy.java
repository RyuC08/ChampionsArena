import java.util.List;

public class TrainingDummy extends Champion {
    public static final String NAME = "Training Dummy";

    public TrainingDummy() {
        super(NAME, 5, 5, 50);
    }

    @Override
    public List<Action> getActions() {
        return List.of(new Headbutt(), new Shrug(), new Bandaid());
    }    
}

// Sample Action 1
class Headbutt extends Action {
    public Headbutt() {
        super("Headbutt");
    }

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

// Sample Action 2
class Shrug extends Action {
    public Shrug() {
        super("Shrug");
    }

    @Override
    public void execute(BattleContext context) {
        context.getLog().addEntry(
            context.wielder, null, getName(),
            context.wielder.getName() + " shrugs and does nothing.",
            context.round, BattleLog.EntryType.ACTION
        );
    }
}

// Sample Action 3
class Bandaid extends Action {
    public Bandaid() {
        super("Bandaid");
    }

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
