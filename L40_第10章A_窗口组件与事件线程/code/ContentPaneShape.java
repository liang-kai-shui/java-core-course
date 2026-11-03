import java.awt.EventQueue;
import javax.swing.JRootPane;

public class ContentPaneShape {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            JRootPane root = new JRootPane();
            System.out.println("content=" + root.getContentPane().getClass().getSimpleName());
            System.out.println("glass=" + root.getGlassPane().getClass().getSimpleName());
            System.out.println("menu-bar=" + (root.getJMenuBar() == null ? "absent" : "present"));
            System.out.println("layered-children=" + root.getLayeredPane().getComponentCount());
        });
    }
}
