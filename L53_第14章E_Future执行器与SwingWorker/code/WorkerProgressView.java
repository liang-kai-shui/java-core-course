import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;
import java.util.concurrent.*;
import javax.swing.*;
public class WorkerProgressView {
    static class View {
        final JPanel panel = new JPanel(new BorderLayout()); final JLabel status = new JLabel("ready");
        final JProgressBar progress = new JProgressBar(0, 100); final JButton start = new JButton("开始"), cancel = new JButton("取消");
        Job active;
        View() {
            JPanel buttons = new JPanel(); buttons.add(start); buttons.add(cancel); cancel.setEnabled(false);
            panel.add(status, BorderLayout.NORTH); panel.add(progress); panel.add(buttons, BorderLayout.SOUTH);
            start.addActionListener(e -> begin(new Job(this, true, null, false)));
            cancel.addActionListener(e -> { if (active != null) active.cancel(true); });
        }
        void begin(Job job) { active = job; progress.setValue(0); status.setText("working"); start.setEnabled(false); cancel.setEnabled(true); job.execute(); }
    }
    static class Job extends SwingWorker<Integer, Integer> {
        final View view; final boolean slow, fail; final CountDownLatch gate;
        final CountDownLatch entered = new CountDownLatch(1), exited = new CountDownLatch(1), finished = new CountDownLatch(1);
        boolean background, doneEdt;
        Job(View view, boolean slow, CountDownLatch gate, boolean fail) { this.view = view; this.slow = slow; this.gate = gate; this.fail = fail; }
        protected Integer doInBackground() throws InterruptedException {
            background = !EventQueue.isDispatchThread(); entered.countDown();
            try {
                if (gate != null) gate.await(); if (fail) throw new IllegalStateException("demo"); int sum = 0;
                for (int i = 1; i <= 100; i++) { if (isCancelled()) return sum; if (slow) Thread.sleep(20); sum += i; publish(i); }
                return sum;
            } finally { exited.countDown(); }
        }
        protected void process(List<Integer> batch) { if (view.active == this && !isCancelled() && !batch.isEmpty()) view.progress.setValue(batch.get(batch.size() - 1)); }
        protected void done() {
            doneEdt = EventQueue.isDispatchThread();
            try {
                if (view.active != this) return;
                try { view.status.setText("done=" + get()); view.progress.setValue(100); }
                catch (CancellationException e) { view.status.setText("cancelled"); }
                catch (ExecutionException e) { view.status.setText("failed=" + e.getCause().getClass().getSimpleName()); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); view.status.setText("interrupted"); }
                finally { view.start.setEnabled(true); view.cancel.setEnabled(false); view.active = null; }
            } finally { finished.countDown(); }
        }
    }
    static void await(CountDownLatch latch) throws InterruptedException { if (!latch.await(3, TimeUnit.SECONDS)) throw new AssertionError("worker timeout"); }
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].endsWith("check")) {
            boolean cancel = args[0].equals("--cancel-check"), fail = args[0].equals("--fail-check"); Job[] holder = new Job[1];
            EventQueue.invokeAndWait(() -> { View v = new View(); Job job = new Job(v, false, cancel ? new CountDownLatch(1) : null, fail); holder[0] = job; v.begin(job); });
            Job job = holder[0]; await(job.entered); if (cancel) job.cancel(true); await(job.exited); await(job.finished);
            EventQueue.invokeAndWait(() -> { System.out.println(job.view.status.getText()); System.out.println("background=" + job.background); System.out.println("done-edt=" + job.doneEdt); });
        } else EventQueue.invokeLater(() -> {
            JFrame f = new JFrame("后台计算与取消"); f.add(new View().panel); f.setSize(420, 180); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setVisible(true);
        });
    }
}
