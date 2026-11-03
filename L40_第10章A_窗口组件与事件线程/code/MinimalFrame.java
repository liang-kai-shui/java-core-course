import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MinimalFrame {
    static JPanel createContent() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(320, 180));
        return panel;
    }

    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) {
            EventQueue.invokeAndWait(() -> {
                JPanel panel = createContent();
                Dimension preferred = panel.getPreferredSize();
                System.out.println("preferred=" + preferred.width + "x" + preferred.height);
                System.out.println("actual-before=" + panel.getWidth() + "x" + panel.getHeight());
                panel.setSize(360, 200);
                System.out.println("actual-after=" + panel.getWidth() + "x" + panel.getHeight());
            });
            return;
        }
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Java学习台");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(createContent());
            frame.pack();
            frame.setLocationByPlatform(true);
            frame.setVisible(true);
        });
    }
}
