import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
public class UniqueNames {
    public static void main(String[] args) {
        Set<String> names = new LinkedHashSet<>(Arrays.asList("梅", "林", "梅", "周"));
        System.out.println(names);
        Set<String> words = new TreeSet<>(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()));
        words.addAll(Arrays.asList("bb", "a", "cc", "a"));
        System.out.println(words);
        System.out.println(new LinkedHashSet<String>());
        System.out.println(new TreeSet<String>());
    }
}
