public class StudyPortfolio {
    public static void main(String[] args) {
        GoalItem reading = new GoalItem("阅读", 10);
        GoalItem practice = new GoalItem("练习", 20);
        reading.record(7);
        practice.record(25);
        for (GoalItem item : new GoalItem[] {reading, practice}) System.out.println(item.summary());
        System.out.println(reading.record(0));
        System.out.println(reading.record(-1));
        System.out.println(reading.record(Integer.MAX_VALUE));
        System.out.println(reading.record(1));
        System.out.println(reading.summary());
    }
}
class GoalItem {
    private final String title;
    private final int target;
    private int done;
    GoalItem(String title, int target) { this.title = title; this.target = target > 0 ? target : 1; }
    boolean record(int n) {
        if (n <= 0 || done == target) return false;
        done += Math.min(n, target - done);
        return true;
    }
    String summary() { return title + " " + done + "/" + target; }
}
