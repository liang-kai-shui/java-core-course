import java.util.Arrays;
public class ArrayDemo {
    public static void main(String[] args) {
        int[] a = {3, 1, 2};
        int[] alias = a;
        alias[0] = 9;
        int[] copy = Arrays.copyOf(a, a.length);
        Arrays.sort(copy);
        System.out.println("original=" + Arrays.toString(a));
        System.out.println("sorted copy=" + Arrays.toString(copy));
        for (int value : a) {
            value++;
        }
        System.out.println("after foreach=" + Arrays.toString(a));
        System.out.println("index of 2=" + Arrays.binarySearch(copy, 2));
    }
}
