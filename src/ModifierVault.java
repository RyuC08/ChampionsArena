import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * A vault of battle modifiers that can be drawn randomly throughout the match.
 * This class is responsible for loading and managing the modifiers, it will dynamically
 * load classes from a specified path and allow for random selection of modifiers, or
 * random selection of a specific type of modifier.
 */

public class ModifierVault {
    private final List<Class<? extends BattleModifier>> registry = new ArrayList<>();
    private final Random random = new Random();

    /**
     * Initialize the vault with default modifiers.
     */
    public ModifierVault() {
        // Initialize the vault with default modifiers
        // registry.add(MyModifier.class);
    }

    /**
     * Initialize the vault with default modifiers and modifiers from a specified path.
     * @param modifierPath The path to the directory containing the modifier classes.
     */
    public ModifierVault(String modifierPath) {

        // Initialize the pool with default modifiers
        this();

        if (modifierPath != null && !modifierPath.isEmpty()) {
            try {
                DynamicClassLoader classLoader = new DynamicClassLoader("path/to/your/classes");
                List<Class<? extends BattleModifier>> classes = classLoader.getSubtypesOf(BattleModifier.class);
                registry.addAll(classes);
            } catch (Exception e) {
                System.err.println("Failed to load modifiers: " + e.getMessage());
            }
        }
    }

    public void registerBattleModifier(Class<? extends BattleModifier> clazz) {
        if (clazz != null && !registry.contains(clazz)) {
            registry.add(clazz);
        }
    }
    
    public BattleModifier drawRandom() {
        if (registry.isEmpty()) return null;
        return instantiate(registry.get(random.nextInt(registry.size())));
    }

    public BattleModifier drawRandomOfType(Class<? extends BattleModifier> type) {
        List<Class<? extends BattleModifier>> matches = registry.stream()
            .filter(c -> type.isAssignableFrom(c))
            .toList();

        if (matches.isEmpty()) return null;
        return instantiate(matches.get(random.nextInt(matches.size())));
    }

    private BattleModifier instantiate(Class<? extends BattleModifier> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.err.println("Could not instantiate " + clazz.getName() + ": " + e.getMessage());
            return null;
        }
    }
}
