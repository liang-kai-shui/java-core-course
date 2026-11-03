import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
public class SharedActionBinding {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            JLabel label = new JLabel("A"); JPanel panel = new JPanel();
            Action action = new AbstractAction("Blue") {
                private static final long serialVersionUID = 1L;
                public void actionPerformed(ActionEvent event) { label.setText("B"); }
            };
            JButton first = new JButton(action), second = new JButton(action);
            panel.add(first); panel.add(second); panel.add(label);
            panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl B"), "course.blue");
            panel.getActionMap().put("course.blue", action);
            first.doClick(0); System.out.println(label.getText());
            Object key = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).get(KeyStroke.getKeyStroke("ctrl B"));
            System.out.println("bound=" + (panel.getActionMap().get(key) == action));
            action.setEnabled(false); System.out.println("enabled=" + first.isEnabled() + ":" + second.isEnabled());
        });
    }
}
