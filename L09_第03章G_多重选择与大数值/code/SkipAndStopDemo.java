public class SkipAndStopDemo {
    public static void main(String[] args) {
        for (int value = 1; value <= 10; value++) {
            if (value > 7) {
                break;
            }
            if (value % 2 == 0) {
                continue;
            }
            System.out.println(value);
        }
    }
}
