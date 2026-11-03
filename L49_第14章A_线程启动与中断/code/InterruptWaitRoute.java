import java.util.concurrent.CountDownLatch;
public class InterruptWaitRoute {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch ready = new CountDownLatch(1), neverOpen = new CountDownLatch(1);
        Thread worker = new Thread(() -> {
            try { ready.countDown(); neverOpen.await(); }
            catch (InterruptedException e) {
                System.out.println("catch=" + Thread.currentThread().isInterrupted()); Thread.currentThread().interrupt();
                System.out.println("restored=" + Thread.currentThread().isInterrupted());
            } finally { System.out.println("cleanup"); }
        }, "waiting-worker");
        worker.start(); ready.await(); worker.interrupt(); worker.join(); System.out.println("done");
    }
}
