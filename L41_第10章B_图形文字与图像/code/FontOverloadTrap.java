import java.awt.Font;

public class FontOverloadTrap {
    public static void main(String[] args) {
        Font base = new Font("SansSerif", Font.PLAIN, 12);
        Font byStyle = base.deriveFont(14);
        Font bySize = base.deriveFont(14.0f);
        System.out.println("style-arg: style=" + byStyle.getStyle() + " size=" + byStyle.getSize2D());
        System.out.println("size-arg: style=" + bySize.getStyle() + " size=" + bySize.getSize2D());
        Font italic = base.deriveFont(Font.ITALIC);
        System.out.println("italic=" + italic.isItalic() + " size=" + italic.getSize2D());
    }
}
