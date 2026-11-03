public class TwoOuterLinks {
    private final String title;
    TwoOuterLinks(String title) { this.title = title; }
    class Card { String label() { return TwoOuterLinks.this.title; } }
    public static void main(String[] args) {
        TwoOuterLinks one = new TwoOuterLinks("青禾");
        TwoOuterLinks two = new TwoOuterLinks("海风");
        Card a = one.new Card();
        Card b = two.new Card();
        Card alias = a;
        System.out.println(alias.label());
        System.out.println(b.label());
        System.out.println(a == alias);
    }
}
