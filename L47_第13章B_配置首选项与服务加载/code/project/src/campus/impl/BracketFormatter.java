package campus.impl;
import campus.spi.LabelFormatter;
public class BracketFormatter implements LabelFormatter {
    public BracketFormatter() { }
    public int rank() { return 2; }
    public String format(String text) { return "[" + text + "]"; }
}
