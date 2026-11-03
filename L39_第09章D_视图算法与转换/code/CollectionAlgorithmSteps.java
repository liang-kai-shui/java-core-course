import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class CollectionAlgorithmSteps {
    public static void main(String[] args) {
        List<String> target = new ArrayList<>(Collections.nCopies(3, "?"));
        Collections.copy(target, Arrays.asList("A", "B"));
        System.out.println(target);
        Collections.reverse(target);
        System.out.println(target);
        int found = Collections.binarySearch(Arrays.asList(10, 20, 30), 25);
        System.out.println(-found - 1);
    }
}
