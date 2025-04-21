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

    /**
     * Constructor for Arsenal.
     * Initializes the arsenal with a set of modifiers drawn from the vault.
     * The arsenal starts with one of each type: Tactic, Relic, and Gambit,
     * and fills the remaining slots with random modifiers from the vault.
     */
    public Arsenal() {
        this.vault = ModifierVault.getInstance();
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

    /**
     * Add a BattleModifier to the arsenal if it is not null.
     * This method is used to ensure that only valid modifiers are added to the arsenal.
     * @param mod The BattleModifier to add to the arsenal.
     */
    private void addIfNotNull(BattleModifier mod) {
        if (mod != null) {
            slots.add(mod);
        }
    }

    /**
     * Get the modifiers in the arsenal.
     * @return The ModifierVault instance.
     */
    public List<BattleModifier> getSlots() {
        return new ArrayList<>(slots); // defensive copy
    }

    /**
     * Determine if the arsenal contains a specific BattleModifier.
     * @param mod The BattleModifier to check for.
     * @return True if the arsenal contains the modifier, false otherwise.
     */
    public boolean contains(BattleModifier mod) {
        return slots.contains(mod);
    }

    /**
     * Get the number of slots in the arsenal.
     * @return The number of slots in the arsenal.
     */
    public boolean isFull() {
        return slots.size() >= MAX_SLOTS;
    }

    /**
     * Discard a BattleModifier from the arsenal.
     * @param mod The BattleModifier to discard.
     */
    public void discard(BattleModifier mod) {
        slots.remove(mod);
    }

    /**
     * Draw a random BattleModifier from the vault and add it to the arsenal.
     * @return True if a modifier was drawn and added, false otherwise.
     */
    public boolean draw() {
        if (isFull()) return false;
        BattleModifier mod = vault.drawRandom();
        if (mod != null) {
            slots.add(mod);
            return true;
        }
        return false;
    }

    /**
     * Refill the arsenal with new modifiers from the vault.
     * This method will continue to draw modifiers until the arsenal is full, and
     * will stop if no more modifiers are available in the vault.
     */
    public void refill() {
        while (!isFull() && draw()) {
            // Keep drawing until the arsenal is full
        }
    }

    /**
     * Return all modifiers in the arsenal to the vault.
     * This method clears the arsenal and returns all modifiers to the vault.
     */
    public void returnAllToVault() {
        slots.clear();
    }
}
