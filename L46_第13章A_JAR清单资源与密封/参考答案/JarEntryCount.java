import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.jar.*;
public class JarEntryCount {
    public static void main(String[] args) throws Exception {
        Manifest mf = new Manifest(); mf.getMainAttributes().putValue("Manifest-Version", "1.0");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (JarOutputStream out = new JarOutputStream(bytes, mf)) {
            out.putNextEntry(new JarEntry("campus/about.txt")); out.write(new byte[] {65}); out.closeEntry();
        }
        try (JarInputStream in = new JarInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            System.out.println(in.getManifest().getMainAttributes().getValue("Manifest-Version"));
            JarEntry entry = in.getNextJarEntry(); System.out.println(entry.getName()); System.out.println(in.read());
        }
    }
}
