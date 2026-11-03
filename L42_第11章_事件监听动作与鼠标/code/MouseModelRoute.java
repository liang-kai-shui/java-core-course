import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class MouseModelRoute {
    static class PointPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        int x = 20, y = 20;
        PointPanel() {
            MouseAdapter handler = new MouseAdapter() {
                public void mousePressed(MouseEvent e) { moveTo(e); }
                public void mouseDragged(MouseEvent e) { moveTo(e); }
            };
            addMouseListener(handler); addMouseMotionListener(handler);
        }
        void moveTo(MouseEvent e) { x = e.getX(); y = e.getY(); repaint(); }
        @Override public Dimension getPreferredSize() { return new Dimension(300, 180); }
        @Override protected void paintComponent(Graphics g) { super.paintComponent(g); g.fillOval(x - 5, y - 5, 10, 10); }
    }
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) EventQueue.invokeAndWait(() -> {
            PointPanel panel = new PointPanel();
            panel.dispatchEvent(new MouseEvent(panel, MouseEvent.MOUSE_PRESSED, 1L, 0, 30, 40, 1, false, MouseEvent.BUTTON1));
            panel.dispatchEvent(new MouseEvent(panel, MouseEvent.MOUSE_DRAGGED, 2L, InputEvent.BUTTON1_DOWN_MASK, 50, 60, 0, false, MouseEvent.NOBUTTON));
            System.out.println(panel.x + ":" + panel.y);
        }); else EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("鼠标模型"); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.add(new PointPanel()); frame.pack(); frame.setLocationByPlatform(true); frame.setVisible(true);
        });
    }
}
