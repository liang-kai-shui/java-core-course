import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
public class IteratorInvalidation {
    public static void main(String[] args) {
        List<String> values = new ArrayList<>(Arrays.asList("A", "B"));
        Iterator<String> it = values.iterator();
        // controlled external change here
        System.out.println(it.next());
    }
}
