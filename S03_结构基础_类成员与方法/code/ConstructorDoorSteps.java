public class ConstructorDoorSteps {
    public static void main(String[] args) {
        new DoorCard("Lin");
        new DoorCard("Mei", 4);
        System.out.println("created=" + DoorCard.created());
    }
}
class DoorCard {
    private static int count;
    private final String owner;
    private final int points;
    DoorCard(String owner) { this(owner, 0); System.out.println("outer"); }
    DoorCard(String owner, int points) {
        this.owner = owner; this.points = points; count++;
        System.out.println("core:" + this.owner + ":" + this.points);
    }
    static int created() { return count; }
}
