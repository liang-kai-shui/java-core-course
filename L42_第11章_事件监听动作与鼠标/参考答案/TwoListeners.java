import java.awt.EventQueue;
import javax.swing.JButton;

public class TwoListeners {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            JButton button = new JButton("go");
            int[] count = {0};
            boolean[] sameSource = {true};
            button.addActionListener(event -> count[0]++);
            button.addActionListener(event -> {
                if (event.getSource() != button) sameSource[0] = false;
            });
            button.doClick(0);
            button.doClick(0);
            System.out.println("count=" + count[0]);
            System.out.println("source=" + sameSource[0]);
        });
    }
}
