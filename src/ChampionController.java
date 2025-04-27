import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.lang.reflect.Field;

/**
 * The ChampionController interface defines the methods for player interactions
 * in the Champions Arena game. It allows players to choose champions and plan
 * their turns during the battle.
 */
public interface ChampionController {
    /**
     * Allow a player to choose a champion from a list of available champions.
     * @param playerName The name of the player choosing the champion.
     * @param availableChampions A list of available champions to choose from.
     * @return a CompletableFuture that will complete with the player's chosen champion
     */
    CompletableFuture<Champion> chooseChampion(String playerName, List<Class<? extends Champion>> availableChampions);

    /**
     * Allow a player to plan their next turn in the battle.
     * @param self the player's champion
     * @param opponent the opponent's champion
     * @param vault the vault of battle modifiers
     * @return a CompletableFuture that will complete with the player's turn submission
     */
    CompletableFuture<TurnSubmission> planTurn(Champion self, Champion opponent, ModifierVault vault);

    /**
     * Get the display name (either a public static NAME variable if available or the class name) from a
     * champion class.
     * @param clazz the champion class
     * @return the display name of the champion
     */
    public default String getChampionDisplayName(Class<? extends Champion> clazz) {
        try {
            Field field = clazz.getField("NAME");
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                Object value = field.get(null);
                if (value instanceof String) {
                    return (String) value;
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}
    
        return clazz.getSimpleName();
    }
    

}
