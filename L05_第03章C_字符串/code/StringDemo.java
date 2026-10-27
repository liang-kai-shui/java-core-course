public class StringDemo {
    public static void main(String[] args) {
        String original = "  Java  ";
        original.trim(); // 忽略返回值，不改变original引用的内容。
        System.out.println("original=[" + original + "]");
        String cleaned = original.trim();
        System.out.println("cleaned=[" + cleaned + "]");
        System.out.println("part=" + cleaned.substring(1, 3));
        String other = new String("Java");
        System.out.println("same reference=" + (cleaned == other));
        System.out.println("same content=" + cleaned.equals(other));
        System.out.println("score=" + 80 + 5);
        System.out.println("score=" + (80 + 5));
        StringBuilder builder = new StringBuilder();
        builder.append("Java");
        builder.append(" course");
        System.out.println(builder.toString());
    }
}
