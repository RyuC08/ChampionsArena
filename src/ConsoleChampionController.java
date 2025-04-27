import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * The ConsoleChampionController class implements the ChampionController interface
 * for a console-based version of the Champions Arena game. It allows players to
 * choose champions and plan their turns using console input.
 */
public class ConsoleChampionController implements ChampionController {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructor for the ConsoleChampionController.
     * This constructor initializes the controller for console-based interactions.
     */
    public ConsoleChampionController() {
        // No initialization needed for console input
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
    public CompletableFuture<Champion> chooseChampion(String playerName,
            List<Class<? extends Champion>> availableChampions) {
        System.out.println("\n" + playerName + " Choose your Champion:");

        // Print out the available champions
        for (int i = 0; i < availableChampions.size(); i++) {
            Class<? extends Champion> clazz = availableChampions.get(i);
            String displayName = getChampionDisplayName(clazz);
            System.out.println("  [" + i + "] " + displayName);
        }
        // Get the user's choice
        int choice = getIntInput("Enter number: ", 0, availableChampions.size() - 1);

        try {
            // Use reflection to create an instance of the chosen Champion class
            Champion champ = availableChampions.get(choice).getDeclaredConstructor().newInstance();

            // Allow the champion to select their initial loadout - their tactic, relic, and gambit
            selectInitialLoadoutFor(champ);

            return CompletableFuture.completedFuture(champ);
        } catch (NoSuchMethodException e) {
            // This should never happen since we are using the default constructor
            System.err.println("Failed to instantiate Champion. Try again." +
                    e.getMessage());
            return chooseChampion(playerName, availableChampions); // re-call
        } catch (Exception e) {
            // Handle any other exceptions that may occur during instantiation
            // This includes IllegalAccessException, InstantiationException, InvocationTargetException
            // and any other exceptions that may be thrown by the constructor
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace(System.err);

            return chooseChampion(playerName, availableChampions); // re-call
        }
    }

    /**
     * Allow the user to plan their turn by selecting an action and optionally
     * swapping a tactic, relic, or gambit. The user can also choose to discard
     * one of their modifiers.
     * @param self The champion whose turn it is.
     * @param opponent The opponent champion.
     * @param vault The vault containing available modifiers.
     * @return A CompletableFuture containing the TurnSubmission for the current turn.
     */
    @Override
    public CompletableFuture<TurnSubmission> planTurn(Champion self, Champion opponent, ModifierVault vault) {
        // Refill the arsenal with new modifiers if it has empty slots
        self.getArsenal().refill();

        System.out.println("\n" + self.getName() + "'s Turn vs " + opponent.getName());
        System.out.print("   Current Health:");
        System.out.printf("  %s: %d HP", self.getName(), self.getCurrentHealth());
        System.out.printf("  %s: %d HP\n", opponent.getName(), opponent.getCurrentHealth());
        System.out.printf("   Current Tactic: %s  Relic: %s  Pocketed Gambit: %s",
                self.getLoadout().getTacticName(),
                self.getLoadout().getRelicName(),
                self.getLoadout().getGambitName());
        System.out.println();


        BattleModifier discard = chooseDiscard(self.getArsenal().getSlots());

        Tactic newTactic = null;
        Relic newRelic = null;
        Gambit newGambit = null;

        System.out.println("\nYou may swap ONE modifier into your loadout this turn:");
        List<BattleModifier> arsenal = self.getArsenal().getSlots();

        for (int i = 0; i < arsenal.size(); i++) {
            System.out.print("  [" + i + "] " + arsenal.get(i).getName());
            if (arsenal.get(i) instanceof Tactic) {
                System.out.print(" (Tactic)");
            } else if (arsenal.get(i) instanceof Relic) {
                System.out.print(" (Relic)");
            } else if (arsenal.get(i) instanceof Gambit) {
                System.out.print(" (Gambit)");
            }
            System.out.println();
        }
        System.out.println("  [X] Skip");

        int swapIndex = getIntInput("Choose a modifier to swap (or X to skip): ", -1, arsenal.size() - 1);

        if (swapIndex >= 0) {
            BattleModifier selected = arsenal.get(swapIndex);
            if (selected instanceof Tactic)
                newTactic = (Tactic) selected;
            else if (selected instanceof Relic)
                newRelic = (Relic) selected;
            else if (selected instanceof Gambit)
                newGambit = (Gambit) selected;
        }

        Action action = self.getLockedInAction();

        if (self.isCharging()) {
            int remaining = self.getChargeTurnsRemaining();
            System.out.printf("   %s is charging (%d turn%s left)\n", action.getName(), remaining,
                    remaining == 1 ? "" : "s");
        } else {
            action = chooseAction(self.getActions());
        }

        TurnSubmission submission = new TurnSubmission(action, newTactic, newRelic, newGambit, discard);
        return CompletableFuture.completedFuture(submission);
    }

    /**
     * Allow the user to select their initial loadout for a champion. This includes
     * selecting a tactic, relic, and gambit from the available arsenal.
     * @param champion
     */
    private void selectInitialLoadoutFor(Champion champion) {
        List<BattleModifier> options = champion.getArsenal().getSlots();
        Loadout loadout = champion.getLoadout();
    
        Tactic chosenTactic = (Tactic) chooseModifierOfType(options, Tactic.class, "Choose a Tactic:");
        if (chosenTactic != null) {
            loadout.swapTactic(chosenTactic);
            champion.getArsenal().discard(chosenTactic);
        }
        
        Relic chosenRelic = (Relic) chooseModifierOfType(options, Relic.class, "Choose a Relic:");
        if (chosenRelic != null) {
            loadout.swapRelic(chosenRelic);
            champion.getArsenal().discard(chosenRelic);
        }

        Gambit chosenGambit = (Gambit) chooseModifierOfType(options, Gambit.class, "Choose a Gambit:");
        if (chosenGambit != null) {
            loadout.swapPocketedGambit(chosenGambit);
            champion.getArsenal().discard(chosenGambit);
        }
    }
   

    /**
     * Allows the user to choose a modifier of a specific type from the items in their arsenal.
     * @param clazz the type of modifier to choose
     * @return the chosen modifier, or null if none are available
     */
    private BattleModifier chooseModifierOfType(List<BattleModifier> pool, Class<?> type, String prompt) {
        List<BattleModifier> filtered = new ArrayList<>();
        for (BattleModifier mod : pool) {
            if (type.isAssignableFrom(mod.getClass())) {
                filtered.add(mod);
            }
        }
    
        if (filtered.isEmpty()) {
            System.out.println("⚠️ No available " + type.getSimpleName() + "s in your Arsenal!");
            return null;
        }
    
        System.out.println("\n" + prompt);
        for (int i = 0; i < filtered.size(); i++) {
            BattleModifier mod = filtered.get(i);
            System.out.printf("  [%d] %s - %s\n", i, mod.getName(), mod.getDescription());
        }
    
        int choice = getIntInput("Enter number: ", 0, filtered.size() - 1);
        return filtered.get(choice);
    }    

    /**
     * Allows the user to choose an action from a list of available actions.
     * @param actions the list of available actions
     * @return the chosen action
     */
    private Action chooseAction(List<Action> actions) {
        System.out.println("\nChoose an Action:");
        for (int i = 0; i < actions.size(); i++) {
            System.out.println("  [" + i + "] " + actions.get(i).getName());
        }
        int choice = getIntInput("Enter number: ", 0, actions.size() - 1);
        return actions.get(choice);
    }

    /**
     * Allows the user to choose a modifier to discard from their arsenal.
     * @param slots the list of available modifiers
     * @return the chosen modifier to discard, or null if none is chosen
     */
    private BattleModifier chooseDiscard(List<BattleModifier> slots) {
        System.out.println("\nDiscard one modifier?");

        for (int i = 0; i < slots.size(); i++) {
            System.out.println("  [" + i + "] " + slots.get(i).getName() + (
                    slots.get(i) instanceof Tactic ? " (Tactic)" :
                    slots.get(i) instanceof Relic ? " (Relic)" :
                    slots.get(i) instanceof Gambit ? " (Gambit)" : ""));
        }
        System.out.println("  [X] Skip");

        int choice = getIntInput("Enter number to discard or X to skip: ", -1, slots.size() - 1);
        return (choice >= 0) ? slots.get(choice) : null;
    }

    private int getIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("X"))
                return -1;
            try {
                int val = Integer.parseInt(input);
                if (val >= min && val <= max)
                    return val;
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Invalid input. Please try again.");
        }
    }
}
