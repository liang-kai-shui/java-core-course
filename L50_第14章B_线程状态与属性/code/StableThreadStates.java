public class StableThreadStates {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> { }); System.out.println(worker.getState());
        worker.start(); worker.join(); System.out.println(worker.getState());
    }
}
