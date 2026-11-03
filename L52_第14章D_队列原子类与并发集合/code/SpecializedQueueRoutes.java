import java.util.concurrent.*;
public class SpecializedQueueRoutes {
    static class Due implements Delayed {
        final String name; final long deadline;
        Due(String name, long millis) { this.name = name; deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(millis); }
        public long getDelay(TimeUnit unit) { return unit.convert(deadline - System.nanoTime(), TimeUnit.NANOSECONDS); }
        public int compareTo(Delayed other) { return Long.compare(deadline, ((Due) other).deadline); }
    }
    public static void main(String[] args) throws InterruptedException {
        PriorityBlockingQueue<Integer> priority = new PriorityBlockingQueue<>(1); priority.put(2); priority.put(1); System.out.println(priority.take() + ":" + priority.take());
        DelayQueue<Due> delays = new DelayQueue<>(); delays.put(new Due("future", 60000)); System.out.println(delays.poll()); delays.put(new Due("ready", -1)); System.out.println(delays.take().name);
        LinkedTransferQueue<Integer> transfer = new LinkedTransferQueue<>(); int[] value = {0}; Thread consumer = new Thread(() -> { try { value[0] = transfer.take(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } });
        consumer.start(); try { transfer.transfer(5); consumer.join(); System.out.println(value[0]); } finally { consumer.interrupt(); consumer.join(); }
    }
}
