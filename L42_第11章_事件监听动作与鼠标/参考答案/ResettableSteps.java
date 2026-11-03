import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
public class ResettableSteps {
    static class Panel extends JPanel {
        private static final long serialVersionUID = 1L;
        int count; final JLabel label = new JLabel("0");
        transient final Action add = new AbstractAction("加一") {
            private static final long serialVersionUID = 1L;
            public void actionPerformed(ActionEvent event) { count++; refresh(); }
        };
        final JButton addButton = new JButton(add), reset = new JButton("重置");
        Panel() {
            add(label); add(addButton); add(reset); reset.addActionListener(e -> { count = 0; refresh(); });
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl R"), "course.add");
            getActionMap().put("course.add", add);
        }
        void refresh() { label.setText(Integer.toString(count)); }
    }
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) EventQueue.invokeAndWait(() -> {
            Panel panel = new Panel(); panel.addButton.doClick(0); panel.addButton.doClick(0); panel.reset.doClick(0); panel.addButton.doClick(0);
            System.out.println("label=" + panel.label.getText()); System.out.println("count=" + panel.count);
            Object key = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).get(KeyStroke.getKeyStroke("ctrl R"));
            Action action = panel.getActionMap().get(key); if (action.isEnabled()) action.actionPerformed(new ActionEvent(panel, ActionEvent.ACTION_PERFORMED, "check"));
            System.out.println("bound=" + panel.count); panel.add.setEnabled(false); System.out.println("enabled=" + panel.addButton.isEnabled());
        }); else EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("可重置计数"); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.add(new Panel()); frame.pack(); frame.setLocationByPlatform(true); frame.setVisible(true);
        });
    }
}
