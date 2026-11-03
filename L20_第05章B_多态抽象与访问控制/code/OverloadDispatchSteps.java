public class OverloadDispatchSteps {
    static String kind(AnimalVoice x) { return "general:" + x.voice(); }
    static String kind(CatVoice x) { return "specific:" + x.voice(); }
    public static void main(String[] args) {
        AnimalVoice a = new CatVoice();
        CatVoice b = (CatVoice) a;
        System.out.println(kind(a));
        System.out.println(kind(b));
    }
}
class AnimalVoice { String voice() { return "animal"; } }
class CatVoice extends AnimalVoice { @Override String voice() { return "cat"; } }
