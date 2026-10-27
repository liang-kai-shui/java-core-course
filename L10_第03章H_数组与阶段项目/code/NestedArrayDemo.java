public class NestedArrayDemo {
    public static void main(String[] args) {
        int[][] grid = {{1, 2}, {3, 4, 5}};
        int sum = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                sum += grid[row][column];
            }
        }
        System.out.println("sum=" + sum);
    }
}
