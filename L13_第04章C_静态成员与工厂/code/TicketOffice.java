public class TicketOffice {
    public static void main(String[] args) {
        Ticket a = Ticket.regular();
        Ticket b = Ticket.guest();
        System.out.println(a.summary());
        System.out.println(b.summary());
        System.out.println("issued=" + Ticket.issued());
    }
}
class Ticket {
    private static int nextId = 1;
    private final int id;
    private final String kind;
    private Ticket(String kind) {
        id = nextId;
        nextId++;
        this.kind = kind;
    }
    static Ticket regular() { return new Ticket("普通"); }
    static Ticket guest() { return new Ticket("访客"); }
    static int issued() { return nextId - 1; }
    String summary() { return id + ":" + kind; }
}
