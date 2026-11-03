public class TextResultPractice {
    static String addMark(String text) { return text + "!"; }
    public static void main(String[] args) {
        String source = "Hi";
        String alias = source;
        addMark(source);
        System.out.println(source);
        source = addMark(source);
        System.out.println(source);
        System.out.println(alias);
        System.out.println(addMark(""));
        System.out.println(addMark("A B"));
    }
}
