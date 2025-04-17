import java.util.List;

public class AdvancedTrainingDummy extends Champion {
    public static final String NAME = "Advanced Training Dummy";

    public AdvancedTrainingDummy() {
        // Name, attackPower, defensePower, maxHealth
        super(NAME, 7, 3, 60);
    }

    @Override
    public List<Action> getActions() {
        return List.of(new Jab(), new Brace(), new WindUpSlam());
    }

    // Action 1: Jab
    private static class Jab extends Action {
        public Jab() {
            super("Jab");
        }

        @Override
        public void execute(BattleContext context) {
            int baseDamage = 5;
            int actual = context.defender.takeDamage(baseDamage, context);
            context.getLog().addEntry(
                context.attacker, context.defender, getName(),
                context.attacker.getName() + " jabs for " + actual + " damage.",
                context.round, BattleLog.EntryType.ACTION
            );
        }
    }

    // Action 2: Brace — no damage, just role flavor
    private static class Brace extends Action {
        public Brace() {
            super("Brace");
        }

        @Override
        public void execute(BattleContext context) {
            context.attacker.getLoadout().addTemporaryModifier(new BraceBuff());

            context.getLog().addEntry(
                context.attacker, null, getName(),
                context.attacker.getName() + " braces defensively.",
                context.round, BattleLog.EntryType.ACTION
            );
        }
    }

    private static class WindUpSlam extends Action {
        public WindUpSlam() {
            super("Wind-Up Slam", 1); // charges for 1 turn
        }
    
        @Override
        public void execute(BattleContext context) {
            int base = 20;
            int actual = context.defender.takeDamage(base, context);
    
            context.getLog().addEntry(
                context.attacker, context.defender, getName(),
                context.attacker.getName() + " unleashes a massive slam for " + actual + " damage!",
                context.round, BattleLog.EntryType.ACTION
            );
        }
    }
    
    private static class BraceBuff extends TemporaryModifier {
        public BraceBuff() {
            super("Brace Buff", "Increases defense by 5 for one attack.", 1);
        }
    
        @Override
        public int modifyDefense(int baseDamage, BattleContext context) {
            return baseDamage - 5;
        }
    }
}
