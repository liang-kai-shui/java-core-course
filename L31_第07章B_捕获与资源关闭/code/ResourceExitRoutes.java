import java.io.IOException;
public class ResourceExitRoutes {
    static void use(boolean failBody, boolean failClose) {
        try (TraceResource a = new TraceResource("A", false);
             TraceResource b = new TraceResource("B", failClose)) {
            a.use(); b.use();
            System.out.println("work");
            if (failBody) throw new IOException("body");
        } catch (IOException ex) {
            System.out.println("caught:" + ex.getMessage() + " suppressed=" + ex.getSuppressed().length);
        } finally {
            System.out.println("finally");
        }
        System.out.println("continue");
    }
    public static void main(String[] args) {
        use(false, false);
        System.out.println("---");
        use(true, true);
    }
}
class TraceResource implements AutoCloseable {
    private final String name;
    private final boolean failClose;
    private boolean closed;
    TraceResource(String name, boolean failClose) {
        this.name = name; this.failClose = failClose;
        System.out.println("open:" + name);
    }
    void use() { if (closed) throw new IllegalStateException("closed resource"); }
    @Override public void close() throws IOException {
        closed = true;
        System.out.println("close:" + name);
        if (failClose) throw new IOException("close-" + name);
    }
}
