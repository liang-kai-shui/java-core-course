import java.util.Arrays;
public class SafeElementSwap {
    static boolean swap(int[] values, int i, int j) {
        if (values == null || i < 0 || j < 0 || i >= values.length || j >= values.length) return false;
        int saved = values[i]; values[i] = values[j]; values[j] = saved;
        return true;
    }
    public static void main(String[] args) {
        int[] values = {4, 9, 7};
        System.out.println(swap(values, 0, 2));
        System.out.println(Arrays.toString(values));
        System.out.println(swap(values, 1, 1));
        System.out.println(swap(values, -1, 0));
        System.out.println(swap(values, 0, 3));
        System.out.println(Arrays.toString(values));
        System.out.println(swap(new int[0], 0, 0));
        System.out.println(swap(null, 0, 0));
    }
}
