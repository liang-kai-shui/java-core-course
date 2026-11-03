import java.util.Arrays;
public class ParallelArraySteps {
    public static void main(String[] args) {
        int[] sorted = {3, 1, 2}; Arrays.parallelSort(sorted); System.out.println(Arrays.toString(sorted));
        int[] generated = new int[3]; Arrays.parallelSetAll(generated, i -> i * 2); System.out.println(Arrays.toString(generated));
        int[] prefix = {1, 2, 3}; Arrays.parallelPrefix(prefix, Integer::sum); System.out.println(Arrays.toString(prefix));
    }
}
