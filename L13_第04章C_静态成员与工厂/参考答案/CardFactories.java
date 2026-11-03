public class CardFactories {
    public static void main(String[] args) {
        MemberCard a = MemberCard.student();
        MemberCard b = MemberCard.teacher();
        MemberCard c = MemberCard.student();
        System.out.println(a.summary());
        System.out.println(b.summary());
        System.out.println(c.summary());
        System.out.println("count=" + MemberCard.count());
    }
}
class MemberCard {
    private static int nextId = 100;
    private static int issuedCount;
    private final int id;
    private final String kind;
    private MemberCard(String kind) {
        id = nextId++;
        issuedCount++;
        this.kind = kind;
    }
    static MemberCard student() { return new MemberCard("学生"); }
    static MemberCard teacher() { return new MemberCard("教师"); }
    static int count() { return issuedCount; }
    String summary() { return id + ":" + kind; }
}
