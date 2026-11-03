public class PageParsing {
    static int parsePage(String text) throws InvalidPageException {
        int page;
        try { page = Integer.parseInt(text); }
        catch (NumberFormatException ex) { throw new InvalidPageException("不是有效int页码", ex); }
        if (page <= 0) throw new InvalidPageException("页码必须为正");
        return page;
    }
    public static void main(String[] args) {
        for (String input : new String[] {"24", "bad", "0", "-1", "1", "2147483647"}) {
            try { System.out.println("ok:" + parsePage(input)); }
            catch (InvalidPageException ex) {
                System.out.println("bad:" + ex.getMessage() + ":" + (ex.getCause() == null ? "none" : ex.getCause().getClass().getSimpleName()));
            }
        }
    }
}
class InvalidPageException extends Exception {
    private static final long serialVersionUID = 1L;
    InvalidPageException(String message) { super(message); }
    InvalidPageException(String message, Throwable cause) { super(message, cause); }
}
