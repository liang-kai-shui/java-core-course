package campus.app;
import java.io.InputStream;
import java.util.Scanner;
public class ResourceMain {
    public static void main(String[] args) {
        InputStream stream = ResourceMain.class.getResourceAsStream("about.txt");
        if (stream == null) throw new IllegalStateException("missing resource: campus/app/about.txt");
        try (Scanner in = new Scanner(stream, "UTF-8")) { while (in.hasNextLine()) System.out.println(in.nextLine()); }
    }
}
