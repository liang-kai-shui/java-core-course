import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
public class LoggingPipeline {
    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger("course.lesson32");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.FINE);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        StreamHandler handler = new StreamHandler(buffer, new CompactLog());
        handler.setEncoding("UTF-8");
        handler.setLevel(Level.INFO);
        logger.addHandler(handler);
        try {
            logger.fine("hidden");
            handler.setLevel(Level.FINE);
            logger.fine("step");
            handler.setFilter(record -> !record.getMessage().equals("skip"));
            logger.info("skip");
            logger.log(Level.INFO, "ready {0}", "L32");
            logger.log(Level.WARNING, "read", new IOException("missing"));
            handler.flush();
            System.out.print(new String(buffer.toByteArray(), StandardCharsets.UTF_8));
        } finally { logger.removeHandler(handler); handler.close(); }
    }
}
class CompactLog extends Formatter {
    @Override public String format(LogRecord record) {
        String error = record.getThrown() == null ? "" : "|" + record.getThrown().getClass().getSimpleName();
        return record.getLevel().getName() + ":" + formatMessage(record) + error + "\n";
    }
}
