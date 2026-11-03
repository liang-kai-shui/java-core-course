import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
public class IteratorFilterWalk {
    public static void main(String[] args) {
        List<String> steps = new ArrayList<>(Arrays.asList("读", "skip", "练"));
        Iterator<String> it = steps.iterator();
        System.out.println(it.hasNext());
        System.out.println(it.hasNext());
        while (it.hasNext()) {
            String step = it.next();
            System.out.println("visit:" + step);
            if (step.equals("skip")) it.remove();
        }
        System.out.println(steps);
    }
}
