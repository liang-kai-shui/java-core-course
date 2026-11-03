public class DispatchNotices {
    public static void main(String[] args) {
        NoticeBase[] notices = {new MailNotice("作业"), new SiteNotice("提醒")};
        for (NoticeBase n : notices) System.out.println(n.label());
        NoticeBase n = new MailNotice("旧题");
        if (n instanceof MailNotice) ((MailNotice) n).setSubject("提醒");
        System.out.println(n.label());
    }
}
abstract class NoticeBase {
    private String name;
    NoticeBase(String name) { this.name = name; }
    protected final String name() { return name; }
    protected final void rename(String name) { this.name = name; }
    public abstract String label();
}
class MailNotice extends NoticeBase {
    MailNotice(String name) { super(name); }
    void setSubject(String subject) { rename(subject); }
    @Override public String label() { return "mail:" + name(); }
}
final class SiteNotice extends NoticeBase {
    SiteNotice(String name) { super(name); }
    @Override public String label() { return "site:" + name(); }
}
