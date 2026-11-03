public class InheritanceRoute {
    public static void main(String[] args) {
        LabCourse course = new LabCourse("实验课", 3, 2);
        System.out.println(course.title() + "=" + course.hours());
    }
}
class CourseBase {
    private final String title;
    private final int baseHours;
    CourseBase(String title, int baseHours) {
        this.title = title;
        this.baseHours = baseHours;
        System.out.println("parent:" + title);
    }
    public String title() { return title; }
    public int hours() { return baseHours; }
}
class LabCourse extends CourseBase {
    private final int labHours;
    LabCourse(String title, int baseHours, int labHours) {
        super(title, baseHours);
        this.labHours = labHours;
        System.out.println("child:" + labHours);
    }
    @Override public int hours() { return super.hours() + labHours; }
}
