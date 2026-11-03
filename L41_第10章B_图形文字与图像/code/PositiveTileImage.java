import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
public class PositiveTileImage {
    public static void main(String[] args) throws IOException {
        BufferedImage tile = new BufferedImage(8, 8, BufferedImage.TYPE_INT_RGB); Graphics2D pen = tile.createGraphics();
        try { pen.setColor(Color.ORANGE); pen.fillRect(0, 0, 8, 8); } finally { pen.dispose(); }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(); ImageIO.write(tile, "png", bytes);
        BufferedImage loaded = ImageIO.read(new ByteArrayInputStream(bytes.toByteArray()));
        if (loaded == null || loaded.getWidth() <= 0 || loaded.getHeight() <= 0) throw new IOException("invalid image");
        BufferedImage output = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB); Graphics2D g = output.createGraphics();
        try { for (int y = 0; y < 20; y += loaded.getHeight()) for (int x = 0; x < 20; x += loaded.getWidth()) g.drawImage(loaded, x, y, null); }
        finally { g.dispose(); }
        System.out.println("size=" + loaded.getWidth() + "x" + loaded.getHeight());
        System.out.println("tiled=" + (output.getRGB(19, 19) == Color.ORANGE.getRGB()));
    }
}
