import java.util.concurrent.*;
public class LatchAndBarrierRounds {
    public static void main(String[] args) throws InterruptedException {
        int[] parts = new int[2]; CountDownLatch done = new CountDownLatch(2); Thread[] first = new Thread[2];
        for (int i = 0; i < 2; i++) { final int id = i; first[i] = new Thread(() -> { try { parts[id] = id + 1; } finally { done.countDown(); } }); first[i].start(); }
        done.await(); for (Thread t : first) t.join(); System.out.println("latch=" + (parts[0] + parts[1]));
        CyclicBarrier barrier = new CyclicBarrier(2, () -> System.out.println("round=" + (parts[0] + parts[1]))); Thread[] rounds = new Thread[2];
        for (int i = 0; i < 2; i++) { final int id = i; rounds[i] = new Thread(() -> {
            try { for (int round = 1; round <= 2; round++) { parts[id] = id + round; barrier.await(); } }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); } catch (BrokenBarrierException e) { throw new IllegalStateException(e); }
        }); rounds[i].start(); }
        for (Thread t : rounds) t.join();
    }
}
