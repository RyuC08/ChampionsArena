import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ChampionController {
    /**
     * Allow a player to choose a champion from a list of available champions.
     * @param availableChampions A list of available champions to choose from.
     * @return a CompletableFuture that will complete with the player's chosen champion
     */
    CompletableFuture<Champion> chooseChampion(List<Class<? extends Champion>> availableChampions);

    /**
     * Allow a player to take their turn in the battle.
     * @param self the player's champion
     * @param opponent the opponent's champion
     * @param vault the vault of battle modifiers
     * @return a CompletableFuture that will complete with the player's turn submission
     */
    CompletableFuture<TurnSubmission> takeTurn(Champion self, Champion opponent, ModifierVault vault);

}
