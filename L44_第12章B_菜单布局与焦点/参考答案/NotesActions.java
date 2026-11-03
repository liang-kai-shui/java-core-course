import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import javax.swing.*;
public class NotesActions {
    static class View {
        int count; final JLabel label = new JLabel("0");
        final JPanel panel = new JPanel(new BorderLayout()); final JMenuBar bar = new JMenuBar();
        final Action add = new AbstractAction("加一") {
            private static final long serialVersionUID = 1L;
            public void actionPerformed(ActionEvent e) { label.setText(String.valueOf(++count)); }
        };
        final Action clear = new AbstractAction("清零") {
            private static final long serialVersionUID = 1L;
            public void actionPerformed(ActionEvent e) { count = 0; label.setText("0"); }
        };
        final JMenuItem item = new JMenuItem(add); final JButton button = new JButton(add);
        final JCheckBoxMenuItem readOnly = new JCheckBoxMenuItem("只读");
        View() {
            JMenu menu = new JMenu("操作"); menu.add(item); menu.add(clear); menu.add(readOnly); bar.add(menu);
            JToolBar tools = new JToolBar(); tools.add(button); tools.add(clear);
            readOnly.addActionListener(e -> add.setEnabled(!readOnly.isSelected()));
            panel.add(tools, BorderLayout.NORTH); panel.add(label);
        }
    }
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) EventQueue.invokeAndWait(() -> {
            View v = new View(); v.item.doClick(0); v.button.doClick(0); v.clear.actionPerformed(new ActionEvent(v, 0, "clear"));
            System.out.println(v.label.getText()); v.readOnly.doClick(0);
            System.out.println(v.item.isEnabled() + ":" + v.button.isEnabled()); v.readOnly.doClick(0); System.out.println(v.item.isEnabled());
        }); else EventQueue.invokeLater(() -> {
            View v = new View(); JFrame f = new JFrame("练习：共享动作"); f.setJMenuBar(v.bar); f.add(v.panel);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(400, 180); f.setVisible(true);
        });
    }
}
