public class SchoolRooms {
    private final String school;
    SchoolRooms(String school) { this.school = school; }
    class Card {
        private final String owner;
        Card(String owner) { this.owner = owner; }
        String display() { return owner + "@" + SchoolRooms.this.school; }
    }
    static class Result {
        private final int count;
        Result(int count) { this.count = count; }
    }
    public static void main(String[] args) {
        SchoolRooms first = new SchoolRooms("青禾"), second = new SchoolRooms("海风");
        System.out.println(first.new Card("林").display());
        System.out.println(second.new Card("梅").display());
        Result result = new Result(2);
        System.out.println(result.count);
    }
}
