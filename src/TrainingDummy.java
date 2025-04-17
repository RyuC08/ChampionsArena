import java.util.List;

public class TrainingDummy extends Champion {
    public static final String NAME = "Training Dummy";

    public TrainingDummy() {
        super(NAME, 5, 5, 50);
    }

    @Override
    public List<Action> getActions() {
        return List.of(new Headbutt(), new Shrug());
    }

    // Sample Action 1
    private static class Headbutt extends Action {
        public Headbutt() {
            super("Headbutt");
        }

        @Override
        public void execute(BattleContext context) {
            int baseDamage = 8;
            int actualDamage = context.defender.takeDamage(baseDamage, context);

            context.getLog().addEntry(
                context.attacker, context.defender, getName(),
                context.attacker.getName() + " headbutts for " + actualDamage + " damage!",
                context.round, BattleLog.EntryType.ACTION
            );
        }
    }

    // Sample Action 2
    private static class Shrug extends Action {
        public Shrug() {
            super("Shrug");
        }

        @Override
        public void execute(BattleContext context) {
            context.getLog().addEntry(
                context.attacker, null, getName(),
                context.attacker.getName() + " shrugs and does nothing.",
                context.round, BattleLog.EntryType.ACTION
            );
        }
    }
}
