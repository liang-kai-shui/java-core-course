import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
public class SetOrderChoices {
    public static void main(String[] args) {
        Set<String> arrival = new LinkedHashSet<>();
        System.out.println(arrival.add("bb"));
        System.out.println(arrival.add("a"));
        System.out.println(arrival.add("bb"));
        System.out.println(arrival);
        System.out.println(new TreeSet<>(arrival));
        Set<String> lengths = new TreeSet<>(Comparator.comparingInt(String::length));
        lengths.add("a"); lengths.add("b");
        System.out.println(lengths);
    }
}
