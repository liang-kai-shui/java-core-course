import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class TextBaselineMeasure {
    public static void main(String[] args) throws IOException {
        BufferedImage image = new BufferedImage(320, 120, BufferedImage.TYPE_INT_RGB); Graphics2D g = image.createGraphics();
        try {
            g.setColor(Color.WHITE); g.fillRect(0, 0, 320, 120);
            Font font = new Font("SansSerif", Font.BOLD, 12).deriveFont(24.0f); g.setFont(font);
            String text = "Java pg"; Rectangle2D bounds = font.getStringBounds(text, g.getFontRenderContext());
            double x = (320 - bounds.getWidth()) / 2; double top = (120 - bounds.getHeight()) / 2; double baseY = top - bounds.getY();
            g.setColor(Color.BLACK); g.drawString(text, (float) x, (float) baseY);
            g.setColor(Color.GRAY); g.drawLine(0, (int) baseY, 319, (int) baseY);
            g.setColor(Color.BLUE); g.draw(new Rectangle2D.Double(x, top, bounds.getWidth(), bounds.getHeight()));
            System.out.println("inside=" + (x >= 0 && top >= 0 && x + bounds.getWidth() <= 320 && top + bounds.getHeight() <= 120));
        } finally { g.dispose(); }
        if (args.length > 1) ImageIO.write(image, "png", new File(args[1]));
    }
}
