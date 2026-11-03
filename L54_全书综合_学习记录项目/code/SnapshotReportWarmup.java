import java.util.*;
import java.util.concurrent.*;
public class SnapshotReportWarmup {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Integer> live = new ArrayList<>(Arrays.asList(30, 20)); List<Integer> snapshot = new ArrayList<>(live); live.add(1000);
        ExecutorService pool = Executors.newSingleThreadExecutor();
        try { Future<Integer> result = pool.submit(() -> { int sum = 0; for (int n : snapshot) sum += n; return sum; }); System.out.println(result.get()); System.out.println("live-size=" + live.size()); }
        finally { pool.shutdownNow(); if (!pool.awaitTermination(3, TimeUnit.SECONDS)) throw new IllegalStateException("pool active"); }
    }
}
