public class MinuteUnitContract {
    /**
     * Converts minutes to seconds; nonpositive inputs count as zero.
     * @param minutes whole minutes
     * @return nonnegative whole seconds
     */
    public static long seconds(int minutes) {
        return minutes <= 0 ? 0 : minutes * 60L;
    }
    public static void main(String[] args) {
        System.out.println(seconds(-1));
        System.out.println(seconds(0));
        System.out.println(seconds(2));
        System.out.println(seconds(Integer.MAX_VALUE));
    }
}
