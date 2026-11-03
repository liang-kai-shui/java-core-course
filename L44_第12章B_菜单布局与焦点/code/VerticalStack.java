import java.awt.*;
import javax.swing.*;
public class VerticalStack {
    static class Stack implements LayoutManager {
        final int gap; Stack(int gap) { if (gap < 0) throw new IllegalArgumentException("gap"); this.gap = gap; }
        public void addLayoutComponent(String name, Component c) { }
        public void removeLayoutComponent(Component c) { }
        private Dimension measure(Container p) {
            Insets in = p.getInsets(); int width = 0, height = 0, count = 0;
            for (Component c : p.getComponents()) if (c.isVisible()) {
                Dimension d = c.getPreferredSize(); width = Math.max(width, d.width); height += d.height; count++;
            }
            return new Dimension(width + in.left + in.right, height + Math.max(0, count - 1) * gap + in.top + in.bottom);
        }
        public Dimension preferredLayoutSize(Container p) { synchronized (p.getTreeLock()) { return measure(p); } }
        public Dimension minimumLayoutSize(Container p) { synchronized (p.getTreeLock()) { return measure(p); } }
        public void layoutContainer(Container p) {
            synchronized (p.getTreeLock()) {
                Insets in = p.getInsets(); int y = in.top, width = Math.max(0, p.getWidth() - in.left - in.right);
                for (Component c : p.getComponents()) if (c.isVisible()) {
                    int h = c.getPreferredSize().height; c.setBounds(in.left, y, width, h); y += h + gap;
                }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            JPanel p = new JPanel(new Stack(5)); p.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
            p.add(new JButton("一")); p.add(new JButton("二")); JButton hidden = new JButton("隐藏"); hidden.setVisible(false); p.add(hidden);
            p.setSize(200, 120); p.doLayout(); int n = 0; for (Component c : p.getComponents()) if (c.isVisible()) n++;
            System.out.println("visible=" + n); p.removeAll(); System.out.println("emptyHeight=" + p.getPreferredSize().height);
        });
    }
}
