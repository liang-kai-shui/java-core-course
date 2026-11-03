import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;
public class PredicateAndOrdering {
    public static void main(String[] args) {
        ArrayList<String> words = new ArrayList<>(Arrays.asList("bb", "", null, "aa", "b"));
        Predicate<String> empty = s -> s != null && s.isEmpty();
        words.removeIf(empty);
        Comparator<String> core = Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder());
        words.sort(Comparator.nullsLast(core));
        System.out.println(words);
    }
}
