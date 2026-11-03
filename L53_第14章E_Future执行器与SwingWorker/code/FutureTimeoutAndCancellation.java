import java.util.concurrent.*;
public class FutureTimeoutAndCancellation {
    public static void main(String[] args) throws InterruptedException {
        FutureTask<Integer> pending = new FutureTask<>(() -> 7);
        try { pending.get(0, TimeUnit.MILLISECONDS); } catch (TimeoutException e) { System.out.println("timeout"); } catch (ExecutionException e) { throw new AssertionError(e); }
        System.out.println("cancelled=" + pending.isCancelled()); pending.cancel(true);
        try { pending.get(); } catch (CancellationException e) { System.out.println("cancelled"); } catch (ExecutionException e) { throw new AssertionError(e); }
        FutureTask<Integer> interruptedTask = new FutureTask<>(() -> { throw new InterruptedException("task failure"); }); Thread worker = new Thread(interruptedTask); worker.start();
        try { interruptedTask.get(); } catch (ExecutionException e) { System.out.println("task-cause=" + e.getCause().getClass().getSimpleName()); } finally { worker.join(); }
    }
}
