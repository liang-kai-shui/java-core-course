import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class SortedInsert {
    static List<Integer> insert(List<Integer> original, int value) {
        List<Integer> copy = new ArrayList<>(original); Collections.sort(copy);
        int found = Collections.binarySearch(copy, value);
        if (found < 0) copy.add(-found - 1, value);
        return copy;
    }
    public static void main(String[] args) {
        List<Integer> original = Arrays.asList(30, 10, 20);
        List<Integer> result = insert(original, 25);
        System.out.println(result); System.out.println(original);
        System.out.println(insert(Collections.emptyList(), 25));
        System.out.println(insert(original, 5)); System.out.println(insert(original, 40)); System.out.println(insert(original, 20));
        Integer[] values = result.toArray(new Integer[0]); System.out.println(Arrays.toString(values));
        String[] slots = {"old", "old", "old", "old"};
        Arrays.asList("A", "B").toArray(slots); System.out.println(Arrays.toString(slots));
    }
}
