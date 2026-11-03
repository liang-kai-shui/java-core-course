import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
public class TimedLockAttempt {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(); lock.lock();
        try {
            Thread worker = new Thread(() -> {
                boolean got = false;
                try { got = lock.tryLock(1, TimeUnit.MILLISECONDS); System.out.println("acquired=" + got); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                finally { if (got) lock.unlock(); }
            }); worker.start(); worker.join();
        } finally { lock.unlock(); }
    }
}
