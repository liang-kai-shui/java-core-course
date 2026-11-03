public class LoaderFailure {
    public static void main(String[] args) throws InterruptedException {
        Thread loader = new Thread(() -> { throw new IllegalArgumentException("demo"); }, "loader");
        loader.setUncaughtExceptionHandler((thread, error) -> System.out.println(thread.getName() + ":" + error.getClass().getSimpleName()));
        loader.start(); loader.join(); System.out.println("main-alive");
    }
}
