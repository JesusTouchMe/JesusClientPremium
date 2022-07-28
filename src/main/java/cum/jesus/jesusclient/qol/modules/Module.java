package cum.jesus.jesusclient.qol.modules;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.utils.MilliTimer;
import net.minecraft.client.Minecraft;

public abstract class Module {
    private String h = "literally just need this to get module names easier";
    protected Minecraft mc = Minecraft.getMinecraft();
    private final String arraylistName;
    private final boolean toggled;
    public MilliTimer toggledTime = new MilliTimer();

    public Module(String name, boolean toggled) {
        this.arraylistName = name;
        this.toggled = toggled;
    }

    public String getName() {
        return this.arraylistName;
    }

    public boolean isToggled() {
        return this.toggled;
    }

    public static <T> T getModule(Class<T> module) {
        for (Module m : JesusClient.modules) {
            if (m.getClass().equals(module))
                return (T)m;
        }
        return null;
    }

    public static Module getModuleByName(String name) {
        for (Module m : JesusClient.modules) {
            if (m.getName().equalsIgnoreCase(name))
                return m;
        }
        return null;
    }
}
