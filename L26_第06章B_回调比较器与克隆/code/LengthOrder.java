import java.util.Arrays;
import java.util.Comparator;
public class LengthOrder {
    public static void main(String[] args) {
        String[] words = {"bb", "a", "cc", "ddd"};
        Arrays.sort(words, new ByLength());
        System.out.println(Arrays.toString(words));
    }
}
class ByLength implements Comparator<String> {
    @Override public int compare(String first, String second) {
        return Integer.compare(first.length(), second.length());
    }
}
