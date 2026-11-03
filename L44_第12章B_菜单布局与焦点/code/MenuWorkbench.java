import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import javax.swing.*;
public class MenuWorkbench {
    static class Work {
        final JPanel panel = new JPanel(new BorderLayout());
        final JLabel status = new JLabel("0");
        int count;
        final Action record = new AbstractAction("记录（内存）") {
            private static final long serialVersionUID = 1L;
            public void actionPerformed(ActionEvent e) { status.setText(String.valueOf(++count)); }
        };
        final JMenuBar bar = new JMenuBar();
        final JMenuItem item = new JMenuItem(record);
        final JButton button = new JButton(record);
        final JCheckBoxMenuItem readOnly = new JCheckBoxMenuItem("只读");
        Work() {
            record.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
            item.setAccelerator((KeyStroke) record.getValue(Action.ACCELERATOR_KEY));
            JMenu file = new JMenu("操作"); file.setMnemonic('O');
            file.add(item); file.addSeparator(); file.add(readOnly); bar.add(file);
            JToolBar tools = new JToolBar(); tools.setFloatable(false); tools.add(button);
            readOnly.addActionListener(e -> record.setEnabled(!readOnly.isSelected()));
            panel.add(tools, BorderLayout.NORTH); panel.add(status, BorderLayout.CENTER);
        }
    }
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) EventQueue.invokeAndWait(() -> {
            Work w = new Work(); w.item.doClick(0); w.button.doClick(0); w.readOnly.doClick(0);
            System.out.println(w.status.getText()); System.out.println(w.item.isEnabled() + ":" + w.button.isEnabled());
        }); else EventQueue.invokeLater(() -> {
            Work w = new Work(); JFrame f = new JFrame("内存计数工作台"); f.setJMenuBar(w.bar);
            f.add(w.panel); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(420, 180); f.setLocationByPlatform(true); f.setVisible(true);
        });
    }
}
