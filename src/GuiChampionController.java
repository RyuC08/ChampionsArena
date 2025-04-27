import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * The GuiChampionController class implements the ChampionController interface
 * for a graphical user interface (GUI) version of the Champions Arena game.
 * It allows players to choose champions and plan their turns using GUI elements.
 */
public class GuiChampionController implements ChampionController {
    /**
     * Constructor for the GuiChampionController.
     * This constructor initializes the controller for GUI-based interactions.
     */
    public GuiChampionController() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Allow the user to choose a champion from the available options. This
     * method uses reflection to create an instance of the chosen champion class.
     * It also allows the user to select their initial loadout, including a tactic,
     * relic, and gambit.
     * @param playerName The name of the player choosing the champion.
     * @param availableChampions A list of available champion classes to choose from.
     * @return A CompletableFuture containing the chosen champion instance.
     */
    @Override
    public CompletableFuture<Champion> chooseChampion(String playerName, List<Class<? extends Champion>> availableChampions) {
        // Implementation for console input to choose a champion
        return CompletableFuture.completedFuture(null);
    }

    /**
     * Allow the user to plan their turn by selecting an action and optionally
     * a target for that action.
     * @param self The player's champion.
     * @param opponent The opponent's champion.
     * @param vault The vault of battle modifiers.
     * @return A CompletableFuture containing the player's turn submission.
     */
    @Override
    public CompletableFuture<TurnSubmission> planTurn(Champion self, Champion opponent, ModifierVault vault) {
        // Implementation for console input to take a turn
        return CompletableFuture.completedFuture(null);
    }
}