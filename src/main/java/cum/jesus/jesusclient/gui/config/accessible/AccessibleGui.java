package cum.jesus.jesusclient.gui.config.accessible;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class AccessibleGui {
    public static boolean isMenuActive = false;
    private Minecraft mc = Minecraft.getMinecraft();
    private final int height = 220;
    private final int width = 350;

    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Post event) {
        if (isMenuActive && JesusClient.mc.thePlayer != null && event.type.equals(RenderGameOverlayEvent.ElementType.ALL)) {
            ScaledResolution res = new ScaledResolution(mc);
            Color color = new Color(25, 25, 25, 236);
            Color outlineColor = new Color(57, 160, 169);

            RenderUtils.drawBorderedRoundedRect(
                    res.getScaledWidth() - 2 - width,
                    res.getScaledHeight() - 30 - height,
                    width,
                    height,
                    3.0f,
                    2.0f,
                    color.getRGB(),
                    outlineColor.getRGB());
        }
    }
}
