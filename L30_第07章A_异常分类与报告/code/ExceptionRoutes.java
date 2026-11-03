public class ExceptionRoutes {
    static int parse(String text) throws InvalidMinutesException {
        int value;
        try { value = Integer.parseInt(text); }
        catch (NumberFormatException ex) { throw new InvalidMinutesException("不是整数", ex); }
        if (value <= 0) throw new InvalidMinutesException("必须为正");
        return value;
    }
    public static void main(String[] args) {
        for (String text : new String[] {"15", "x", "-1"}) {
            try { System.out.println("ok:" + parse(text)); }
            catch (InvalidMinutesException ex) {
                String cause = ex.getCause() == null ? "none" : ex.getCause().getClass().getSimpleName();
                System.out.println("bad:" + ex.getMessage() + " cause=" + cause);
            }
        }
    }
}
class InvalidMinutesException extends Exception {
    private static final long serialVersionUID = 1L;
    InvalidMinutesException(String message) { super(message); }
    InvalidMinutesException(String message, Throwable cause) { super(message, cause); }
}
