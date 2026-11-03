import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
public class ListCursorGaps {
    public static void main(String[] args) {
        List<String> values = new LinkedList<>(Arrays.asList("A", "C"));
        ListIterator<String> it = values.listIterator();
        System.out.println(it.next());
        it.add("B");
        System.out.println(it.next());
        it.set("c");
        System.out.println(values);
        System.out.println(it.previous());
        it.remove();
        System.out.println(values);
        System.out.println("nextIndex=" + it.nextIndex());
    }
}
