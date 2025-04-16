import java.util.List;
import java.util.ArrayList;

public class Loadout {
    private Tactic tactic;
    private Relic relic;
    private Gambit pocketedGambit;

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
    
        return active;
    }
}
