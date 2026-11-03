import java.util.concurrent.*;
public class CompletionOrderGate {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(2); CountDownLatch gate = new CountDownLatch(1);
        try {
            CompletionService<String> completed = new ExecutorCompletionService<>(pool);
            completed.submit(() -> { gate.await(); return "slow"; }); completed.submit(() -> "fast");
            System.out.println(completed.take().get()); gate.countDown(); System.out.println(completed.take().get());
        } finally { gate.countDown(); pool.shutdownNow(); if (!pool.awaitTermination(3, TimeUnit.SECONDS)) throw new IllegalStateException("pool still active"); }
    }
}
