import java.util.concurrent.locks.ReentrantLock;
public class LockedCredits {
    static class Ledger {
        private final long[] credits = {1000, 1000}; private final ReentrantLock lock = new ReentrantLock();
        boolean transfer(int from, int to, long amount) {
            if (from < 0 || from >= credits.length || to < 0 || to >= credits.length || amount < 0) throw new IllegalArgumentException("bad transfer");
            lock.lock(); try { if (credits[from] < amount) return false; credits[from] -= amount; credits[to] += amount; return true; } finally { lock.unlock(); }
        }
        long total() { lock.lock(); try { long sum = 0; for (long n : credits) sum += n; return sum; } finally { lock.unlock(); } }
    }
    public static void main(String[] args) throws InterruptedException {
        Ledger ledger = new Ledger(); Thread a = new Thread(() -> { for (int i = 0; i < 1000; i++) ledger.transfer(0, 1, 1); });
        Thread b = new Thread(() -> { for (int i = 0; i < 1000; i++) ledger.transfer(1, 0, 1); }); a.start(); b.start(); a.join(); b.join();
        System.out.println(ledger.total()); System.out.println(ledger.transfer(0, 1, 0));
        try { ledger.transfer(0, 1, -1); } catch (IllegalArgumentException e) { System.out.println("negative-rejected"); }
    }
}
