import java.awt.EventQueue;
import java.util.Arrays;
import javax.swing.JPasswordField;
public class PasswordArrayLifecycle {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            JPasswordField field = new JPasswordField("demo", 8); char originalEcho = field.getEchoChar(); char[] chars = field.getPassword();
            System.out.println("length=" + chars.length); Arrays.fill(chars, '\0'); field.setText("");
            boolean cleared = true; for (char c : chars) if (c != '\0') cleared = false; System.out.println("cleared=" + cleared);
            field.setEchoChar((char) 0); System.out.println("masked=" + field.echoCharIsSet()); field.setEchoChar(originalEcho);
        });
    }
}
