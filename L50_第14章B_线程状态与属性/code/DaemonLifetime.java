import java.util.concurrent.CountDownLatch;
public class DaemonLifetime {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch ready = new CountDownLatch(1), gate = new CountDownLatch(1);
        Thread helper = new Thread(() -> {
            ready.countDown(); try { gate.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        helper.setDaemon(true); helper.start(); ready.await();
        if (args.length > 0 && args[0].equals("--abandon")) { System.out.println("main-return"); return; }
        System.out.println("daemon=" + helper.isDaemon()); gate.countDown(); helper.join(); System.out.println("clean-finish");
    }
}
