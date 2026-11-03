import java.util.concurrent.*;
public class SynchronizerShapes {
    public static void main(String[] args) throws InterruptedException {
        Semaphore permits = new Semaphore(2); Thread[] workers = new Thread[2];
        for (int i = 0; i < workers.length; i++) { workers[i] = new Thread(() -> { boolean got = false; try { permits.acquire(); got = true; } catch (InterruptedException e) { Thread.currentThread().interrupt(); } finally { if (got) permits.release(); } }); workers[i].start(); }
        for (Thread t : workers) t.join(); System.out.println("permits=" + permits.availablePermits());
        Exchanger<String> exchange = new Exchanger<>(); String[] received = new String[2];
        Thread left = new Thread(() -> { try { received[0] = exchange.exchange("left"); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } });
        Thread right = new Thread(() -> { try { received[1] = exchange.exchange("right"); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } });
        left.start(); right.start(); left.join(); right.join(); System.out.println(received[0] + ":" + received[1]);
        Phaser phase = new Phaser(1); phase.register(); Thread worker = new Thread(() -> { phase.arriveAndAwaitAdvance(); phase.arriveAndDeregister(); });
        worker.start(); phase.arriveAndAwaitAdvance(); worker.join(); System.out.println("phase=" + phase.getPhase()); phase.arriveAndDeregister(); System.out.println("terminated=" + phase.isTerminated());
    }
}
