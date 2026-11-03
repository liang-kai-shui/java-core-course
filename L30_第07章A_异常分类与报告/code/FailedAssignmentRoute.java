public class FailedAssignmentRoute {
    public static void main(String[] args) {
        int saved = 7;
        try {
            saved = Integer.parseInt("bad");
            System.out.println("after");
        } catch (NumberFormatException ex) {
            System.out.println("caught");
        }
        System.out.println("saved=" + saved);
    }
}
