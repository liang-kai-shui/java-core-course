public class InterruptPropagation {
    static void subTask() throws InterruptedException { Thread.currentThread().interrupt(); Thread.sleep(1000); }
    public static void main(String[] args) {
        Runnable task = () -> {
            try { subTask(); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); System.out.println("cancelled=" + Thread.currentThread().isInterrupted()); }
            finally { System.out.println("released"); }
        };
        task.run(); Thread.interrupted();
    }
}
