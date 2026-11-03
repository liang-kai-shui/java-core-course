public class PlanConstructors {
    public static void main(String[] args) {
        System.out.println(new LessonPlan().summary());
        System.out.println(new LessonPlan("字符串").summary());
        System.out.println(new LessonPlan("数组", 3).summary());
    }
}
class LessonPlan {
    private final String title;
    private final int hours;
    LessonPlan() { this("未命名", 1); }
    LessonPlan(String title) { this(title, 1); }
    LessonPlan(String title, int hours) {
        this.title = title;
        this.hours = hours > 0 ? hours : 1;
    }
    String summary() { return title + ":" + hours; }
}
