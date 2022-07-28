package cum.jesus.jesusclient.command.commands;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.command.Command;
import cum.jesus.jesusclient.utils.Utils;
import jline.internal.Log;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

public class BanCommand extends Command {
    private static long time = 0;
    private static boolean isDoingThing = false;
    public BanCommand() {
        super("selfban", "Bans you from the server you are on", 0, 1, new String[] { "Confirmation" });
    }

    public void onCall(String[] args) {
        if (args == null) {
            incorrectArgs();
            return;
        }

        if (args.length == 2 && args[1].equalsIgnoreCase("confirm")) {
            time = System.currentTimeMillis();
            isDoingThing = true;

            JesusClient.sendPrefixMessage("You will get banned in a few seconds. Don't come crying to me");

            Log.info(time);
            Log.info(isDoingThing);
        } else {
            JesusClient.sendPrefixMessage("-selfban confirm");
        }
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent t) {
        if (t.phase != TickEvent.Phase.START)
            return;
        if (JesusClient.mc.thePlayer == null || JesusClient.mc.theWorld == null)
            return;

        //System.out.println("---");
        //System.out.println(System.currentTimeMillis());
        //System.out.println(time);
        //System.out.println(System.currentTimeMillis() - time);
        if (isDoingThing && System.currentTimeMillis() - time > 2000) {
            (Utils.getTimer()).timerSpeed = 6.9f;
            JesusClient.sendPrefixMessage("You will get banned in a few seconds. Don't come crying to me");
            KeyBinding.setKeyBindState(JesusClient.mc.gameSettings.keyBindInventory.getKeyCode(), true);
            for (int i = 0; i < 10; i++) {
                JesusClient.mc.getNetHandler().getNetworkManager().sendPacket((Packet) new C08PacketPlayerBlockPlacement(new BlockPos((new Random()).nextInt(), (new Random()).nextInt(), (new Random()).nextInt()), 1, JesusClient.mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
                JesusClient.mc.thePlayer.setPosition((new Random()).nextDouble(), (new Random()).nextDouble(), (new Random()).nextDouble());
                JesusClient.mc.thePlayer.motionX = (new Random()).nextDouble();
                JesusClient.mc.thePlayer.motionY = (new Random()).nextDouble();
                JesusClient.mc.thePlayer.motionZ = (new Random()).nextDouble();
                JesusClient.mc.thePlayer.moveForward = (new Random()).nextFloat();
            }
            isDoingThing = false;
        }
    }
}
