import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
public class TaskAudit {
    static void record(Logger logger, String title, int minutes) {
        if (minutes < 0) throw new IllegalArgumentException("negative minutes");
        logger.info(title + ":" + minutes);
    }
    static int area(int width, int height) { return width * height; }
    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger("course.audit");
        logger.setUseParentHandlers(false); logger.setLevel(Level.INFO);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        StreamHandler handler = new StreamHandler(buffer, new AuditFormat());
        handler.setEncoding("UTF-8"); handler.setLevel(Level.ALL); logger.addHandler(handler);
        try {
            record(logger, "数组", 20);
            try { record(logger, "数组", -1); }
            catch (IllegalArgumentException ex) { logger.warning(ex.getMessage()); }
            handler.flush();
            System.out.print(new String(buffer.toByteArray(), StandardCharsets.UTF_8));
        } finally { logger.removeHandler(handler); handler.close(); }
        System.out.println("area=" + area(2, 3));
    }
}
class AuditFormat extends Formatter {
    @Override public String format(LogRecord record) { return record.getLevel().getName() + ":" + formatMessage(record) + "\n"; }
}
