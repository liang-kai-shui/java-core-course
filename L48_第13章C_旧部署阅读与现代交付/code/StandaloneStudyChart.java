import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
public class StandaloneStudyChart {
    static class Bars extends JPanel {
        private static final long serialVersionUID = 1L;
        final int[] values;
        Bars(int[] values) { this.values = values.clone(); for (int v : values) if (v < 0) throw new IllegalArgumentException("negative"); setBackground(Color.WHITE); }
        public Dimension getPreferredSize() { return new Dimension(320, 180); }
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); if (values.length == 0) return;
            int max = 0; for (int v : values) max = Math.max(max, v); if (max == 0) return;
            int width = Math.max(0, getWidth() - 40), height = Math.max(0, getHeight() - 40); g.setColor(Color.RED);
            for (int i = 0; i < values.length; i++) {
                int cell = width / values.length, h = (int) ((long) height * values[i] / max);
                g.fillRect(20 + cell * i, getHeight() - 20 - h, Math.max(0, cell - 6), h);
            }
        }
    }
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) EventQueue.invokeAndWait(() -> {
            Bars bars = new Bars(new int[] {20, 40}); bars.setSize(320, 180); BufferedImage image = new BufferedImage(320, 180, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics(); try { bars.printAll(g); } finally { g.dispose(); }
            System.out.println("bars=" + bars.values.length); System.out.println("painted=" + (image.getRGB(30, 150) == Color.RED.getRGB()));
        }); else EventQueue.invokeLater(() -> {
            JFrame f = new JFrame("独立学习图表"); f.add(new Bars(new int[] {20, 40})); f.pack(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setVisible(true);
        });
    }
}
