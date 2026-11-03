import java.awt.EventQueue;
import javax.swing.*;
public class GroupLayoutRows {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            JPanel panel = new JPanel(); GroupLayout g = new GroupLayout(panel); panel.setLayout(g);
            JLabel first = new JLabel("课程"), second = new JLabel("备注");
            JTextField a = new JTextField(12), b = new JTextField(12);
            g.setAutoCreateGaps(true); g.setAutoCreateContainerGaps(true);
            g.setHorizontalGroup(g.createSequentialGroup()
                .addGroup(g.createParallelGroup().addComponent(first).addComponent(second))
                .addGroup(g.createParallelGroup().addComponent(a).addComponent(b)));
            g.setVerticalGroup(g.createSequentialGroup()
                .addGroup(g.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(first).addComponent(a))
                .addGroup(g.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(second).addComponent(b)));
            panel.setSize(360, 140); panel.doLayout();
            System.out.println("sameX=" + (a.getX() == b.getX())); System.out.println("rows=" + (a.getY() < b.getY()));
        });
    }
}
