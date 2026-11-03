public class ReferenceAbsence {
    public static void main(String[] args) {
        ProfileCard card = new ProfileCard("");
        ProfileCard alias = card;
        alias = null;
        System.out.println(alias == null);
        System.out.println("length=" + card.getName().length());
        ProfileCard missing = null;
        if (missing != null) System.out.println(missing.getName());
        else System.out.println("no-card");
    }
}
class ProfileCard {
    private final String name;
    ProfileCard(String name) { this.name = name; }
    String getName() { return name; }
}
