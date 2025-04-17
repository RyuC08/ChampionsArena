import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BattleLog {
    private List<Entry> entries;

    public BattleLog() {
        entries = new ArrayList<>();
    }

    public Entry[] getLog() {
        return entries.toArray(new Entry[0]);
    }

    public void addEntry(String actionName, String description, int round, EntryType type) {
        addEntry(null, null, actionName, description, round, type);
    }

    public void addInfoEntry(String actionName, String description, int round) {
        addEntry(null, null, actionName, description, round, EntryType.INFO);
    }

    public void addActionEntry(Champion actor, String actionName, String description, int round) {
        addEntry(actor, null, actionName, description, round, EntryType.ACTION);
    }


    public void addEntry(Champion actor, String actionName, String description, int round, EntryType type) {
        addEntry(actor, null, actionName, description, round, type);
    }

    public void addEntry(Champion actor, Champion target, String actionName, String description, int round, EntryType type) {
        entries.add(new Entry(actor, target, actionName, description, round, type));
    }

    public void addActionEntry(Champion actor, Champion target, String actionName, String description, int round) {
        addEntry(actor, target, actionName, description, round, EntryType.ACTION);
    }

    public List<Entry> getEntriesByRound(int round) {
        return entries.stream()
                    .filter(e -> e.round == round)
                    .collect(Collectors.toList());
    }


    public static class Entry {
        public final Champion actor;
        public final Champion target;
        public final String description;
        public final String actionName;
        public final int round;
        public final EntryType type;

        public Entry(Champion actor, Champion target, String actionName,
                     String description, int round, EntryType type) {
            this.actor = actor;
            this.target = target;
            this.actionName = actionName;
            this.description = description;
            this.round = round;
            this.type = type;
        }

        @Override
        public String toString() {
            return "[" + type + " | Round " + round + "] " + description;
        }
    }

    public static enum EntryType {
        ACTION,     // "Frostmare uses Fire Fang"
        DAMAGE,     // "Infernosaur takes 12 damage"
        MODIFIER,   // "Shield Wall reduces damage by 4"
        STATUS,     // "Burn applied to Frostmare"
        INFO        // "Round 3 begins"
    }
}