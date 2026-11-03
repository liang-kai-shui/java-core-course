import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
public class ScheduledTaskSteps {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        try {
            ScheduledFuture<Integer> once = scheduler.schedule(() -> 7, 0, TimeUnit.MILLISECONDS); System.out.println(once.get());
            CountDownLatch twice = new CountDownLatch(2); AtomicInteger count = new AtomicInteger();
            ScheduledFuture<?> repeating = scheduler.scheduleWithFixedDelay(() -> { count.incrementAndGet(); twice.countDown(); }, 0, 1, TimeUnit.MILLISECONDS);
            if (!twice.await(3, TimeUnit.SECONDS)) throw new AssertionError("not triggered"); repeating.cancel(true); System.out.println("repeated=" + (count.get() >= 2));
        } finally { scheduler.shutdownNow(); if (!scheduler.awaitTermination(3, TimeUnit.SECONDS)) throw new IllegalStateException("scheduler active"); }
    }
}
