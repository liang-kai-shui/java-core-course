package campus.model;
import java.util.*;
public final class StudyBook {
    private final Map<Integer, StudyEntry> entries = new LinkedHashMap<>();
    public void add(StudyEntry entry) {
        if (entry == null || entries.containsKey(entry.getId())) throw new IllegalArgumentException("duplicate or null entry");
        entries.put(entry.getId(), entry);
    }
    public void setCompleted(int id, boolean value) {
        StudyEntry old = entries.get(id); if (old == null) throw new NoSuchElementException("unknown id: " + id);
        entries.put(id, old.withCompleted(value));
    }
    public List<StudyEntry> snapshot() { return new ArrayList<>(entries.values()); }
}
