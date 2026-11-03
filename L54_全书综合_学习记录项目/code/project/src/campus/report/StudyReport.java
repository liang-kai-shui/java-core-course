package campus.report;
import java.util.List;
import campus.model.StudyEntry;
public final class StudyReport {
    private final int entries, pending; private final long completedMinutes;
    private StudyReport(int entries, int pending, long completedMinutes) { this.entries = entries; this.pending = pending; this.completedMinutes = completedMinutes; }
    public static StudyReport summarize(List<StudyEntry> snapshot) {
        long minutes = 0; int pending = 0;
        for (StudyEntry entry : snapshot) { if (entry.isCompleted()) minutes += entry.getMinutes(); else pending++; }
        return new StudyReport(snapshot.size(), pending, minutes);
    }
    public int getEntries() { return entries; }
    public int getPending() { return pending; }
    public long getCompletedMinutes() { return completedMinutes; }
}
