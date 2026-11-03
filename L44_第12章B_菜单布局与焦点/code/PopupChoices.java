import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.*;
public class PopupChoices {
    static class View {
        final JPanel panel = new JPanel(); final JPopupMenu menu = new JPopupMenu();
        final JCheckBoxMenuItem mark = new JCheckBoxMenuItem("标记");
        final JRadioButtonMenuItem shortMode = new JRadioButtonMenuItem("简洁", true), longMode = new JRadioButtonMenuItem("详细");
        View() {
            ButtonGroup group = new ButtonGroup(); group.add(shortMode); group.add(longMode);
            menu.add(mark); menu.addSeparator(); menu.add(shortMode); menu.add(longMode);
            panel.setBackground(Color.WHITE); panel.setComponentPopupMenu(menu);
        }
    }
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("--check")) EventQueue.invokeAndWait(() -> {
            View v = new View(); v.mark.doClick(0); v.longMode.doClick(0);
            System.out.println(v.mark.isSelected()); System.out.println(v.shortMode.isSelected() + ":" + v.longMode.isSelected());
        }); else EventQueue.invokeLater(() -> {
            JFrame f = new JFrame("在面板上右键"); f.add(new View().panel); f.setSize(360, 200);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setVisible(true);
        });
    }
}
