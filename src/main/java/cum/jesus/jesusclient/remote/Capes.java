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
import java.net.ProtocolException;
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
            JsonObject json = (JsonObject) JesusClient.getJson("https://gist.githubusercontent.com/JesusTouchMe/65f152460cfbf452f7049bc489a2fbbb/raw/75dae1fb72db3d308d7c91607e928a4ffde1e9e5/capeData.json");
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

            //JesusClient.Log.debug(getCape(JesusClient.compactUUID));
        } catch (Exception e) {
            JesusClient.Log.error("Could not download capes");
            e.printStackTrace();
        }
    }

    private static ResourceLocation capeFromFile(String capeName, String capeUrl) {
        try {
            File file = new File(capeDir, capeName + ".png");
            if (!file.exists()) JesusClient.download(capeUrl, file.getAbsolutePath());

            return JesusClient.mc.getTextureManager().getDynamicTextureLocation("jesusclient", new DynamicTexture(ImageIO.read(file)));
        } catch (IOException e) {
            JesusClient.Log.error("Failed to load the funny cape");
            e.printStackTrace();
        }
        return null;
    }
}
