public class RuntimeAssertions {
    public static void main(String[] args) {
        int value = args.length == 0 ? 9 : Integer.parseInt(args[0]);
        System.out.println("enabled=" + RuntimeAssertions.class.desiredAssertionStatus());
        assert value >= 0 : "内部值不应为负:" + value;
        System.out.println(Math.sqrt(value));
    }
}
