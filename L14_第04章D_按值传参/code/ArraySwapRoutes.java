import java.util.Arrays;
public class ArraySwapRoutes {
    static void swapVariables(int[] first, int[] second) {
        int[] saved = first;
        first = second;
        second = saved;
    }
    static boolean swapElements(int[] values, int i, int j) {
        if (values == null || i < 0 || j < 0 || i >= values.length || j >= values.length) return false;
        int saved = values[i];
        values[i] = values[j];
        values[j] = saved;
        return true;
    }
    public static void main(String[] args) {
        int[] left = {1, 2};
        int[] right = {8, 9};
        swapVariables(left, right);
        System.out.println(Arrays.toString(left) + "/" + Arrays.toString(right));
        System.out.println(swapElements(left, 0, 1));
        System.out.println(Arrays.toString(left));
        System.out.println(swapElements(left, 1, 1));
        System.out.println(swapElements(left, 0, 2));
        System.out.println(Arrays.toString(left));
    }
}
