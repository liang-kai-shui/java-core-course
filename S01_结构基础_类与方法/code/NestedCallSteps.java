public class NestedCallSteps {
    public static void showCourse() { System.out.println("3.course"); }
    public static void prepare() {
        System.out.println("2.prepare");
        showCourse();
        System.out.println("4.prepare");
    }
    public static void main(String[] args) {
        System.out.println("1.main");
        prepare();
        System.out.println("5.main");
    }
}
