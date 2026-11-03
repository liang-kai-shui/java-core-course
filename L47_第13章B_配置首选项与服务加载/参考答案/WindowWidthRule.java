public class WindowWidthRule {
    static int width(String text) {
        if (text == null) return 300;
        try { int n = Integer.parseInt(text.trim()); return n >= 200 && n <= 1600 ? n : 300; }
        catch (NumberFormatException e) { return 300; }
    }
    public static void main(String[] args) {
        for (String text : new String[] {null, "abc", "199", "200", "1600", "1601"}) System.out.println(width(text));
    }
}
