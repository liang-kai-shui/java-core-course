import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.*;
public class ColorCommitRule {
    static Color choose(Color old, Color selected) { return selected == null ? old : selected; }
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) {
            System.out.println(choose(Color.WHITE, null).equals(Color.WHITE)); System.out.println(choose(Color.WHITE, Color.BLUE).equals(Color.BLUE));
        } else EventQueue.invokeLater(() -> {
            JPanel panel = new JPanel(); panel.setBackground(Color.WHITE); JButton change = new JButton("背景色"); panel.add(change);
            JFrame f = new JFrame("确认才换色"); change.addActionListener(e -> {
                Color old = panel.getBackground(), selected = JColorChooser.showDialog(f, "背景色", old); panel.setBackground(choose(old, selected));
            });
            f.add(panel); f.setSize(360, 180); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setVisible(true);
        });
    }
}
