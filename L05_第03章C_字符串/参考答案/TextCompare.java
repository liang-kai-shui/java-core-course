public class TextCompare {
    public static void main(String[] args) {
        String a = "Java";
        String b = new String("Java");
        System.out.println(a == b);
        System.out.println(a.equals(b));
        StringBuilder builder = new StringBuilder();
        builder.append("Java");
        builder.append(" ");
        builder.append("course");
        System.out.println(builder.toString());
    }
}
