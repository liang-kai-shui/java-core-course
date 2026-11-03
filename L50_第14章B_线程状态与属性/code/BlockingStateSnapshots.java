import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
public class BlockingStateSnapshots {
    static void observe(Thread thread, Thread.State expected) {
        long begin = System.nanoTime();
        while (thread.getState() != expected) {
            if (System.nanoTime() - begin > TimeUnit.SECONDS.toNanos(3)) throw new AssertionError("state not observed: " + expected);
            Thread.yield();
        }
        System.out.println(expected);
    }
    public static void main(String[] args) throws InterruptedException {
        Object monitor = new Object(); Thread blocked = new Thread(() -> { synchronized (monitor) { } });
        try { synchronized (monitor) { blocked.start(); observe(blocked, Thread.State.BLOCKED); } } finally { blocked.join(); }
        CountDownLatch gate = new CountDownLatch(1);
        Thread waiting = new Thread(() -> { try { gate.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } });
        waiting.start(); try { observe(waiting, Thread.State.WAITING); } finally { gate.countDown(); waiting.join(); }
        Thread sleeping = new Thread(() -> { try { Thread.sleep(60000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } });
        sleeping.start(); try { observe(sleeping, Thread.State.TIMED_WAITING); } finally { sleeping.interrupt(); sleeping.join(); }
    }
}
