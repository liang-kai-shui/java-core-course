import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CenteredTitle {
    static class TitlePanel extends JPanel {
        private static final long serialVersionUID = 1L;
        TitlePanel() { setBackground(Color.WHITE); }
        @Override public Dimension getPreferredSize() { return new Dimension(320, 120); }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D copy = (Graphics2D) g.create();
            try {
                Font font = new Font("SansSerif", Font.BOLD, 12).deriveFont(24.0f);
                copy.setFont(font);
                String text = "学习 Java";
                Rectangle2D bounds = font.getStringBounds(text, copy.getFontRenderContext());
                double x = (getWidth() - bounds.getWidth()) / 2;
                double top = (getHeight() - bounds.getHeight()) / 2;
                double baseY = top - bounds.getY();
                copy.setColor(Color.BLACK);
                copy.drawString(text, (float) x, (float) baseY);
                copy.setColor(Color.GRAY);
                copy.drawLine((int) x, (int) baseY, (int) (x + bounds.getWidth()), (int) baseY);
            } finally {
                copy.dispose();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) {
            EventQueue.invokeAndWait(() -> {
                TitlePanel panel = new TitlePanel();
                panel.setSize(320, 120);
                BufferedImage image = new BufferedImage(320, 120, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                boolean inside;
                try {
                    panel.printAll(g);
                    Font font = new Font("SansSerif", Font.BOLD, 12).deriveFont(24.0f);
                    Rectangle2D bounds = font.getStringBounds("学习 Java", g.getFontRenderContext());
                    double x = (320 - bounds.getWidth()) / 2;
                    double top = (120 - bounds.getHeight()) / 2;
                    inside = x >= 0 && top >= 0 && x + bounds.getWidth() <= 320 && top + bounds.getHeight() <= 120;
                } finally { g.dispose(); }
                System.out.println("inside=" + inside);
            });
        } else {
            EventQueue.invokeLater(() -> {
                JFrame frame = new JFrame("居中标题");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new TitlePanel());
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            });
        }
    }
}
