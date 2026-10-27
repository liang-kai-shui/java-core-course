public class MenuDemo {
    public static void main(String[] args) {
        int option = args.length == 0 ? 1 : Integer.parseInt(args[0]);
        switch (option) {
            case 1:
                System.out.println("start");
                break;
            case 2:
                System.out.println("help");
                break;
            default:
                System.out.println("unknown");
        }
    }
}
