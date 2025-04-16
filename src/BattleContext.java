public class BattleContext {
    public final Champion attacker;
    public final Champion defender;
    public final int round;
    private final BattleLog log;

    public BattleContext(Champion attacker, Champion defender, int round, BattleLog log) {
        this.attacker = attacker;
        this.defender = defender;
        this.round = round;
        this.log = log;
    }

    public boolean isAttacking(Champion champ) {
        return champ == attacker;
    }

    public boolean isDefending(Champion champ) {
        return champ == defender;
    }

    public BattleLog getLog() {
        return log;
    }
}
