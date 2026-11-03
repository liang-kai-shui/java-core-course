import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;

public class ListenerRegistrationOrder {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            JButton button = new JButton("go");
            List<String> log = new ArrayList<>();
            ActionListener first = event -> log.add("first");
            ActionListener second = event -> log.add("second");
            ActionListener third = event -> log.add("third");
            button.addActionListener(first);
            button.addActionListener(second);
            button.addActionListener(third);
            button.doClick(0);
            System.out.println(String.join(",", log));
            button.removeActionListener(second);
            button.doClick(0);
            System.out.println(String.join(",", log));
        });
    }
}
