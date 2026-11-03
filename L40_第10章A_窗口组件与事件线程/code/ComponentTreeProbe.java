import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ComponentTreeProbe {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            JPanel card = new JPanel(new BorderLayout());
            JPanel body = new JPanel();
            body.add(new JLabel("第一行"));
            card.add(new JLabel("标题"), BorderLayout.NORTH);
            card.add(body, BorderLayout.CENTER);
            System.out.println("card-children=" + card.getComponentCount());
            System.out.println("body-children=" + body.getComponentCount());
            System.out.println("body-in-card=" + (body.getParent() == card));
        });
    }
}
