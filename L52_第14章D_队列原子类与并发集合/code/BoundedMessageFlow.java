import java.util.concurrent.*;
public class BoundedMessageFlow {
    static class Message { final int number; Message(int number) { this.number = number; } }
    static final Message END = new Message(0);
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Message> queue = new ArrayBlockingQueue<>(2); int[] sum = {0}; Thread[] peers = new Thread[2];
        peers[0] = new Thread(() -> {
            try { for (int n = 1; n <= 3; n++) queue.put(new Message(n)); queue.put(END); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); peers[1].interrupt(); }
        });
        peers[1] = new Thread(() -> {
            try { while (true) { Message m = queue.take(); if (m == END) break; sum[0] += m.number; } }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); peers[0].interrupt(); }
        });
        peers[0].start(); peers[1].start();
        try { peers[0].join(); peers[1].join(); System.out.println("sum=" + sum[0]); }
        finally { peers[0].interrupt(); peers[1].interrupt(); peers[0].join(); peers[1].join(); }
    }
}
