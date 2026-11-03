import java.io.StringReader;
import java.util.Properties;
public class ConfigFallbackSteps {
    static int positive(String text, int fallback) {
        try { int n = Integer.parseInt(text); return n > 0 ? n : fallback; }
        catch (NumberFormatException e) { return fallback; }
    }
    public static void main(String[] args) throws Exception {
        Properties defaults = new Properties(); defaults.setProperty("width", "300"); defaults.setProperty("height", "200");
        Properties settings = new Properties(defaults); settings.load(new StringReader("title=数组\nwidth=abc\n"));
        System.out.println(settings.getProperty("title", "课程"));
        System.out.println(positive(settings.getProperty("width"), 300)); System.out.println(settings.getProperty("height"));
    }
}
