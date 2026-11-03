import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class WildcardCopy {
    static <T> void copyInto(List<? super T> target, List<? extends T> source) {
        for (T value : source) target.add(value);
    }
    public static void main(String[] args) {
        List<Integer> source = Arrays.asList(1, 2);
        List<Number> target = new ArrayList<>();
        copyInto(target, source);
        System.out.println(target);
        List<? super Integer> sink = target;
        sink.add(3);
        System.out.println(target);
    }
}
