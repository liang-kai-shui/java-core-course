public class StartVersusRun {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> System.out.println(Thread.currentThread().getName().equals("main") ? "run:main" : "start:helper");
        task.run();
        Thread worker = new Thread(task, "helper"); worker.start(); worker.join(); System.out.println("joined");
    }
}
