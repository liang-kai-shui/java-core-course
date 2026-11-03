import java.awt.geom.Rectangle2D;
public class DiagonalRectangle {
    public static void main(String[] args) {
        Rectangle2D rect = new Rectangle2D.Double();
        rect.setFrameFromDiagonal(100, 60, 20, 10);
        System.out.println(rect.getX() + ":" + rect.getY() + ":" + rect.getWidth() + ":" + rect.getHeight());
        System.out.println(rect.getCenterX() + ":" + rect.getCenterY());
    }
}
