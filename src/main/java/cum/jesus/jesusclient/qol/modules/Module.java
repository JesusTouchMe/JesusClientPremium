package cum.jesus.jesusclient.qol.modules;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.qol.modules.render.ClickGuiModule;
import cum.jesus.jesusclient.utils.Utils;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public abstract class Module {
    protected static final Minecraft mc = Minecraft.getMinecraft();
    private String name;
    private String description;
    private Category category;
    private boolean canBeEnabled;
    private boolean hidden;
    private int keybind;
    private boolean state;
    private boolean shouldNotify = true;

    protected Module(String name, String description, Category moduleCategory) {
        this(name, description, moduleCategory, true, false, Keyboard.KEY_NONE);
    }

    protected Module(String name, String description, Category category, boolean canBeEnabled, boolean hidden, int keybind) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.canBeEnabled = canBeEnabled;
        this.hidden = hidden;
        this.keybind = keybind;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isCanBeEnabled() {
        return canBeEnabled;
    }

    public boolean isHidden() {
        return hidden;
    }

    public int getKeybind() {
        return keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public boolean getState() {
        return state;
    }

    public boolean shouldNotify() {
        return shouldNotify;
    }

    public void setState(boolean state) {
        if (state) {
            this.state = true;

            onEnable();

            if (!ClickGuiModule.hideNotifs.getObject() && shouldNotify()) {
                JesusClient.sendPrefixMessage(Utils.getColouredBoolean(state) + " " + getName());
            }

        } else {
            this.state = false;

            onDisable();

            if (!ClickGuiModule.hideNotifs.getObject() && shouldNotify){
                JesusClient.sendPrefixMessage(Utils.getColouredBoolean(state) + " " + getName());
            }
        }
    }

    public void setStateNoNotif(boolean state) {
        if (state) {
            this.state = true;
            onEnable();
        } else {
            this.state = false;
            onDisable();
        }
    }

    public void toggle() {
        setState(!getState());
    }

    protected void onEnable() {

    }

    protected void onDisable() {

    }
}
