import java.awt.EventQueue;
import javax.swing.*;
public class InputCommitRoute {
    static String accept(String draft) {
        if (draft == null) return "cancel";
        String value = draft.trim(); return value.isEmpty() ? "empty" : value;
    }
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) {
            System.out.println(accept(null)); System.out.println(accept("  ")); System.out.println(accept(" 数组 "));
        } else EventQueue.invokeLater(() -> {
            JFrame f = new JFrame("草稿与提交"); JLabel name = new JLabel("循环"); JButton change = new JButton("改名");
            JPanel p = new JPanel(); p.add(name); p.add(change); f.add(p);
            change.addActionListener(e -> {
                String draft = JOptionPane.showInputDialog(f, "输入课程名", name.getText());
                if (draft == null) return;
                String result = accept(draft);
                if (draft.trim().isEmpty()) JOptionPane.showMessageDialog(f, result); else name.setText(result);
            });
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setSize(360, 140); f.setVisible(true);
        });
    }
}
