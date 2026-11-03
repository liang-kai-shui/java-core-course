import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class ShapePaintLab {
    static class Scene extends JPanel {
        private static final long serialVersionUID = 1L;
        Scene() { setBackground(Color.WHITE); }
        @Override public Dimension getPreferredSize() { return new Dimension(360, 220); }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D copy = (Graphics2D) g.create();
            try {
                copy.setColor(Color.RED); copy.fill(new Rectangle2D.Double(20, 20, 120, 70));
                copy.setColor(Color.BLUE); copy.fill(new Ellipse2D.Double(180, 20, 120, 70));
                copy.setColor(Color.BLACK); copy.drawLine(20, 130, 300, 130); copy.drawString("Java 2D", 20, 180);
            } finally { copy.dispose(); }
        }
    }
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) {
            EventQueue.invokeAndWait(() -> {
                Scene panel = new Scene(); panel.setSize(360, 220);
                BufferedImage image = new BufferedImage(360, 220, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics(); try { panel.printAll(g); } finally { g.dispose(); }
                System.out.println("rectangle=" + (image.getRGB(30, 30) == Color.RED.getRGB()));
                System.out.println("ellipse=" + (image.getRGB(240, 55) == Color.BLUE.getRGB()));
                if (args.length > 1) {
                    try { ImageIO.write(image, "png", new File(args[1])); }
                    catch (IOException ex) { throw new UncheckedIOException(ex); }
                }
            });
        } else EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("形状与坐标"); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new Scene()); frame.pack(); frame.setLocationByPlatform(true); frame.setVisible(true);
        });
    }
}
