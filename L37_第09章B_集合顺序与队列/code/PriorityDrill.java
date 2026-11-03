import java.util.Arrays;
import java.util.PriorityQueue;
public class PriorityDrill {
    public static void main(String[] args) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.addAll(Arrays.asList(3, 1, 2, 1));
        while (!queue.isEmpty()) System.out.println(queue.poll());
        System.out.println(queue.poll());
    }
}
