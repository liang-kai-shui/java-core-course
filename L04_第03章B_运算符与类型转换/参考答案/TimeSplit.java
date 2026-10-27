public class TimeSplit {
    public static void main(String[] args) {
        int total = 3671;
        int hours = total / 3600;
        int minutes = (total % 3600) / 60;
        int seconds = total % 60;
        System.out.println("hours=" + hours);
        System.out.println("minutes=" + minutes);
        System.out.println("seconds=" + seconds);
    }
}
