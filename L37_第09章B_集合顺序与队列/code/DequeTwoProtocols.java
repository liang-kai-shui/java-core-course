import java.util.ArrayDeque;
import java.util.Deque;
public class DequeTwoProtocols {
    public static void main(String[] args) {
        Deque<String> queue = new ArrayDeque<>();
        queue.offerLast("A"); queue.offerLast("B");
        System.out.println(queue.pollFirst()); System.out.println(queue.pollFirst());
        queue.push("A"); queue.push("B");
        System.out.println(queue.pop()); System.out.println(queue.pop());
        System.out.println(queue.poll());
    }
}
