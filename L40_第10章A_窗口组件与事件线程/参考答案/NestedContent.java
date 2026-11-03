import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NestedContent {
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) {
            EventQueue.invokeAndWait(() -> {
                JPanel body = new JPanel();
                body.add(new JLabel("第一行"));
                JPanel card = new JPanel(new BorderLayout());
                card.add(new JLabel("标题"), BorderLayout.NORTH);
                card.add(body, BorderLayout.CENTER);
                card.setPreferredSize(new Dimension(300, 160));
                System.out.println("card=" + card.getComponentCount());
                System.out.println("body=" + body.getComponentCount());
                System.out.println("parent=" + (body.getParent() == card));
            });
            return;
        }
        EventQueue.invokeLater(() -> {
            JPanel body = new JPanel();
            body.add(new JLabel("第一行"));
            JPanel card = new JPanel(new BorderLayout());
            card.add(new JLabel("标题"), BorderLayout.NORTH);
            card.add(body, BorderLayout.CENTER);
            card.setPreferredSize(new Dimension(300, 160));
            JFrame frame = new JFrame("嵌套内容");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(card);
            frame.pack();
            frame.setLocationByPlatform(true);
            frame.setVisible(true);
        });
    }
}
