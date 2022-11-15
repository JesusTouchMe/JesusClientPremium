package cum.jesus.jesusclient.qol.modules;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.qol.modules.combat.Cum;
import cum.jesus.jesusclient.qol.modules.combat.KillAura;
import cum.jesus.jesusclient.qol.modules.combat.Reach;
import cum.jesus.jesusclient.qol.modules.combat.Velo;
import cum.jesus.jesusclient.qol.modules.funny.AntiThrow;
import cum.jesus.jesusclient.qol.modules.funny.Retardation;
import cum.jesus.jesusclient.qol.modules.movement.*;
import cum.jesus.jesusclient.qol.modules.other.DiscordRPC;
import cum.jesus.jesusclient.qol.modules.other.HiddenShit;
import cum.jesus.jesusclient.qol.modules.render.NickHider;
import cum.jesus.jesusclient.qol.modules.player.InvMove;
import cum.jesus.jesusclient.qol.modules.player.Timer;
import cum.jesus.jesusclient.qol.modules.render.ClickGuiModule;
import cum.jesus.jesusclient.qol.modules.skyblock.AutoRogue;
import cum.jesus.jesusclient.qol.modules.skyblock.BonerThrower;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static DiscordRPC richPresence = new DiscordRPC();

    @NotNull
    private List<Module> modules = new ArrayList<>();

    public void addModules() {
        addModule(Velo.INSTANCE);
        addModule(new InvMove());
        addModule(new AutoRogue());
        addModule(NickHider.INSTANCE);
        addModule(ToggleSprint.INSTANCE);
        addModule(new Retardation());
        addModule(HiddenShit.INSTANCE);

        addModule(Reach.INSTANCE);
        addModule(richPresence);
        addModule(KillAura.INSTANCE);
        addModule(new Cum());
        addModule(Flight.INSTANCE);
        addModule(new NoFall());
        addModule(new Parkour());
        addModule(new Jesus());
        addModule(new BHop());
        addModule(new Timer());
        addModule(new AntiThrow());
        addModule(new BonerThrower());

        addModule(new ClickGuiModule());

        if (richPresence.getState())
            richPresence.onEnable();
    }

    private void addModule(@NotNull Module module) {
        modules.add(module);
        JesusClient.register(module);
        JesusClient.INSTANCE.valueManager.registerObject(module.getName(), module);
    }

    @NotNull
    public List<Module> getModules() {
        return modules;
    }

    @NotNull
    public <T extends Module> T getModule(Class<T> klass) {
        return (T) modules.stream().filter(mod -> mod.getClass() == klass).findFirst().orElse(null);
    }

    public Module getModule(@NotNull String name, boolean caseSensitive) {
        return modules.stream().filter(mod -> !caseSensitive && name.equalsIgnoreCase(mod.getName()) || name.equals(mod.getName())).findFirst().orElse(null);
    }

    public void handleKeypress(int key) {
        if (key == 0) return;
        for (Module m : modules) {
            if (m.getKeybind() == key) {
                m.toggle();
            }
        }
    }
}
