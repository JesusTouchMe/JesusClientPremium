package cum.jesus.jesusclient.qol.modules;

public enum Category {
    COMBAT("Combat"), SKYBLOCK("Skyblock"), PLAYER("Player"), MACROS("Macros"), MOVEMENT("Movement"), FUNNY("Funny"), OTHER("Other"), RENDER("Render");

    private String name;

    Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
