import java.util.concurrent.CountDownLatch;
public class LostUpdateSchedule {
    public static void main(String[] args) throws InterruptedException {
        int[] count = {0}; CountDownLatch bothRead = new CountDownLatch(2);
        Runnable increment = () -> {
            int old = count[0]; bothRead.countDown();
            try { bothRead.await(); count[0] = old + 1; } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        };
        Thread a = new Thread(increment), b = new Thread(increment); a.start(); b.start(); a.join(); b.join(); System.out.println("count=" + count[0]);
    }
}
