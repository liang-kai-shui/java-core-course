public class GradeDemo {
    public static void main(String[] args) {
        int score = args.length == 0 ? 60 : Integer.parseInt(args[0]);
        if (score < 0 || score > 100) {
            System.out.println("invalid");
        } else if (score >= 90) {
            System.out.println("excellent");
        } else if (score >= 60) {
            System.out.println("pass");
        } else {
            System.out.println("retry");
        }
    }
}
