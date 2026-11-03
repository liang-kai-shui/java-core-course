import java.util.Objects;
public class BookmarkEquality {
    public static void main(String[] args) {
        BookmarkKey a = new BookmarkKey("B7", 12);
        BookmarkKey b = new BookmarkKey("B7", 12);
        System.out.println(a == b);
        System.out.println(a.equals(b));
        System.out.println(a.hashCode() == b.hashCode());
        System.out.println(a);
    }
}
final class BookmarkKey {
    private final String book;
    private final int page;
    BookmarkKey(String book, int page) { this.book = book; this.page = page; }
    @Override public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null || getClass() != otherObject.getClass()) return false;
        BookmarkKey other = (BookmarkKey) otherObject;
        return page == other.page && Objects.equals(book, other.book);
    }
    @Override public int hashCode() { return Objects.hash(book, page); }
    @Override public String toString() { return book + "@" + page; }
}
