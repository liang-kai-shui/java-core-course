/** Provides a small documented printing-cost example. */
public class DocumentedCost {
    /**
     * Counts sheets for nonnegative inputs; negative inputs count as zero.
     * @param pages pages per copy, with a small enough product for int
     * @param copies number of copies
     * @return total sheets, or zero if an input is negative
     */
    public static int sheets(int pages, int copies) {
        if (pages < 0 || copies < 0) return 0;
        return pages * copies;
    }
    /**
     * Runs the fixed example.
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {
        System.out.println(sheets(4, 3));
    }
}
