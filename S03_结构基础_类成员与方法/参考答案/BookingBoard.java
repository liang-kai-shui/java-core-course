public class BookingBoard {
    public static void main(String[] args) {
        TicketBox box = new TicketBox(5);
        System.out.println(box.book(0));
        System.out.println(box.book(3));
        System.out.println(box.book(3));
        System.out.println(box.book(2));
        System.out.println(box.remaining());
    }
}
class TicketBox {
    private final int capacity;
    private int booked = 0;
    public TicketBox(int capacity) { this.capacity = capacity; }
    public boolean book(int count) {
        if (count > 0 && count <= capacity - booked) {
            booked += count;
            return true;
        }
        return false;
    }
    public int remaining() { return capacity - booked; }
}
