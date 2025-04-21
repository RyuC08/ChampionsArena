/**
 * The current battle context in which a BattleModifier is being employed. The
 * context includes the current round, the champion that is the wielder of the 
 * modifier, the enemy champion, and the battle log.
 */
public class BattleContext {
    public final Champion wielder; // The champion that is the wielder of the modifier
    public final Champion enemy;  // The enemy champion
    public final int round;       // The current round of the battle
    private final BattleLog log;  // The battle log to record actions and events

    /**
     * Constructor for BattleContext.
     * @param wielder The champion that is the wielder of the modifier
     * @param enemy The enemy champion
     * @param round The current round of the battle
     * @param log The battle log to record actions and events
     */
    public BattleContext(Champion wielder, Champion enemy, int round, BattleLog log) {
        this.wielder = wielder;
        this.enemy = enemy;
        this.round = round;
        this.log = log;
    }

    /**
     * @return true if the champion is the attacker weilding the modifier.
     * @param champ The champion to check
     */
    public boolean isAttacker(Champion champ) {
        return champ == wielder;
    }

    /**
     * @return true if the champion is the defender weilding the wielder.
     * @param champ The champion to check
     */
    public boolean isDefending(Champion champ) {
        return champ == enemy;
    }

    /**
     * @return the battle log for the current battle context
     */
    public BattleLog getLog() {
        return log;
    }

    /**
     * @return a new BattleContext with the roles of the champions reversed, ie for
     * the second champion to be the wielder and the first champion to be the enemy.
     */
    public BattleContext reverse() {
        return new BattleContext(enemy, wielder, round, log);
    }
}
