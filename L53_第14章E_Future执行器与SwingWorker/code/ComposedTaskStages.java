import java.util.concurrent.*;
public class ComposedTaskStages {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        try {
            CompletableFuture<String> name = CompletableFuture.supplyAsync(() -> " 数组 ", pool);
            CompletableFuture<String> label = name.thenApply(String::trim).thenCompose(s -> CompletableFuture.supplyAsync(() -> "[" + s + "]", pool)); System.out.println(label.get());
            CompletableFuture<String> failed = new CompletableFuture<>(); failed.completeExceptionally(new IllegalArgumentException("demo"));
            System.out.println(failed.handle((value, error) -> error == null ? value : "fallback").get());
            CompletableFuture<Integer> a = CompletableFuture.completedFuture(1), b = CompletableFuture.completedFuture(2);
            System.out.println(a.thenCombine(b, Integer::sum).get()); CompletableFuture.allOf(a, b).get();
            CompletableFuture<Object> either = CompletableFuture.anyOf(CompletableFuture.completedFuture("first"), new CompletableFuture<String>()); System.out.println(either.get());
        } finally { pool.shutdownNow(); if (!pool.awaitTermination(3, TimeUnit.SECONDS)) throw new IllegalStateException("pool active"); }
    }
}
