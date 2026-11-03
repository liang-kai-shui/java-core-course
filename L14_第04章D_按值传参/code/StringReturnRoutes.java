public class StringReturnRoutes {
    static void appendLocally(String received) {
        received = received + "!";
        System.out.println("inside=" + received);
    }
    static String withMark(String received) { return received + "!"; }
    public static void main(String[] args) {
        String text = "Java";
        appendLocally(text);
        System.out.println("main=" + text);
        String result = withMark(text);
        System.out.println("result=" + result);
        System.out.println("still=" + text);
    }
}
