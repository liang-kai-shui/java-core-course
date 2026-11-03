import java.time.LocalDate;
public class StudySprint {
    public static void main(String[] args) {
        StudyGoal goal = new StudyGoal("数组", LocalDate.of(2025, 3, 1), 30);
        System.out.println(goal.summary());
        System.out.println(goal.record(20));
        System.out.println(goal.record(20));
        System.out.println(goal.summary());
        System.out.println(goal.record(5));
    }
}
class StudyGoal {
    private final String title;
    private final LocalDate start;
    private final int target;
    private int done;
    StudyGoal(String title, LocalDate start, int target) {
        this.title = title;
        this.start = start;
        this.target = target > 0 ? target : 1;
    }
    boolean record(int minutes) {
        if (minutes <= 0 || done == target) return false;
        int remaining = target - done;
        done += Math.min(minutes, remaining);
        return true;
    }
    String summary() { return title + "@" + start + " " + done + "/" + target; }
}
