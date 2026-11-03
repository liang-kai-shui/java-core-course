import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class SubListDeletion {
    public static void main(String[] args) {
        List<String> source = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        List<String> window = source.subList(1, 3);
        System.out.println(window);
        window.clear();
        System.out.println(source);
        System.out.println(window);
    }
}
