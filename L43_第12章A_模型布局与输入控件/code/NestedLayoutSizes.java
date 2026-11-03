import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
public class NestedLayoutSizes {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            JPanel root = new JPanel(new BorderLayout(4, 4)), south = new JPanel(); JTextArea center = new JTextArea(3, 10);
            south.add(new JButton("保存")); south.add(new JButton("取消")); root.add(new JLabel("标题"), BorderLayout.NORTH); root.add(center); root.add(south, BorderLayout.SOUTH);
            root.setSize(400, 200); root.doLayout(); int width = south.getWidth(), height = center.getHeight();
            root.setSize(600, 320); root.doLayout(); System.out.println("wider=" + (south.getWidth() > width)); System.out.println("centerTaller=" + (center.getHeight() > height));
        });
    }
}
