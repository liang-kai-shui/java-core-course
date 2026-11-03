import java.util.Arrays;
import java.util.List;
public class WildcardCaptureSwap {
    static void swap(List<?> values) { capture(values); }
    private static <T> void capture(List<T> values) {
        if (values.size() < 2) throw new IllegalArgumentException("need two");
        T temp = values.get(0);
        values.set(0, values.get(1));
        values.set(1, temp);
    }
    public static void main(String[] args) {
        List<String> values = Arrays.asList("A", "B");
        swap(values);
        System.out.println(values);
    }
}
