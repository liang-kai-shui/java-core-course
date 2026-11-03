import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class RepaintFromState {
    static class ReviewPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private boolean painted;
        @Override public Dimension getPreferredSize() { return new Dimension(200, 100); }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g); painted = true; g.drawString("Study", 20, 50);
        }
    }
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) {
            EventQueue.invokeAndWait(() -> {
                ReviewPanel panel = new ReviewPanel(); panel.setSize(200, 100);
                BufferedImage image = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                try { panel.printAll(g); } finally { g.dispose(); }
                System.out.println("painted=" + panel.painted);
            });
        } else EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("绘制状态"); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new ReviewPanel()); frame.pack(); frame.setLocationByPlatform(true); frame.setVisible(true);
        });
    }
}
