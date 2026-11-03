import java.awt.*;
import javax.swing.*;
public class GridBagRows {
    static GridBagConstraints at(int x, int y) {
        GridBagConstraints c = new GridBagConstraints(); c.gridx = x; c.gridy = y;
        c.insets = new Insets(4, 4, 4, 4); c.anchor = GridBagConstraints.EAST; return c;
    }
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            JPanel form = new JPanel(new GridBagLayout()); JTextField name = new JTextField(10), target = new JTextField(10);
            form.add(new JLabel("课程"), at(0, 0)); form.add(new JLabel("目标"), at(0, 1));
            GridBagConstraints a = at(1, 0); a.weightx = 1; a.fill = GridBagConstraints.HORIZONTAL; form.add(name, a);
            GridBagConstraints b = at(1, 1); b.weightx = 1; b.fill = GridBagConstraints.HORIZONTAL; form.add(target, b);
            GridBagConstraints c = at(0, 2); c.gridwidth = 2; form.add(new JButton("提交"), c);
            form.setSize(300, 160); form.doLayout(); int before = name.getWidth();
            form.setSize(500, 160); form.doLayout(); System.out.println("grows=" + (name.getWidth() > before));
        });
    }
}
