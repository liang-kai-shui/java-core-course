import java.awt.EventQueue;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
public class FileChoicePolicy {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("文本 (*.txt)", "txt");
            if (args.length > 0 && args[0].equals("--check")) {
                System.out.println(filter.accept(new File("README.TXT"))); System.out.println(filter.accept(new File("image.png"))); return;
            }
            JFileChooser chooser = new JFileChooser(); chooser.setFileFilter(filter); chooser.setAcceptAllFileFilterUsed(false);
            JFrame f = new JFrame("只选择，不写入"); JLabel path = new JLabel("尚未选择"); JButton choose = new JButton("选择文本");
            choose.addActionListener(e -> {
                if (chooser.showOpenDialog(f) != JFileChooser.APPROVE_OPTION) return;
                File file = chooser.getSelectedFile();
                if (file != null && file.isFile() && filter.accept(file)) path.setText(file.getPath());
                else JOptionPane.showMessageDialog(f, "请选择普通文本文件");
            });
            JPanel p = new JPanel(); p.add(choose); p.add(path); f.add(p); f.setSize(600, 150); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setVisible(true);
        });
    }
}
