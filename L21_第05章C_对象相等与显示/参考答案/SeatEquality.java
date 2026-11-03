import java.util.Objects;
public class SeatEquality {
    public static void main(String[] args) {
        RoomSeat a = new RoomSeat("A1", 3), b = new RoomSeat("A1", 3), c = new RoomSeat("A1", 3);
        System.out.println(a.equals(b));
        System.out.println(a.equals(new RoomSeat("A1", 4)));
        System.out.println(a.equals(null));
        System.out.println(a.hashCode() == b.hashCode());
        System.out.println(a);
        System.out.println(a.equals(b) == b.equals(a));
        System.out.println(a.equals(b) && b.equals(c) && a.equals(c));
        System.out.println(new RoomSeat(null, 3).equals(new RoomSeat(null, 3)));
        System.out.println(new RoomSeat(null, 3).equals(a));
    }
}
final class RoomSeat {
    private final String room;
    private final int number;
    RoomSeat(String room, int number) { this.room = room; this.number = number; }
    @Override public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        RoomSeat other = (RoomSeat) object;
        return number == other.number && Objects.equals(room, other.room);
    }
    @Override public int hashCode() { return Objects.hash(room, number); }
    @Override public String toString() { return room + "#" + number; }
}
