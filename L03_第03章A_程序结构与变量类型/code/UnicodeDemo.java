public class UnicodeDemo {
    public static void main(String[] args) {
        String chinese = "中";
        String emoji = "\uD83D\uDE00";
        System.out.println("chinese units=" + chinese.length());
        System.out.println("emoji units=" + emoji.length());
        System.out.println("emoji code points=" + emoji.codePointCount(0, emoji.length()));
    }
}
