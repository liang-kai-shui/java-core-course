import java.awt.EventQueue;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class InputMapConditions {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            JPanel panel = new JPanel();
            KeyStroke ctrlB = KeyStroke.getKeyStroke("ctrl B");
            panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ctrlB, "course.blue");
            System.out.println("window=" + panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).get(ctrlB));
            System.out.println("focused=" + panel.getInputMap(JComponent.WHEN_FOCUSED).get(ctrlB));
            System.out.println("ancestor=" + panel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).get(ctrlB));
        });
    }
}
