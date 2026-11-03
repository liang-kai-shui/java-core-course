/** Converts whole minutes to seconds. */
public class MinuteDocs {
    /**
     * Converts positive minutes; nonpositive inputs yield zero.
     * @param minutes whole minutes
     * @return seconds, calculated with long multiplication
     */
    public static long seconds(int minutes) { return minutes <= 0 ? 0L : minutes * 60L; }
    /** @param args unused arguments */
    public static void main(String[] args) {
        System.out.println(seconds(2));
        System.out.println(seconds(0));
        System.out.println(seconds(-1));
        System.out.println(seconds(Integer.MAX_VALUE));
    }
}
