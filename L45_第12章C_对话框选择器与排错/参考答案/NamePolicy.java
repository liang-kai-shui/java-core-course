public class NamePolicy {
    static String normalized(String draft) {
        if (draft == null) return null;
        String value = draft.trim(); return value.isEmpty() || value.length() > 12 ? null : value;
    }
    public static void main(String[] args) {
        System.out.println(normalized(null) == null); System.out.println(normalized("  ") == null);
        System.out.println(normalized("abcdefghijkl")); System.out.println(normalized("abcdefghijklm") == null);
        String old = "循环", next = normalized(null); if (next != null) old = next; System.out.println(old);
    }
}
