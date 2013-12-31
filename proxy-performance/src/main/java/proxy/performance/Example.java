package proxy.performance;

public class Example implements IExample {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }
}
