import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
public class BlankFilter {
    static void removeBlanks(List<String> words) {
        Iterator<String> it = words.iterator();
        while (it.hasNext()) if (it.next().isEmpty()) it.remove();
    }
    public static void main(String[] args) {
        List<String> words = new ArrayList<>(Arrays.asList("A", "", "", "B"));
        removeBlanks(words); System.out.println(words);
        ListIterator<String> it = words.listIterator();
        it.next(); it.add("X"); it.next(); it.set("b"); System.out.println(words);
        List<String> empty = new ArrayList<>(); removeBlanks(empty); System.out.println(empty);
        List<String> blanks = new ArrayList<>(Arrays.asList("", "")); removeBlanks(blanks); System.out.println(blanks);
    }
}
