/**
 * A class that encapsulates the player's choices during their turn in the game.
 * It includes the selected action, either a new tactic, relic, or gambit to swap,
 * and a battle modifier to discard.
 */
public class TurnSubmission {
    public final Action selectedAction;
    public final Tactic newTactic;
    public final Relic newRelic;
    public final Gambit newGambit;
    public final BattleModifier discardSlot;

    public TurnSubmission(Action selectedAction,
                          Tactic newTactic,
                          Relic newRelic,
                          Gambit newGambit,
                          BattleModifier discardSlot) {
        this.selectedAction = selectedAction;
        this.newTactic = newTactic;
        this.newRelic = newRelic;
        this.newGambit = newGambit;
        this.discardSlot = discardSlot;
    }
}
