public class MonitorOneShot {
    static class Result {
        private boolean ready; private String text;
        synchronized void set(String value) { text = value; ready = true; notifyAll(); }
        synchronized String get() throws InterruptedException { while (!ready) wait(); return text; }
    }
    public static void main(String[] args) throws InterruptedException {
        Result result = new Result(); Thread producer = new Thread(() -> result.set("数组")); producer.start();
        System.out.println("ready:" + result.get()); producer.join();
    }
}
