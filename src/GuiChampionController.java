import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class GuiChampionController implements ChampionController {
    public GuiChampionController() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public CompletableFuture<Champion> chooseChampion(String playerName, List<Class<? extends Champion>> availableChampions) {
        // Implementation for console input to choose a champion
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<TurnSubmission> planTurn(Champion self, Champion opponent, ModifierVault vault) {
        // Implementation for console input to take a turn
        return CompletableFuture.completedFuture(null);
    }
}