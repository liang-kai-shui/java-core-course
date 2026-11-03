public class PracticeConstructors {
    public static void main(String[] args) {
        System.out.println(new PracticePlan().summary());
        System.out.println(new PracticePlan("循环").summary());
        System.out.println(new PracticePlan("数组", 4).summary());
        for (int n : new int[] {0, -1, 1}) System.out.println(new PracticePlan("边界", n).summary());
    }
}
class PracticePlan {
    private final String title;
    private final int count;
    PracticePlan() { this("每日练习", 2); }
    PracticePlan(String title) { this(title, 2); }
    PracticePlan(String title, int count) {
        this.title = title;
        this.count = count > 0 ? count : 2;
    }
    String summary() { return title + ":" + count; }
}
