import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class FixedViewAndSnapshot {
    public static void main(String[] args) {
        String[] array = {"A", "B"};
        List<String> fixed = Arrays.asList(array);
        List<String> readonly = Collections.unmodifiableList(fixed);
        fixed.set(0, "X");
        List<String> snapshot = new ArrayList<>(fixed);
        System.out.println(Arrays.toString(array));
        array[1] = "Y";
        System.out.println(readonly);
        System.out.println(snapshot);
    }
}
