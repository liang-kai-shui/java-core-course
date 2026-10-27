import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
public class FileUtf8Demo {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("usage: FileUtf8Demo <new-experiment-file>");
            return;
        }
        Path path = Paths.get(args[0]);
        Path parent = path.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {
            writer.println("你好，Java！");
        }
        try (Scanner input = new Scanner(path, "UTF-8")) {
            System.out.println(input.nextLine());
        }
    }
}
