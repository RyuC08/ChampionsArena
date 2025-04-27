import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * The ChampionsArena class is the entry point for the Champions Arena game.
 * It initializes the game, loads champions, and starts the battle between two players.
 */
public class ChampionsArena {
    /**
     * An unused constructor for the ChampionsArena class.
     * This constructor is not implemented and will throw an exception if called.
     */
    public ChampionsArena() {
        // Constructor for the ChampionsArena class
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * The main method is the entry point for the Champions Arena game.
     * It initializes the game, loads champions, and starts the battle between two players.
     * @param args Command line arguments to specify the controller type (console, gui, web).
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java ChampionsArena <controller_type>");
            System.err.println("Available controller types: console, gui, web");
            args = new String[] {"console"};
        }
        
        // Determine the controller type based on command line argument
        ChampionController controller = null;
        switch(args[0]) {
            case "gui":
                controller = new GuiChampionController();
                break;
            case "web":
                controller = new WebChampionController();
                break;
            default:
            case "console":
                controller = new ConsoleChampionController();
                break;
        };

        // Load champions from the specified folder
        List<Class<? extends Champion>> championClasses = loadChampionClasses("./");
        championClasses.add(TrainingDummy.class);
        championClasses.add(AdvancedTrainingDummy.class);

        // Check if there are enough champions to run a battle
        if (championClasses.size() < 2) {
            System.err.println("Not enough Champions found to run a battle.");
            return;
        }

        // Initialize the vault with modifiers
        ModifierVault vault = ModifierVault.initialize("./");

        // Allow players to choose their champions
        CompletableFuture<Champion> playerOneFuture = controller.chooseChampion("Player 1", championClasses);
        CompletableFuture<Champion> playerTwoFuture = controller.chooseChampion("Player 2", championClasses);
        Champion playerOne = playerOneFuture.join();
        Champion playerTwo = playerTwoFuture.join();

        // Initialize the battle log and engine
        BattleLog log = new BattleLog();
        BattleEngine engine = new BattleEngine(playerOne, playerTwo, log, vault, controller);

        // Start the battle
        engine.runMatch();
    }

    /**
     * Loads champion classes from the specified folder.
     * @param folderPath The path to the folder containing champion classes.
     * @return A list of champion classes.
     */
    private static List<Class<? extends Champion>> loadChampionClasses(String folderPath) {
        try {
            DynamicClassLoader loader = new DynamicClassLoader(folderPath);
            return loader.getSubtypesOf(Champion.class);
        } catch (Exception e) {
            System.err.println("Error loading champions: " + e.getMessage());
            return List.of();
        }
    }
}
