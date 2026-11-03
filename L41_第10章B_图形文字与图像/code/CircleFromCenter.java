import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class CircleFromCenter {
    public static void main(String[] args) {
        Ellipse2D circle = new Ellipse2D.Double();
        circle.setFrameFromCenter(100, 80, 130, 110);
        System.out.println("x=" + circle.getX() + " y=" + circle.getY() + " w=" + circle.getWidth() + " h=" + circle.getHeight());
        Rectangle2D naive = new Rectangle2D.Double(100, 60, 20 - 100, 10 - 60);
        System.out.println("naive-empty=" + naive.isEmpty());
        System.out.println("naive-w=" + naive.getWidth());
    }
}
