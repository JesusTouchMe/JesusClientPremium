package cum.jesus.jesusclient.qol.modules.funny;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.qol.modules.Category;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.qol.modules.other.HiddenShit;
import cum.jesus.jesusclient.utils.Utils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.io.*;
import java.util.Base64;
import java.util.Random;

import static cum.jesus.jesusclient.JesusClient.COLOR;

public class Retardation extends Module {
    private File a = new File(JesusClient.mc.mcDataDir + "/jesus/sounds", "a.wav");
    private File boom = new File(JesusClient.mc.mcDataDir + "/jesus/sounds", "vineboom.wav");

    public Retardation() {
        super("Retardation", "", Category.FUNNY);
    }

    public static String sender = "";

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.START) return;
        if (!getState()) return;
        if (Math.random() < 0.00001) {
            Utils.playSound(a, HiddenShit.aVolume.getObject());
        }
    }

    @SubscribeEvent
    public void key(InputEvent.KeyInputEvent e) {
        if (!getState()) return;
        int rnd = (new Random()).nextInt(HiddenShit.retardMsg.getObject());
        if (rnd == 0 && JesusClient.init) {
            JesusClient.sendMessage(JesusClient.people[new Random().nextInt(JesusClient.people.length)].replace('&',COLOR) + COLOR + "7" + ": " + Retardation.i(JesusClient.obfMessages[new Random().nextInt(JesusClient.obfMessages.length)]));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void chat(ClientChatReceivedEvent event) {
        if (!getState()) return;
        String message = event.message.getUnformattedText();
        String formatted = event.message.getFormattedText();
        if (event.type == 0) {
            if (message.startsWith("From")) {
                sender = formatted.substring(formatted.indexOf("From") + 5, formatted.indexOf(":"));
                if (message.toLowerCase().contains("are you using jesus client")) {
                    JesusClient.sendMessageAsPlayer("/message " + removeFormatting(sender.split(" ")[1]) + " yes ofc! jesus client on top!");
                } else if (message.toLowerCase().contains("are u using jesus client")) {
                    JesusClient.sendMessageAsPlayer("/message " + removeFormatting(sender.split(" ")[1]) + " yes ofc! jesus client on top!");
                }
            }
            else if (message.startsWith("§9Party")) {
                if (message.toLowerCase().contains("are you using jesus client")) {
                    JesusClient.sendMessageAsPlayer("/pc yes! jesus client on top!");
                } else if (message.toLowerCase().contains("are u using jesus client")) {
                    JesusClient.sendMessageAsPlayer("/pc yes! jesus client on top!");
                }
            }
        }
    }

    public static String i(String s) {
        String str = "something went wrong lol";
        try {
            str = new String(Base64.getDecoder().decode(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String removeFormatting(String input) {
        return input.replaceAll("§[0-9a-fk-or]", "");
    }
}
