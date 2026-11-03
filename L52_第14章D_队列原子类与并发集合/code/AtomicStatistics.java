import java.util.concurrent.atomic.*;
public class AtomicStatistics {
    public static void main(String[] args) throws InterruptedException {
        AtomicLong sequence = new AtomicLong(), maximum = new AtomicLong(Long.MIN_VALUE); LongAdder total = new LongAdder();
        Runnable low = () -> { for (int i = 0; i < 1000; i++) { final int observed = i; sequence.incrementAndGet(); maximum.updateAndGet(old -> Math.max(old, observed)); total.increment(); } };
        Runnable high = () -> { for (int i = 1000; i < 2000; i++) { final int observed = i; sequence.incrementAndGet(); maximum.accumulateAndGet(observed, Math::max); total.increment(); } };
        Thread a = new Thread(low), b = new Thread(high); a.start(); b.start(); a.join(); b.join();
        System.out.println(sequence.get()); System.out.println(maximum.get()); System.out.println(total.sum());
    }
}
