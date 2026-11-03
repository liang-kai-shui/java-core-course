package campus.app;
import java.awt.EventQueue;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;
public class DeployMain {
    public static void main(String[] args) {
        String title;
        InputStream stream = DeployMain.class.getResourceAsStream("title.txt");
        if (stream == null) throw new IllegalStateException("missing resource");
        try (Scanner in = new Scanner(stream, "UTF-8")) { title = in.hasNextLine() ? in.nextLine() : "课程"; }
        if (args.length == 0 || !args[0].equals("--window")) { System.out.println("ready:" + title); return; }
        final String caption = title;
        EventQueue.invokeLater(() -> {
            JFrame f = new JFrame(caption); f.add(new JLabel("从JAR资源读取标题", SwingConstants.CENTER));
            f.setSize(360, 150); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.setVisible(true);
        });
    }
}
