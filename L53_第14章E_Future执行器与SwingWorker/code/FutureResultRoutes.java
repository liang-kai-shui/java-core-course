import java.util.concurrent.*;
public class FutureResultRoutes {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<Integer> good = new FutureTask<>(() -> 6 * 7); Thread a = new Thread(good); a.start();
        try { System.out.println(good.get()); } finally { a.join(); }
        FutureTask<Integer> bad = new FutureTask<>(() -> { throw new IllegalArgumentException("demo"); }); Thread b = new Thread(bad); b.start();
        try { bad.get(); } catch (ExecutionException e) { System.out.println(e.getCause().getClass().getSimpleName()); } finally { b.join(); }
    }
}
