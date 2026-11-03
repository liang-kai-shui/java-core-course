package campus.app;
import java.util.ServiceLoader;
import campus.spi.LabelFormatter;
public class PluginMain {
    public static void main(String[] args) {
        LabelFormatter best = null;
        for (LabelFormatter formatter : ServiceLoader.load(LabelFormatter.class)) {
            if (best == null || formatter.rank() > best.rank()) best = formatter;
        }
        String text = args.length == 0 ? "数组" : args[0]; System.out.println(best == null ? text : best.format(text));
    }
}
