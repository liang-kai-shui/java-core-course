package campus.impl;
import campus.spi.LabelFormatter;
public class PlainFormatter implements LabelFormatter {
    public PlainFormatter() { }
    public int rank() { return 1; }
    public String format(String text) { return text; }
}
