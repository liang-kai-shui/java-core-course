import java.awt.EventQueue;
import javax.swing.Timer;
public class SessionLifecycle {
    static class Session {
        int count; boolean running, closed;
        final Timer timer = new Timer(1000, e -> count++);
        boolean start() { if (closed || running) return false; running = true; timer.start(); return true; }
        void pause() { timer.stop(); running = false; }
        void close() { pause(); closed = true; }
    }
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            Session s = new Session(); System.out.println(s.start()); System.out.println(s.start());
            s.pause(); System.out.println(s.start()); s.close(); System.out.println(s.start()); System.out.println(!s.timer.isRunning());
        });
    }
}
