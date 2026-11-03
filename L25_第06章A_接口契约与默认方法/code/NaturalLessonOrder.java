import java.util.Arrays;
public class NaturalLessonOrder {
    public static void main(String[] args) {
        RankedLesson[] lessons = {new RankedLesson("后", 3), new RankedLesson("早", 1)};
        Arrays.sort(lessons);
        for (RankedLesson lesson : lessons) System.out.println(lesson);
    }
}
final class RankedLesson implements Comparable<RankedLesson> {
    private final String title;
    private final int priority;
    RankedLesson(String title, int priority) { this.title = title; this.priority = priority; }
    @Override public int compareTo(RankedLesson other) { return Integer.compare(priority, other.priority); }
    @Override public String toString() { return title + ":" + priority; }
}
