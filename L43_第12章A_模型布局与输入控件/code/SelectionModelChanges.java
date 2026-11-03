import java.awt.EventQueue;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
public class SelectionModelChanges {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            JComboBox<String> mode = new JComboBox<>(new String[]{"阅读", "练习"}); JLabel selected = new JLabel("阅读");
            mode.addActionListener(e -> { int i = mode.getSelectedIndex(); selected.setText(i < 0 ? "none" : mode.getItemAt(i)); });
            JSlider slider = new JSlider(0, 100, 50); JLabel amount = new JLabel("50"); slider.addChangeListener(e -> amount.setText(Integer.toString(slider.getValue())));
            mode.setSelectedIndex(1); System.out.println(selected.getText()); slider.setValue(80); System.out.println(amount.getText());
            mode.removeAllItems(); System.out.println(selected.getText());
        });
    }
}
