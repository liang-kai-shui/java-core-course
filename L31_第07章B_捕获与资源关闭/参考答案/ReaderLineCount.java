import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
public class ReaderLineCount {
    static int countLines(String text) throws IOException {
        int count = 0;
        try (BufferedReader in = new BufferedReader(new StringReader(text))) {
            String line;
            while ((line = in.readLine()) != null) count++;
        }
        return count;
    }
    public static void main(String[] args) throws IOException {
        System.out.println(countLines("A\nB\n"));
        System.out.println(countLines(""));
        System.out.println(countLines("\n"));
        System.out.println(countLines("A\n\nB"));
    }
}
