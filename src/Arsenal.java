import java.util.List;
import java.util.ArrayList;

/**
 * A Champion's arsenal is a collection of BattleModifiers that can be used
 * during a battle. The arsenal is initialized with a set of modifiers drawn
 * from a vault, which contains various types of modifiers. The arsenal can
 * be refreshed, allowing the player to draw new modifiers from the vault when
 * slots are available.
 */
public class Arsenal {
    private static final int MAX_SLOTS = 5;

    private final List<BattleModifier> slots;
    private final ModifierVault vault;

    public Arsenal(ModifierVault vault) {
        this.vault = vault;
        this.slots = new ArrayList<>();

        // Draw one of each type
        addIfNotNull(vault.drawRandomOfType(Tactic.class));
        addIfNotNull(vault.drawRandomOfType(Relic.class));
        addIfNotNull(vault.drawRandomOfType(Gambit.class));

        // Fill remaining slots with random modifiers
        while (slots.size() < MAX_SLOTS) {
            BattleModifier mod = vault.drawRandom();
            if (mod == null) break;
            slots.add(mod);
        }
    }

    private void addIfNotNull(BattleModifier mod) {
        if (mod != null) {
            slots.add(mod);
        }
    }

    public List<BattleModifier> getSlots() {
        return new ArrayList<>(slots); // defensive copy
    }

    public boolean contains(BattleModifier mod) {
        return slots.contains(mod);
    }

    public boolean isFull() {
        return slots.size() >= MAX_SLOTS;
    }

    public void discard(BattleModifier mod) {
        slots.remove(mod);
    }

    public boolean draw() {
        if (isFull()) return false;
        BattleModifier mod = vault.drawRandom();
        if (mod != null) {
            slots.add(mod);
            return true;
        }
        return false;
    }

    public void refresh() {
        while (!isFull()) {
            if (!draw()) break;
        }
    }

    public void returnAllToVault() {
        slots.clear();
    }
}
