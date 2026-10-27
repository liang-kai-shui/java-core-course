public class NameCard {
    public static void main(String[] args) {
        String original = "  Li Ming  ";
        String name = original.trim();
        System.out.println("name=" + name);
        System.out.println("length=" + name.length());
        System.out.println("prefix=" + name.substring(0, 2));
    }
}
