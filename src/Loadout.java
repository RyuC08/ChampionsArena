import java.util.List;
import java.util.ArrayList;

public class Loadout {
    private Tactic tactic;
    private Relic relic;
    private Gambit pocketedGambit;
    private final List<BattleModifier> temporaryModifiers = new ArrayList<>();

    public Loadout() {
        this.tactic = null;
        this.relic = null;
        this.pocketedGambit = null;
    }

    // --- Accessors ---
    public Tactic getTactic() {
        return tactic;
    }

    public Relic getRelic() {
        return relic;
    }

    public Gambit getPocketedGambit() {
        return pocketedGambit;
    }

    public String getTacticName() {
        return tactic != null ? tactic.getName() : "None";
    }

    public String getRelicName() {
        return relic != null ? relic.getName() : "None";
    }

    public String getGambitName() {
        return pocketedGambit != null ? pocketedGambit.getName() : "None";
    }

    // --- Swaps that return the old item ---
    public Tactic swapTactic(Tactic newTactic) {
        Tactic old = this.tactic;
        this.tactic = newTactic;
        return old;
    }

    public Relic swapRelic(Relic newRelic) {
        Relic old = this.relic;
        this.relic = newRelic;
        return old;
    }

    public Gambit swapPocketedGambit(Gambit newGambit) {
        Gambit old = this.pocketedGambit;
        this.pocketedGambit = newGambit;
        return old;
    }

    public Gambit clearGambit() {
        return swapPocketedGambit(null);
    }

    public void addTemporaryModifier(BattleModifier mod) {
        temporaryModifiers.add(mod);
    }

    // --- Modifier Pipeline ---
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
