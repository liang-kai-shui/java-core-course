public class MemberRooms {
    private final String title;
    MemberRooms(String title) { this.title = title; }
    class Reader {
        private final String title = "读者";
        String display() { return this.title + "/" + MemberRooms.this.title; }
    }
    static class Range {
        private final int min, max;
        Range(int min, int max) { this.min = min; this.max = max; }
    }
    static Range minmax(int[] values) {
        if (values.length == 0) throw new IllegalArgumentException("empty array");
        int min = values[0], max = values[0];
        for (int value : values) { min = Math.min(min, value); max = Math.max(max, value); }
        return new Range(min, max);
    }
    public static void main(String[] args) {
        MemberRooms east = new MemberRooms("东馆");
        Reader reader = east.new Reader();
        System.out.println(reader.display());
        Range range = minmax(new int[] {7, 2, 9});
        System.out.println(range.min + ".." + range.max);
    }
}
