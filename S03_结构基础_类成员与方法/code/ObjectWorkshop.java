public class ObjectWorkshop {
    public static void main(String[] args) {
        PointsCard first = new PointsCard("Lin", 2);
        PointsCard second = new PointsCard("Mei");
        first.add(3);
        System.out.println(first.describe());
        System.out.println(second.describe());
        System.out.println("created=" + PointsCard.getCreated());
        int number = 2;
        changeNumber(number);
        System.out.println("number=" + number);
        replaceCard(first);
        System.out.println(first.describe());
        System.out.println("created=" + PointsCard.getCreated());
    }

    private static void changeNumber(int copy) {
        copy = 99;
    }

    private static void replaceCard(PointsCard copy) {
        copy.add(1);
        copy = new PointsCard("temporary", 0);
        copy.add(10);
    }
}

class PointsCard {
    private final String owner;
    private int points;
    private static int created = 0;

    public PointsCard(String owner, int points) {
        this.owner = owner;
        this.points = points;
        created++;
    }

    public PointsCard(String owner) {
        this(owner, 0);
    }

    public void add(int amount) {
        if (amount > 0) {
            points += amount;
        }
    }

    public String describe() {
        return owner + ":" + points;
    }

    public static int getCreated() {
        return created;
    }
}
