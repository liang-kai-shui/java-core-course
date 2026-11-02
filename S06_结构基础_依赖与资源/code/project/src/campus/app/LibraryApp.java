package campus.app;

import campus.lib.LabelMaker;
import java.io.InputStream;
import java.util.Scanner;

public class LibraryApp {
    public static void main(String[] args) {
        InputStream stream = LibraryApp.class.getResourceAsStream("/campus/app/message.txt");
        if (stream == null) {
            throw new IllegalStateException("missing resource");
        }
        try (Scanner in = new Scanner(stream, "UTF-8")) {
            String text = in.nextLine();
            System.out.println(LabelMaker.decorate(text));
        }
    }
}
