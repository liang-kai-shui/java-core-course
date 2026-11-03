import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class FillVersusDrawPixels {
    static BufferedImage blank() {
        BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        Graphics2D paint = image.createGraphics();
        try {
            paint.setColor(Color.WHITE);
            paint.fillRect(0, 0, 32, 32);
        } finally {
            paint.dispose();
        }
        return image;
    }

    public static void main(String[] args) {
        Rectangle2D rect = new Rectangle2D.Double(0, 0, 10, 20);
        BufferedImage filled = blank();
        Graphics2D fillPen = filled.createGraphics();
        try {
            fillPen.setColor(Color.RED);
            fillPen.fill(rect);
        } finally {
            fillPen.dispose();
        }
        BufferedImage drawn = blank();
        Graphics2D drawPen = drawn.createGraphics();
        try {
            drawPen.setColor(Color.RED);
            drawPen.draw(rect);
        } finally {
            drawPen.dispose();
        }
        System.out.println("fill-inside=" + (filled.getRGB(9, 19) == Color.RED.getRGB()));
        System.out.println("fill-right-edge=" + (filled.getRGB(10, 20) == Color.RED.getRGB()));
        System.out.println("draw-edge=" + (drawn.getRGB(10, 20) == Color.RED.getRGB()));
        System.out.println("draw-inside=" + (drawn.getRGB(9, 19) == Color.RED.getRGB()));
    }
}
