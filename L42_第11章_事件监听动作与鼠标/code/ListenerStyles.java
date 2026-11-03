import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ListenerStyles {
    static class Counter implements ActionListener {
        private int count;
        public void actionPerformed(ActionEvent event) { count++; }
    }

    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            Counter counter = new Counter();
            JButton byClass = new JButton("class");
            JButton byAnonymous = new JButton("anonymous");
            JButton byLambda = new JButton("lambda");
            byClass.addActionListener(counter);
            byAnonymous.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) { counter.count++; }
            });
            byLambda.addActionListener(event -> counter.count++);
            byClass.doClick(0);
            byAnonymous.doClick(0);
            byLambda.doClick(0);
            System.out.println("count=" + counter.count);
        });
    }
}
