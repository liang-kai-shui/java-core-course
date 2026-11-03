import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class ButtonEventRoute {
    static class StepPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private int count;
        private final JLabel label = new JLabel("0");
        private final JButton button = new JButton("记录一步");
        StepPanel() {
            add(label); add(button);
            button.addActionListener(event -> { count++; label.setText(Integer.toString(count)); });
        }
    }
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) EventQueue.invokeAndWait(() -> {
            StepPanel panel = new StepPanel(); panel.button.doClick(0); panel.button.doClick(0);
            System.out.println("count=" + panel.count); System.out.println("label=" + panel.label.getText());
        }); else EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("事件路线"); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new StepPanel()); frame.pack(); frame.setLocationByPlatform(true); frame.setVisible(true);
        });
    }
}
