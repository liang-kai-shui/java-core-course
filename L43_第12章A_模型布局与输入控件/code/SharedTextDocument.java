import java.awt.EventQueue;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;
public class SharedTextDocument {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            PlainDocument model = new PlainDocument(); JTextField first = new JTextField(3), second = new JTextField(12);
            first.setDocument(model); second.setDocument(model); first.setText("A"); second.setText("AB");
            System.out.println(first.getText()); System.out.println(second.getText()); System.out.println(first.getDocument() == second.getDocument());
        });
    }
}
