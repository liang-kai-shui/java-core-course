import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class ImageLoadBoundary {
    public static void main(String[] args) {
        try {
            BufferedImage image = args.length == 0 ? ImageIO.read(new ByteArrayInputStream(new byte[] {1, 2, 3})) : ImageIO.read(new File(args[0]));
            if (image == null) System.out.println("unsupported");
            else if (image.getWidth() <= 0 || image.getHeight() <= 0) System.out.println("bad-size");
            else System.out.println("size=" + image.getWidth() + ":" + image.getHeight());
        } catch (IOException e) { System.out.println("io-error"); }
    }
}
