import java.util.List;
import java.util.ArrayList;

/**
 * A champion's loadout, which includes a tactic, a relic, and a pocketed gambit, and any
 * temporary modifiers that are active. The loadout is used to manage the
 * champion's current state in the game, including the active modifiers and
 * the items that are currently equipped.
 */
public class Loadout {
    private Tactic tactic;
    private Relic relic;
    private Gambit pocketedGambit;
    private final List<BattleModifier> temporaryModifiers = new ArrayList<>();

    /**
     * Constructor for the Loadout class. Initializes the loadout with no
     * tactic, relic, or pocketed gambit.
     */
    public Loadout() {
        this.tactic = null;
        this.relic = null;
        this.pocketedGambit = null;
    }

    /**
     * Get the champion's current tactic.
     * @return The current tactic, or null if none is equipped.
     */
    public Tactic getTactic() {
        return tactic;
    }

    /**
     * Get the champion's current relic.
     * @return The current relic, or null if none is equipped.
     */
    public Relic getRelic() {
        return relic;
    }

    /**
     * Get the champion's current pocketed gambit.
     * @return The current pocketed gambit, or null if none is equipped.
     */
    public Gambit getPocketedGambit() {
        return pocketedGambit;
    }

    /**
     * Get the name of the champion's current tactic.
     * @return The name of the current tactic, or "None" if none is equipped.
     */
    public String getTacticName() {
        return tactic != null ? tactic.getName() : "None";
    }

    /**
     * Get the name of the champion's current relic.
     * @return The name of the current relic, or "None" if none is equipped.
     */
    public String getRelicName() {
        return relic != null ? relic.getName() : "None";
    }

    /**
     * Get the name of the champion's current pocketed gambit.
     * @return The name of the current pocketed gambit, or "None" if none is equipped.
     */
    public String getGambitName() {
        return pocketedGambit != null ? pocketedGambit.getName() : "None";
    }

    /**
     * This method replaces the current tactic with the new tactic and returns
     * the old tactic. If the new tactic is null, the current tactic is removed.
     * @param newTactic The new tactic to replace the current tactic.
     * @return The old tactic, or null if there was no tactic to replace.
     */
    public Tactic swapTactic(Tactic newTactic) {
        Tactic old = this.tactic;
        this.tactic = newTactic;
        return old;
    }

    /**
     * This method replaces the current relic with the new relic and returns
     * the old relic. If the new relic is null, the current relic is removed.
     * @param newRelic The new relic to replace the current relic.
     * @return The old relic, or null if there was no relic to replace.
     */
    public Relic swapRelic(Relic newRelic) {
        Relic old = this.relic;
        this.relic = newRelic;
        return old;
    }

    /**
     * This method replaces the current pocketed gambit with the new gambit and
     * returns the old gambit. If the new gambit is null, the current gambit is
     * removed.
     * @param newGambit The new gambit to replace the current gambit.
     * @return The old gambit, or null if there was no gambit to replace.
     */
    public Gambit swapPocketedGambit(Gambit newGambit) {
        Gambit old = this.pocketedGambit;
        this.pocketedGambit = newGambit;
        return old;
    }

    /**
     * This method clears the current pocketed gambit and returns it. This is
     * useful for removing the gambit from the loadout without replacing it with
     * a new one.
     * @return The old gambit, or null if there was no gambit to clear.
     */
    public Gambit clearGambit() {
        return swapPocketedGambit(null);
    }

    /**
     * This method adds a temporary modifier to the loadout. Temporary modifiers
     * such as status effects or buffs are added to the loadout and will be
     * removed when they expire.
     * @param mod The temporary modifier to add to the loadout.
     */
    public void addTemporaryModifier(BattleModifier mod) {
        temporaryModifiers.add(mod);
    }

    /**
     * Get a list of all active modifiers in the loadout. This includes the
     * current tactic, relic, and any temporary modifiers that are active. The
     * pocketed gambit is only included if it is activated.
     * @return A list of all active modifiers in the loadout.
     */
    public List<BattleModifier> getActiveModifiers() {
        List<BattleModifier> active = new ArrayList<>();
    
        if (tactic != null && !tactic.isExpired()) {
            active.add(tactic);
        }
    
        if (relic != null && !relic.isExpired()) {
            active.add(relic);
        }
    
        // Only include the gambit if it is activated
        if (pocketedGambit != null && pocketedGambit.isActivated() && !pocketedGambit.isExpired()) {
            active.add(pocketedGambit);
        }

        active.addAll(temporaryModifiers);
    
        return active;
    }

    /**
     * End the turn by removing expired modifiers and clearing temporary
     * modifiers. This method should be called at the end of each turn to
     * ensure that the loadout is ready for the next turn.
     * @param context The context of the battle, including the current round and
     *                the current champion. 
     */
    public void endTurn(BattleContext context) {
        // End the turn for all active modifiers
        for (BattleModifier mod : getActiveModifiers()) {
            if (!mod.isExpired()) {
                mod.onTurnEnd(context);
                mod.endRound();
            }
        }

        // Remove expired modifiers
        if (tactic != null && tactic.isExpired()) {
            tactic = null;
        }
    
        if (relic != null && relic.isExpired()) {
            relic = null;
        }
    
        if (pocketedGambit != null && pocketedGambit.isExpired()) {
            pocketedGambit = null;
        }
        
        temporaryModifiers.removeIf(BattleModifier::isExpired);
    }

}
