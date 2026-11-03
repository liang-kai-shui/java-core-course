public class ParameterFrames {
    static void triple(int received) {
        System.out.println("inside-before=" + received);
        received = received * 3;
        System.out.println("inside-after=" + received);
    }
    public static void main(String[] args) {
        int original = 7;
        triple(original);
        System.out.println("main-after=" + original);
    }
}
