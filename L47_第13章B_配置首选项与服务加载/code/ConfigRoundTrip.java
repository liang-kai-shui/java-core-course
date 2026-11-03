import java.io.*;
import java.util.Properties;
public class ConfigRoundTrip {
    public static void main(String[] args) throws Exception {
        Properties defaults = new Properties(); defaults.setProperty("width", "300");
        Properties settings = new Properties(defaults); settings.setProperty("title", "数组");
        StringWriter out = new StringWriter(); settings.store(out, "course config");
        Properties copy = new Properties(); copy.load(new StringReader(out.toString()));
        System.out.println(copy.getProperty("title")); System.out.println(copy.getProperty("width"));
        Properties withDefaults = new Properties(defaults); withDefaults.load(new StringReader(out.toString())); System.out.println(withDefaults.getProperty("width"));
    }
}
