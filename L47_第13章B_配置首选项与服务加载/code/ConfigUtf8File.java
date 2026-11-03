import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Properties;
public class ConfigUtf8File {
    public static void main(String[] args) {
        if (args.length == 0) { System.out.println("path-required"); return; }
        try {
            Path root = Paths.get("").toRealPath(), path = Paths.get(args[0]).toAbsolutePath().normalize();
            Path parent = path.getParent(); if (parent == null || !parent.toRealPath().startsWith(root)) throw new IOException("outside course");
            Properties p = new Properties(); p.setProperty("title", "数组");
            try (Writer out = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW)) { p.store(out, "course config"); }
            Properties read = new Properties(); try (Reader in = Files.newBufferedReader(path, StandardCharsets.UTF_8)) { read.load(in); }
            System.out.println(read.getProperty("title") + ":" + read.getProperty("width", "300"));
        } catch (FileAlreadyExistsException e) { System.out.println("already-exists"); }
        catch (IOException e) { System.out.println("io-error"); }
    }
}
