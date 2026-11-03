import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
public class SwingTimerTicks {
    static class TickCounter implements ActionListener {
        private int ticks;
        private final Timer timer = new Timer(10, this);
        void start() { timer.start(); }
        public void actionPerformed(ActionEvent event) {
            ticks++;
            if (ticks == 2) { timer.stop(); System.out.println("ticks=" + ticks + " edt=" + EventQueue.isDispatchThread()); }
        }
    }
    public static void main(String[] args) { EventQueue.invokeLater(() -> new TickCounter().start()); }
}
