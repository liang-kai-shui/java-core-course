import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import java.util.stream.Stream;
import javax.tools.*;
public class SealingOriginCheck {
    static void jar(Path output, Path cls, String entry, boolean sealed) throws IOException {
        Manifest mf = new Manifest(); mf.getMainAttributes().putValue("Manifest-Version", "1.0");
        if (sealed) mf.getMainAttributes().putValue("Sealed", "true");
        try (JarOutputStream out = new JarOutputStream(Files.newOutputStream(output), mf)) {
            out.putNextEntry(new JarEntry(entry)); Files.copy(cls, out); out.closeEntry();
        }
    }
    static void deleteTree(Path root) throws IOException {
        List<Path> all = new ArrayList<>(); try (Stream<Path> paths = Files.walk(root)) { paths.forEach(all::add); }
        all.sort(Comparator.reverseOrder()); for (Path p : all) Files.delete(p);
    }
    public static void main(String[] args) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); if (compiler == null) throw new IllegalStateException("need JDK");
        Path root = Files.createTempDirectory("course-seal-");
        try {
            Path a = root.resolve("A.java"), b = root.resolve("B.java"), classes = root.resolve("classes"); Files.createDirectory(classes);
            Files.write(a, "package tiny; public class A {}".getBytes(StandardCharsets.UTF_8));
            Files.write(b, "package tiny; public class B {}".getBytes(StandardCharsets.UTF_8));
            int result = compiler.run(null, null, null, "--release", "8", "-encoding", "UTF-8", "-d", classes.toString(), a.toString(), b.toString());
            if (result != 0) throw new IllegalStateException("compile failed");
            Path first = root.resolve("first.jar"), second = root.resolve("second.jar");
            jar(first, classes.resolve("tiny/A.class"), "tiny/A.class", true); jar(second, classes.resolve("tiny/B.class"), "tiny/B.class", false);
            try (URLClassLoader loader = new URLClassLoader(new URL[] {first.toUri().toURL(), second.toUri().toURL()}, null)) {
                Class<?> type = Class.forName("tiny.A", true, loader); System.out.println("sealed=" + type.getPackage().isSealed());
                try { Class.forName("tiny.B", true, loader); throw new AssertionError("mixed origin accepted"); }
                catch (SecurityException expected) { System.out.println("mixed-origin=rejected"); }
            }
        } finally { deleteTree(root); }
    }
}
