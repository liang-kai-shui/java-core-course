import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
public class ParentComparableMinimum {
    static <T extends Comparable<? super T>> T min(List<T> values) {
        if (values.isEmpty()) throw new IllegalArgumentException("empty");
        T best = values.get(0);
        for (T value : values) if (value.compareTo(best) < 0) best = value;
        return best;
    }
    public static void main(String[] args) {
        System.out.println(min(Arrays.asList(LocalDate.of(2025, 2, 1), LocalDate.of(2025, 1, 1))));
    }
}
