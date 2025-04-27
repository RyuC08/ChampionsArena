/**
 * A class that encapsulates the player's choices during their turn in the game.
 * It includes the selected action, either a new tactic, relic, or gambit to swap,
 * and a battle modifier to discard.
 */
public class TurnSubmission {
    /**
     * The action chosen by the player for this turn.
     */
    public final Action selectedAction;

    /**
     * The new tactic to swap in, or null if not applicable.
     */
    public final Tactic newTactic;

    /**
     * The new relic to swap in, or null if not applicable.
     */
    public final Relic newRelic;

    /**
     * The new gambit to swap in, or null if not applicable.
     */
    public final Gambit newGambit;

    /**
     * The battle modifier to discard, or null if not applicable.
     */
    public final BattleModifier discardSlot;

    /**
     * Constructor for TurnSubmission.
     * @param selectedAction The action chosen by the player.
     * @param newTactic The new tactic to swap in, or null if not applicable.
     * @param newRelic The new relic to swap in, or null if not applicable.
     * @param newGambit The new gambit to swap in, or null if not applicable.
     * @param discardSlot The battle modifier to discard, or null if not applicable.
     */
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
