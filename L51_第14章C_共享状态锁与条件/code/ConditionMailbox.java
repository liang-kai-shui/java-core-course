import java.util.concurrent.locks.*;
public class ConditionMailbox {
    static class Box {
        private final ReentrantLock lock = new ReentrantLock(); private final Condition changed = lock.newCondition();
        private Integer value; private boolean closed;
        void put(Integer item) throws InterruptedException {
            if (item == null) throw new IllegalArgumentException("null item"); lock.lockInterruptibly();
            try { while (value != null && !closed) changed.await(); if (closed) throw new IllegalStateException("closed"); value = item; changed.signalAll(); } finally { lock.unlock(); }
        }
        Integer take() throws InterruptedException {
            lock.lockInterruptibly(); try { while (value == null && !closed) changed.await(); Integer item = value; value = null; changed.signalAll(); return item; } finally { lock.unlock(); }
        }
        void close() { lock.lock(); try { closed = true; changed.signalAll(); } finally { lock.unlock(); } }
    }
    public static void main(String[] args) throws InterruptedException {
        Box box = new Box(); int[] sum = {0};
        Thread consumer = new Thread(() -> { try { Integer n; while ((n = box.take()) != null) sum[0] += n; } catch (InterruptedException e) { Thread.currentThread().interrupt(); } });
        Thread producer = new Thread(() -> { try { box.put(1); box.put(2); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } finally { box.close(); } });
        consumer.start(); producer.start(); producer.join(); consumer.join(); System.out.println("sum=" + sum[0]);
        Box empty = new Box(); empty.close(); System.out.println("closed=" + (empty.take() == null));
    }
}
