import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.EventQueue;
import javax.swing.*;
public class NameDialogTransaction {
    static class Edit {
        final JPanel panel = new JPanel(new BorderLayout()); final JTextField field = new JTextField(14);
        final JLabel tip = new JLabel("输入非空名字"); final JButton ok = new JButton("确定"), cancel = new JButton("取消");
        boolean accepted; Runnable close = () -> { };
        Edit() {
            JPanel buttons = new JPanel(); buttons.add(ok); buttons.add(cancel);
            panel.add(field, BorderLayout.NORTH); panel.add(tip); panel.add(buttons, BorderLayout.SOUTH);
            ok.addActionListener(e -> { if (field.getText().trim().isEmpty()) { tip.setText("empty"); return; } accepted = true; close.run(); });
            cancel.addActionListener(e -> close.run());
        }
        void reset(String old) { accepted = false; field.setText(old); tip.setText("输入非空名字"); }
        String show(JFrame owner, String old) {
            reset(old); JDialog dialog = new JDialog(owner, "编辑草稿", Dialog.ModalityType.APPLICATION_MODAL);
            close = dialog::dispose; dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); dialog.add(panel);
            dialog.getRootPane().setDefaultButton(ok); dialog.pack(); dialog.setLocationRelativeTo(owner); dialog.setVisible(true);
            return accepted ? field.getText().trim() : null;
        }
    }
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) EventQueue.invokeAndWait(() -> {
            Edit edit = new Edit(); edit.reset(" "); edit.ok.doClick(0); System.out.println(edit.accepted);
            edit.field.setText(" 数组 "); edit.ok.doClick(0); System.out.println(edit.accepted + ":" + edit.field.getText().trim());
            edit.reset("数组"); edit.cancel.doClick(0); System.out.println(edit.accepted);
        }); else EventQueue.invokeLater(() -> {
            JFrame f = new JFrame("草稿事务"); JLabel name = new JLabel("循环"); JButton button = new JButton("改名"); Edit edit = new Edit();
            button.addActionListener(e -> { String result = edit.show(f, name.getText()); if (result != null) name.setText(result); });
            JPanel p = new JPanel(); p.add(name); p.add(button); f.add(p); f.setSize(360, 150); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setVisible(true);
        });
    }
}
