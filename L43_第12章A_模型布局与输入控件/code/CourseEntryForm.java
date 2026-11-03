import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class CourseEntryForm {
    static class Entry {
        final JPanel panel = new JPanel(new BorderLayout(8, 8));
        final JTextField field = new JTextField(12);
        final JTextArea area = new JTextArea(5, 24);
        final JLabel status = new JLabel("ready");
        final JButton add = new JButton("添加");
        Entry() {
            JLabel label = new JLabel("课程名"); label.setLabelFor(field);
            JPanel row = new JPanel(); row.add(label); row.add(field); row.add(add);
            area.setEditable(false); area.setLineWrap(true); area.setWrapStyleWord(true);
            panel.add(row, BorderLayout.NORTH); panel.add(new JScrollPane(area), BorderLayout.CENTER); panel.add(status, BorderLayout.SOUTH);
            add.addActionListener(e -> submit()); field.addActionListener(e -> submit());
        }
        void submit() {
            String title = field.getText().trim();
            if (title.isEmpty()) { status.setText("empty"); return; }
            area.append(title + "\n"); status.setText("added");
        }
    }
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) EventQueue.invokeAndWait(() -> {
            Entry entry = new Entry(); entry.field.setText("  数组  "); entry.add.doClick(0); entry.field.setText("  "); entry.add.doClick(0);
            System.out.println(entry.area.getText().replace("\n", "|")); System.out.println("status=" + entry.status.getText());
        }); else EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("课程输入"); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.add(new Entry().panel); frame.pack(); frame.setLocationByPlatform(true); frame.setVisible(true);
        });
    }
}
