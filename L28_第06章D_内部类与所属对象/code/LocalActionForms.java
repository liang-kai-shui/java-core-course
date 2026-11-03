public class LocalActionForms {
    private final String prefix = "外";
    Runnable make(String suffix) {
        class Local implements Runnable {
            @Override public void run() { System.out.println(prefix + suffix); }
        }
        return new Local();
    }
    void forms() {
        Runnable local = make("!");
        Runnable anonymous = new Runnable() {
            private final String prefix = "内";
            @Override public void run() { System.out.println(this.prefix + "/" + LocalActionForms.this.prefix); }
        };
        Runnable lambda = () -> System.out.println(this.prefix);
        local.run(); anonymous.run(); lambda.run();
    }
    public static void main(String[] args) { new LocalActionForms().forms(); }
}
