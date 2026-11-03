import java.awt.EventQueue;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
public class IndependentAndExclusive {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            JCheckBox bold = new JCheckBox("粗"), italic = new JCheckBox("斜"); bold.doClick(0); italic.doClick(0); System.out.println(bold.isSelected() + ":" + italic.isSelected());
            ButtonGroup group = new ButtonGroup(); JRadioButton small = new JRadioButton("小", true), large = new JRadioButton("大");
            small.setActionCommand("SMALL"); large.setActionCommand("LARGE"); group.add(small); group.add(large); large.doClick(0);
            System.out.println(small.isSelected() + ":" + large.isSelected()); System.out.println(group.getSelection().getActionCommand());
        });
    }
}
