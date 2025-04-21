import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * The battle log records the actions taken during a battle.
 * It stores entries that describe the actions, their actors, targets,
 * and the round in which they occurred.
 */
public class BattleLog {
    private List<Entry> entries;

    /**
     * Constructor for the BattleLog.
     * Initializes an empty list of entries.
     */
    public BattleLog() {
        entries = new ArrayList<>();
    }

    /**
     * @return An array of Entry objects representing the log.
     */
    public Entry[] getLog() {
        return entries.toArray(new Entry[0]);
    }

    /**
     * Adds an entry to the battle log.
     * @param actionName The name of the action performed.
     * @param description A description of the action.
     * @param round The round in which the action occurred.
     * @param type The type of entry (ACTION, DAMAGE, MODIFIER, STATUS, INFO).
     */
    public void addEntry(String actionName, String description, int round, EntryType type) {
        addEntry(null, null, actionName, description, round, type);
    }

    /**
     * Adds an informational entry to the battle log.
     * @param actionName The name of the action performed.
     * @param description A description of the action.
     * @param round The round in which the action occurred.
     */
    public void addInfoEntry(String actionName, String description, int round) {
        addEntry(null, null, actionName, description, round, EntryType.INFO);
    }

    /**
     * Adds an action entry to the battle log.
     * @param actor The champion who performed the action.
     * @param actionName The name of the action performed.
     * @param description A description of the action.
     * @param round The round in which the action occurred.
     */
    public void addActionEntry(Champion actor, String actionName, String description, int round) {
        addEntry(actor, null, actionName, description, round, EntryType.ACTION);
    }


    /**
     * Adds an entry to the battle log.
     * @param actor The champion who performed the action.
     * @param actionName The name of the action performed.
     * @param description A description of the action.
     * @param round The round in which the action occurred.
     * @param type The type of entry (ACTION, DAMAGE, MODIFIER, STATUS, INFO).
     */
    public void addEntry(Champion actor, String actionName, String description, int round, EntryType type) {
        addEntry(actor, null, actionName, description, round, type);
    }

    /**
     * Adds an entry to the battle log.
     * @param actor The champion who performed the action.
     * @param target The champion who was targeted by the action.
     * @param actionName The name of the action performed.
     * @param description A description of the action.
     * @param round The round in which the action occurred.
     * @param type The type of entry (ACTION, DAMAGE, MODIFIER, STATUS, INFO).
     */
    public void addEntry(Champion actor, Champion target, String actionName, String description, int round, EntryType type) {
        entries.add(new Entry(actor, target, actionName, description, round, type));
    }

    /**
     * Adds an action entry to the battle log.
     * @param actor The champion who performed the action.
     * @param target The champion who was targeted by the action.
     * @param actionName The name of the action performed.
     * @param description A description of the action.
     * @param round The round in which the action occurred.
     */
    public void addActionEntry(Champion actor, Champion target, String actionName, String description, int round) {
        addEntry(actor, target, actionName, description, round, EntryType.ACTION);
    }

    /**
     * Get all entries in the log that occurred in a specific round.
     * @param round The round number to filter by.
     * @return A list of entries that occurred in the specified round.
     */
    public List<Entry> getEntriesByRound(int round) {
        return entries.stream()
                    .filter(e -> e.round == round)
                    .collect(Collectors.toList());
    }

    /**
     * The Entry class represents a single entry in the battle log.
     * It contains information about the action, the actor, the target,
     * the description, the round, and the type of entry.
     * It is used to record the details of each action taken during the battle.
     * 
     * Each entry is created with the relevant information and can be printed
     * to the console for debugging or logging purposes.
     */
    public static class Entry {
        public final Champion actor;
        public final Champion target;
        public final String description;
        public final String actionName;
        public final int round;
        public final EntryType type;

        /**
         * Constructor for the Entry class.
         * @param actor The champion who performed the action.
         * @param target The champion who was targeted by the action.
         * @param actionName The name of the action performed.
         * @param description A description of the action.
         * @param round The round in which the action occurred.
         * @param type The type of entry (ACTION, DAMAGE, MODIFIER, STATUS, INFO).
         */
        public Entry(Champion actor, Champion target, String actionName,
                     String description, int round, EntryType type) {
            this.actor = actor;
            this.target = target;
            this.actionName = actionName;
            this.description = description;
            this.round = round;
            this.type = type;
        }

        /**
         * @return A string representation of the entry.
         */
        @Override
        public String toString() {
            return "[" + type + " | Round " + round + "] " + description;
        }
    }

    /**
     * The EntryType enum represents the different types of entries that can be recorded in the battle log.
     * It includes ACTION, DAMAGE, MODIFIER, STATUS, and INFO.
     */
    public static enum EntryType {
        ACTION,     // "Frostmare uses Fire Fang"
        DAMAGE,     // "Infernosaur takes 12 damage"
        MODIFIER,   // "Shield Wall reduces damage by 4"
        STATUS,     // "Burn applied to Frostmare"
        INFO        // "Round 3 begins"
    }
}