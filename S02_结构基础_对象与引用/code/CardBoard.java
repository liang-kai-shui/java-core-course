public class CardBoard {
    public static void main(String[] args) {
        ReaderCard first = new ReaderCard("Lin");
        ReaderCard alias = first;
        ReaderCard other = new ReaderCard("Lin");
        alias.rename("Mei");
        System.out.println(first.getName());
        System.out.println(other.getName());
        System.out.println(first == alias);
        System.out.println(first == other);
    }
}

class ReaderCard {
    private String name;

    public ReaderCard(String name) {
        this.name = name;
    }

    public void rename(String newName) {
        name = newName;
    }

    public String getName() {
        return name;
    }
}
