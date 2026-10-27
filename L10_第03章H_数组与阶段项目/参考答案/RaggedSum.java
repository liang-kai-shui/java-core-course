public class RaggedSum {
    public static void main(String[] args) {
        int[][] values = {{1, 2}, {3, 4, 5}, {}};
        int sum = 0;
        for (int row = 0; row < values.length; row++) {
            for (int column = 0; column < values[row].length; column++) {
                sum += values[row][column];
            }
        }
        System.out.println("sum=" + sum);
    }
}
