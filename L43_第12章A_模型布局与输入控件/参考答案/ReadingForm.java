import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
public class ReadingForm {
    static class Entry {
        final JPanel panel = new JPanel(new BorderLayout(8, 8));
        final JTextField title = new JTextField(14); final JCheckBox done = new JCheckBox("完成");
        final JComboBox<String> mode = new JComboBox<>(new String[]{"阅读", "练习"}); final JSlider minutes = new JSlider(0, 60, 20);
        final JButton submit = new JButton("提交"); final JLabel output = new JLabel("ready");
        Entry() {
            JPanel fields = new JPanel(new GridLayout(0, 2, 6, 6)); fields.setBorder(BorderFactory.createTitledBorder("学习记录"));
            fields.add(new JLabel("标题")); fields.add(title); fields.add(new JLabel("模式")); fields.add(mode); fields.add(new JLabel("分钟")); fields.add(minutes); fields.add(done); fields.add(submit);
            panel.add(fields); panel.add(output, BorderLayout.SOUTH); submit.addActionListener(e -> save());
        }
        void save() {
            String name = title.getText().trim(); if (name.isEmpty()) { output.setText("empty"); return; }
            int index = mode.getSelectedIndex(); if (index < 0) { output.setText("no mode"); return; }
            output.setText(name + ":" + mode.getItemAt(index) + ":" + minutes.getValue() + ":" + done.isSelected());
        }
    }
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) EventQueue.invokeAndWait(() -> {
            Entry e = new Entry(); e.title.setText("  数组  "); e.mode.setSelectedIndex(1); e.minutes.setValue(30); e.done.setSelected(true); e.submit.doClick(0); System.out.println(e.output.getText());
            e.title.setText(" "); e.submit.doClick(0); System.out.println(e.output.getText());
            e.title.setText("数组"); e.minutes.setValue(0); e.submit.doClick(0); System.out.println(e.output.getText());
            e.minutes.setValue(60); e.submit.doClick(0); System.out.println(e.output.getText());
            e.mode.removeAllItems(); e.submit.doClick(0); System.out.println(e.output.getText());
        }); else EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("阅读表单"); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.add(new Entry().panel); frame.pack(); frame.setLocationByPlatform(true); frame.setVisible(true);
        });
    }
}
