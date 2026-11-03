import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
public class MyStudyWindow {
    static JPanel content() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("每天一步", SwingConstants.CENTER));
        panel.setPreferredSize(new Dimension(360, 200)); return panel;
    }
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) {
            EventQueue.invokeAndWait(() -> {
                JPanel panel = content(); System.out.println(panel.getComponentCount());
                Dimension size = panel.getPreferredSize(); System.out.println(size.width + "x" + size.height);
            });
        } else EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("我的练习台"); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(content()); frame.pack(); frame.setLocationByPlatform(true); frame.setVisible(true);
        });
    }
}
