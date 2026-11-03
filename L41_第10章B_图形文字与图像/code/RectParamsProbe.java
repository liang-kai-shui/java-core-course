import java.awt.geom.Rectangle2D;

public class RectParamsProbe {
    public static void main(String[] args) {
        Rectangle2D.Double rect = new Rectangle2D.Double(100, 50, 80, 40);
        System.out.println("x=" + rect.getX() + " y=" + rect.getY());
        System.out.println("w=" + rect.getWidth() + " h=" + rect.getHeight());
        System.out.println("maxX=" + rect.getMaxX() + " maxY=" + rect.getMaxY());
        System.out.println("center=" + rect.getCenterX() + "," + rect.getCenterY());
        Rectangle2D upcast = new Rectangle2D.Float(100F, 50F, 80F, 40F);
        System.out.println("float-width=" + upcast.getWidth());
        rect.setRect(0, 0, -80, -40);
        System.out.println("empty=" + rect.isEmpty());
    }
}
