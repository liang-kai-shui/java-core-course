public class SynchronizedCounter {
    static class Counter { private int count; synchronized void add() { count++; } synchronized int get() { return count; } }
    public static void main(String[] args) throws InterruptedException {
        Counter c = new Counter(); Runnable task = () -> { for (int i = 0; i < 1000; i++) c.add(); };
        Thread a = new Thread(task), b = new Thread(task); a.start(); b.start(); a.join(); b.join(); System.out.println(c.get());
    }
}
