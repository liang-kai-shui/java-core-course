public class FilteredSum {
    public static void main(String[] args) {
        int sum = 0;
        for (int value = 1; value <= 20; value++) {
            if (value > 15) { break; }
            if (value % 3 == 0) { continue; }
            sum += value;
        }
        System.out.println("sum=" + sum);
    }
}
