public class MoodCounter {
    static int countPositive(int... values) {
        int count = 0;
        for (int value : values) if (value > 0) count++;
        return count;
    }
    public static void main(String[] args) {
        System.out.println(countPositive());
        System.out.println(countPositive(-1, 0, 2, 3));
        for (Mood mood : Mood.values()) System.out.println(mood.name() + ":" + mood.label());
        System.out.println(countPositive(new int[] {0, -2}));
        System.out.println(countPositive(Integer.MAX_VALUE, 1));
    }
}
enum Mood {
    CALM("平静"), FOCUSED("专注"), TIRED("疲惫");
    private final String label;
    Mood(String label) { this.label = label; }
    String label() { return label; }
}
