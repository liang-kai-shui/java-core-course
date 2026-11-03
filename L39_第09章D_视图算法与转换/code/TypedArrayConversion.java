import java.util.Arrays;
import java.util.List;
public class TypedArrayConversion {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("A", "B");
        String[] typed = words.toArray(new String[0]);
        System.out.println(Arrays.toString(typed));
        String[] large = {"old", "old", "old", "old"};
        String[] returned = words.toArray(large);
        System.out.println(Arrays.toString(large));
        System.out.println(returned == large);
    }
}
