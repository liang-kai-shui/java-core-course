import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
public class ConcurrentWordUpdates {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<String, Long> counts = new ConcurrentHashMap<>(); ConcurrentHashMap<String, LongAdder> adders = new ConcurrentHashMap<>();
        Runnable task = () -> { for (int i = 0; i < 1000; i++) { counts.merge("java", 1L, Long::sum); adders.computeIfAbsent("java", k -> new LongAdder()).increment(); } };
        Thread a = new Thread(task), b = new Thread(task); a.start(); b.start(); a.join(); b.join(); System.out.println(counts.get("java")); System.out.println(adders.get("java").sum());
        ConcurrentHashMap<String, LongAdder> fresh = new ConcurrentHashMap<>(); LongAdder old = fresh.putIfAbsent("java", new LongAdder()); System.out.println(old == null);
        try { old.increment(); } catch (NullPointerException expected) { System.out.println("first-return-is-null"); }
    }
}
