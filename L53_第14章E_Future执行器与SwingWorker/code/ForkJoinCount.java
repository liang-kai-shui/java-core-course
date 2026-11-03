import java.util.concurrent.*;
public class ForkJoinCount {
    static class Count extends RecursiveTask<Integer> {
        private static final long serialVersionUID = 1L;
        final int[] values; final int from, to;
        Count(int[] values, int from, int to) { this.values = values; this.from = from; this.to = to; }
        protected Integer compute() {
            if (to - from <= 2) { int count = 0; for (int i = from; i < to; i++) if (values[i] >= 4) count++; return count; }
            int mid = from + (to - from) / 2; Count left = new Count(values, from, mid), right = new Count(values, mid, to);
            invokeAll(left, right); return left.join() + right.join();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool(2);
        try { int[] values = {1, 4, 2, 8}; System.out.println(pool.invoke(new Count(values, 0, values.length))); System.out.println(pool.invoke(new Count(new int[0], 0, 0))); }
        finally { pool.shutdown(); if (!pool.awaitTermination(3, TimeUnit.SECONDS)) { pool.shutdownNow(); throw new IllegalStateException("fork pool active"); } }
    }
}
