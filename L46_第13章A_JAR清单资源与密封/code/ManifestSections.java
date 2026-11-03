import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.jar.Manifest;
public class ManifestSections {
    public static void main(String[] args) throws Exception {
        String text = "Manifest-Version: 1.0\nMain-Class: campus.app.Main\nClass-Path: lib/text.jar\n\nName: campus/model/\nSealed: true\n\n";
        Manifest manifest = new Manifest(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));
        System.out.println(manifest.getMainAttributes().getValue("Main-Class"));
        System.out.println(manifest.getMainAttributes().getValue("Class-Path"));
        System.out.println(manifest.getAttributes("campus/model/").getValue("Sealed"));
    }
}
