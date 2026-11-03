import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class StudyBadge {
    static class Badge extends JPanel {
        private static final long serialVersionUID = 1L;
        Badge() { setBackground(Color.WHITE); }
        @Override public Dimension getPreferredSize() { return new Dimension(220, 140); }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g); Graphics2D copy = (Graphics2D) g.create();
            try { copy.setColor(Color.GREEN); copy.fillRect(20, 20, 80, 50); copy.setColor(Color.BLACK); copy.drawString("Study", 20, 100); }
            finally { copy.dispose(); }
        }
    }
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) EventQueue.invokeAndWait(() -> {
            Badge badge = new Badge(); badge.setSize(220, 140); BufferedImage image = new BufferedImage(220, 140, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics(); try { badge.printAll(g); } finally { g.dispose(); }
            System.out.println("fill=" + (image.getRGB(30, 30) == Color.GREEN.getRGB())); System.out.println("background=" + (image.getRGB(5, 5) == Color.WHITE.getRGB()));
            if (args.length > 1) try { ImageIO.write(image, "png", new File(args[1])); } catch (IOException ex) { throw new UncheckedIOException(ex); }
        }); else EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Study徽章"); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.add(new Badge()); frame.pack(); frame.setLocationByPlatform(true); frame.setVisible(true);
        });
    }
}
