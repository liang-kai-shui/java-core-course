public class AreaCheckpoints {
    static int area(int width, int height) { return width * height; }
    static void check(int width, int height, int expected) {
        int actual = area(width, height);
        System.out.println(width + ":" + height + " expected=" + expected + " actual=" + actual + " mismatch=" + (actual != expected));
    }
    public static void main(String[] args) {
        check(2, 3, 6);
        check(0, 3, 0);
    }
}
