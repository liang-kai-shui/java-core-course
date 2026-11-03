public class ExplicitCloseWalk {
    public static void main(String[] args) {
        try (DemoHandle handle = new DemoHandle()) {
            handle.work();
        }
        System.out.println("after");
    }
}
class DemoHandle implements AutoCloseable {
    private boolean closed;
    DemoHandle() { System.out.println("open"); }
    void work() {
        if (closed) throw new IllegalStateException("closed");
        System.out.println("work");
    }
    public void close() { closed = true; System.out.println("closed"); }
}
