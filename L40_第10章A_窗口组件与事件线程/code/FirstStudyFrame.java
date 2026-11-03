import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
public class FirstStudyFrame {
    static JPanel createContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("讲义先学，教材深化。", SwingConstants.CENTER), BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(320, 180));
        return panel;
    }
    public static void main(String[] args) throws Exception {
        boolean check = args.length > 0 && args[0].equals("--check");
        boolean windowCheck = args.length > 0 && args[0].equals("--window-check");
        if (check) {
            EventQueue.invokeAndWait(() -> {
                JPanel panel = createContent();
                System.out.println("edt=" + EventQueue.isDispatchThread());
                System.out.println("components=" + panel.getComponentCount());
                Dimension size = panel.getPreferredSize();
                System.out.println("preferred=" + size.width + "x" + size.height);
            });
        } else {
            EventQueue.invokeLater(() -> {
                JFrame frame = new JFrame("Java学习台");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(createContent()); frame.pack();
                frame.setLocationByPlatform(true); frame.setVisible(true);
                if (windowCheck) { System.out.println("showing=" + frame.isShowing()); frame.dispose(); }
            });
        }
    }
}
