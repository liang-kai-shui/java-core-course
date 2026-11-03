package campus.model;
public final class StudyEntry {
    private final int id, minutes; private final String title; private final boolean completed;
    public StudyEntry(int id, String title, int minutes, boolean completed) {
        if (id <= 0 || title == null || title.trim().isEmpty() || title.indexOf('\t') >= 0 || title.indexOf('\n') >= 0 || title.indexOf('\r') >= 0 || minutes < 0 || minutes > 1000000) throw new IllegalArgumentException("invalid entry");
        this.id = id; this.title = title.trim(); this.minutes = minutes; this.completed = completed;
    }
    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getMinutes() { return minutes; }
    public boolean isCompleted() { return completed; }
    public StudyEntry withCompleted(boolean value) { return new StudyEntry(id, title, minutes, value); }
}
