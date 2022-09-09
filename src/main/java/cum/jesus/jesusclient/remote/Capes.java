package cum.jesus.jesusclient.remote;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cum.jesus.jesusclient.JesusClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.codec.digest.DigestUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class Capes {
    public static HashMap<String, String> playerCapes = new HashMap<>();
    public static HashMap<String, ResourceLocation> capes = new HashMap<>();

    private static final File capeDir = new File(JesusClient.cache, "capes");

    public static ResourceLocation getCape(String uuid) {
        String capeName = playerCapes.get(uuid);
        if(capeName == null) return null;

        return capes.get(capeName);
    }

    public static void load() {
        JesusClient.Log.info("Loading capes...");
        capeDir.mkdirs();

        try {
            JsonObject json = (JsonObject) JesusClient.getJson("https://gist.githubusercontent.com/JesusTouchMe/fd99f68f3ac49c654a3c6b8a82382ad8/raw");
            JsonObject jsonCapes = json.get("capes").getAsJsonObject();
            JsonObject jsonOwners = json.get("owners").getAsJsonObject();

            for (Map.Entry<String, JsonElement> e : jsonCapes.entrySet()) {
                String name = e.getKey();
                String url = e.getValue().getAsString();

                JesusClient.Log.info("Loading cape: " + name + " from cache");

               capes.put(name, capeFromFile(name, url));
            }

            for (Map.Entry<String, JsonElement> owner : jsonOwners.entrySet()) {
                playerCapes.put(owner.getKey(), owner.getValue().getAsString());
            }

            JesusClient.Log.debug(getCape(JesusClient.compactUUID));
        } catch (Exception e) {
            JesusClient.Log.error("Could not download capes");
            e.printStackTrace();
        }
    }

    private static ResourceLocation capeFromFile(String capeName, String capeUrl) {
        try {
            File file = new File(capeDir, capeName + ".png");
            if (!file.exists()) downloadCape(capeUrl, file);

            return JesusClient.mc.getTextureManager().getDynamicTextureLocation("jesusclient", new DynamicTexture(ImageIO.read(file)));
        } catch (Exception e) {
            JesusClient.Log.error("Failed to load the funny cape");
            e.printStackTrace();
        }
        return null;
    }

    private static void downloadCape(String imgUrl, File f) {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(imgUrl).openConnection();
            con.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
            con.setRequestMethod("GET");
            BufferedInputStream in = new BufferedInputStream(con.getInputStream());
            f.createNewFile();
            FileOutputStream stream = new FileOutputStream(f);
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                stream.write(dataBuffer, 0, bytesRead);
            }
        } catch (Exception e) {
            JesusClient.Log.error("Failed to download cape(s) \n" + e.getMessage());
            e.printStackTrace();
        }
    }
}
