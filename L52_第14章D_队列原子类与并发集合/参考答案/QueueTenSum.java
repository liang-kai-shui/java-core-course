import java.util.concurrent.*;
public class QueueTenSum {
    static class Item { final int n; Item(int n) { this.n = n; } }
    static final Item END = new Item(0);
    public static void main(String[] args) throws InterruptedException {
        int capacity = args.length == 0 ? 1 : Integer.parseInt(args[0]); BlockingQueue<Item> q = new ArrayBlockingQueue<>(capacity); int[] sum = {0};
        Thread consumer = new Thread(() -> { try { while (true) { Item item = q.take(); if (item == END) break; sum[0] += item.n; } } catch (InterruptedException e) { Thread.currentThread().interrupt(); } });
        consumer.start(); try { for (int n = 1; n <= 10; n++) q.put(new Item(n)); q.put(END); consumer.join(); System.out.println(sum[0]); }
        finally { consumer.interrupt(); consumer.join(); }
    }
}
