import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import javax.swing.*;
public class ComponentTreeWalk {
    static void walk(Component c, String indent) {
        System.out.println(indent + c.getName());
        if (c instanceof Container) for (Component child : ((Container) c).getComponents()) walk(child, indent + "  ");
    }
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            JPanel root = new JPanel(); root.setName("root"); JPanel row = new JPanel(); row.setName("row");
            JLabel label = new JLabel("课程"); label.setName("label"); row.add(label); root.add(row); walk(root, "");
        });
    }
}
