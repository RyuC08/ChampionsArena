import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class ConsoleChampionController implements ChampionController {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public CompletableFuture<Champion> chooseChampion(String playerName, List<Class<? extends Champion>> availableChampions) {
        System.out.println("\n" + playerName + " Choose your Champion:");

        for (int i = 0; i < availableChampions.size(); i++) {
            Class<? extends Champion> clazz = availableChampions.get(i);
            String displayName = getChampionDisplayName(clazz);
            System.out.println("  [" + i + "] " + displayName);
        }        

        int choice = getIntInput("Enter number: ", 0, availableChampions.size() - 1);
        try {
            Champion champ = availableChampions.get(choice).getDeclaredConstructor().newInstance();
            return CompletableFuture.completedFuture(champ);
        } catch (NoSuchMethodException e) {
            System.err.println("Failed to instantiate Champion. Try again." + 
                e.getMessage());
             
            return chooseChampion(playerName, availableChampions); // re-call
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace(System.err);
            
            return chooseChampion(playerName, availableChampions); // re-call
        }
    }

    @Override
    public CompletableFuture<TurnSubmission> planTurn(Champion self, Champion opponent, ModifierVault vault) {
        System.out.println("\n" + self.getName() + "'s Turn vs " + opponent.getName());
        System.out.print("   Current Health:");
        System.out.printf("  %s: %d HP", self.getName(), self.getCurrentHealth());
        System.out.printf("  %s: %d HP\n", opponent.getName(), opponent.getCurrentHealth());

        BattleModifier discard = chooseDiscard(self.getArsenal().getSlots());

        Tactic newTactic = null;
        Relic newRelic = null;
        Gambit newGambit = null;

        System.out.println("\nYou may swap ONE modifier this turn:");
        List<BattleModifier> arsenal = self.getArsenal().getSlots();

        for (int i = 0; i < arsenal.size(); i++) {
            System.out.println("  [" + i + "] " + arsenal.get(i).getName() + " (" + arsenal.get(i).getClass().getSimpleName() + ")");
        }
        System.out.println("  [X] Skip");

        int swapIndex = getIntInput("Choose a modifier to swap (or X to skip): ", -1, arsenal.size() - 1);

        if (swapIndex >= 0) {
            BattleModifier selected = arsenal.get(swapIndex);
            if (selected instanceof Tactic) newTactic = (Tactic) selected;
            else if (selected instanceof Relic) newRelic = (Relic) selected;
            else if (selected instanceof Gambit) newGambit = (Gambit) selected;
        }

        Action action = self.getLockedInAction();

        if (self.isCharging()) {
            int remaining = self.getChargeTurnsRemaining();
            System.out.printf("   %s is charging (%d turn%s left)\n", action.getName(), remaining, remaining == 1 ? "" : "s");
        }
        else {
            action = chooseAction(self.getActions());
        }

        TurnSubmission submission = new TurnSubmission(action, newTactic, newRelic, newGambit, discard);
        return CompletableFuture.completedFuture(submission);
    }

    private Action chooseAction(List<Action> actions) {
        System.out.println("\nChoose an Action:");
        for (int i = 0; i < actions.size(); i++) {
            System.out.println("  [" + i + "] " + actions.get(i).getName());
        }
        int choice = getIntInput("Enter number: ", 0, actions.size() - 1);
        return actions.get(choice);
    }

    private BattleModifier chooseDiscard(List<BattleModifier> slots) {
        System.out.println("\nDiscard one modifier?");

        for (int i = 0; i < slots.size(); i++) {
            System.out.println("  [" + i + "] " + slots.get(i).getName() + " (" + slots.get(i).getClass().getSimpleName() + ")");
        }
        System.out.println("  [X] Skip");

        int choice = getIntInput("Enter number to discard or X to skip: ", -1, slots.size() - 1);
        return (choice >= 0) ? slots.get(choice) : null;
    }

    private int getIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("X")) return -1;
            try {
                int val = Integer.parseInt(input);
                if (val >= min && val <= max) return val;
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid input. Please try again.");
        }
    }
}
