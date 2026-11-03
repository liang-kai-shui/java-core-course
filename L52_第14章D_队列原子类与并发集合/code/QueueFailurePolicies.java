import java.util.NoSuchElementException;
import java.util.concurrent.*;
public class QueueFailurePolicies {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> q = new ArrayBlockingQueue<>(1);
        System.out.println(q.offer("A")); System.out.println(q.offer("B")); System.out.println(q.poll()); System.out.println(q.poll());
        try { q.remove(); } catch (NoSuchElementException e) { System.out.println("empty-error"); }
        q.put("C"); System.out.println(q.offer("D", 0, TimeUnit.MILLISECONDS));
    }
}
