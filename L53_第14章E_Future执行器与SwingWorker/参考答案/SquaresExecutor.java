import java.util.*;
import java.util.concurrent.*;
public class SquaresExecutor {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            List<Future<Integer>> results = new ArrayList<>(); for (int i = 1; i <= 3; i++) { final int n = i; results.add(pool.submit(() -> n * n)); }
            int sum = 0; for (Future<Integer> result : results) sum += result.get(); System.out.println(sum);
            Future<Integer> bad = pool.submit(() -> { throw new IllegalStateException("demo"); });
            try { bad.get(); } catch (ExecutionException e) { System.out.println(e.getCause().getClass().getSimpleName()); }
        } finally { pool.shutdownNow(); if (!pool.awaitTermination(3, TimeUnit.SECONDS)) throw new IllegalStateException("pool still active"); }
    }
}
