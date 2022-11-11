package cum.jesus.jesusclient.config;

import cum.jesus.jesusclient.JesusClient;
import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.JVMAnnotationPropertyCollector;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyCollector;
import gg.essential.vigilance.data.PropertyType;
import java.io.File;

public class Config extends Vigilant {
    public static Config INSTANCE = new Config();

    // "Combat", "Skyblock Qol", "Player", "Macros", "Movement", "Funny", "Other" : categories

    public final String WIP = JesusClient.COLOR + "b[WIP] " + JesusClient.COLOR + "f";

    public static final String risky = JesusClient.COLOR + "cHella risky. Don't cry if you get banned. " + JesusClient.COLOR + "f";

    @Property(type = PropertyType.SWITCH, name = "Reach", description = "Increases the reach that you can hit things from", category = "Combat", subcategory = "Reach")
    public boolean reach = false;

    @Property(type = PropertyType.DECIMAL_SLIDER, name = "Reach Amount", description = "Changes the amount of reach you have. Vanilla reach is 3", category = "Combat", subcategory = "Reach", minF = 2f, maxF = 4.5f, decimalPlaces = 1)
    public float reachAmount = 3;

    @Property(type = PropertyType.SWITCH, name = "Anti KB", description = "Disables knockback", category = "Combat", subcategory = "Player")
    public boolean antiKB = false;

    @Property(type = PropertyType.SWITCH, name = "Enable", description = risky + "Enables killaura. Required to see settings", category = "Combat", subcategory = "KillAura")
    public boolean killAura = false;

    @Property(type = PropertyType.CHECKBOX, name = "Players", category = "Combat", subcategory = "KillAura")
    public boolean kaPlayers = false;

    @Property(type = PropertyType.CHECKBOX, name = "Mobs", category = "Combat", subcategory = "KillAura")
    public boolean kaMobs = true;

    @Property(type = PropertyType.CHECKBOX, name = "Hit Through Walls", category = "Combat", subcategory = "KillAura")
    public boolean kaWalls = true;

    @Property(type = PropertyType.CHECKBOX, name = "Teams", category = "Combat", subcategory = "KillAura")
    public boolean kaTeam= true;

    @Property(type = PropertyType.CHECKBOX, name = "Disable on new world", category = "Combat", subcategory = "KillAura")
    public boolean kaDisable = true;

    @Property(type = PropertyType.CHECKBOX, name = "Don't hit in containers", description = "Doesn't attack while in an inventory. Recommended to leave on", category = "Combat", subcategory = "KillAura")
    public boolean kaGui = true;

    @Property(type = PropertyType.CHECKBOX, name = "No NPC", category = "Combat", subcategory = "KillAura")
    public boolean kaAntiNpc = true;

    @Property(type = PropertyType.CHECKBOX, name = "Block", description = "Blocks when attacking with KillAura", category = "Combat", subcategory = "KillAura")
    public boolean kaBlock = false;

    @Property(type = PropertyType.DECIMAL_SLIDER, name = "Reach", category = "Combat", subcategory = "KillAura", minF = 2.0f, maxF = 6.0f, decimalPlaces = 1)
    public float kaReach = 3F;

    @Property(type = PropertyType.DECIMAL_SLIDER, name = "Rotation Range", description = "Set this as the same as your reach if you have no clue what it is", category = "Combat", subcategory = "KillAura", minF = 2.0f, maxF = 12.0f, decimalPlaces = 1)
    public float kaRotationRange = 3F;

    @Property(type = PropertyType.DECIMAL_SLIDER, name = "FOV", category = "Combat", subcategory = "KillAura", minF = 30.0f, maxF = 360.0f, decimalPlaces = 1)
    public float kaFov = 360F;

    @Property(type = PropertyType.SELECTOR, name = "Sorting", category = "Combat", subcategory = "KillAura", options = { "Distance", "Health", "Hurt", "Hp reverse" })
    public int kaMode = 0;

    @Property(type = PropertyType.SWITCH, name = "Cum", description = "Automatically throws every single snowball in your hotbar or inventory. \nRequires you to press the keybind to actually throw them", category = "Combat", subcategory = "Snowball")
    public boolean cumEnabled = false;

    @Property(type = PropertyType.SWITCH, name = "PickupStash", category = "Combat", subcategory = "Snowball")
    public boolean cumStash = true;

    @Property(type = PropertyType.CHECKBOX, name = "Inv Mode", description = risky + "Throws snowballs from your inventory", category = "Combat", subcategory = "Snowball")
    public boolean cumInvMode = false;

    @Property(type = PropertyType.SLIDER, name = "Main Slot", description = "Leave on 0 if you wanna use old slot", category = "Combat", subcategory = "Snowball", min = 0, max = 8)
    public int cumMainSlot = 0;

    @Property(type = PropertyType.SWITCH, name = "Auto Rogue", description = "Automatically clicks a rogue sword in your hotbar every 30 seconds", category = "Skyblock", subcategory = "Auto Rogue")
    public boolean autoRogue = false;

    @Property(type = PropertyType.CHECKBOX, name = "More Legit", description = "Randomizes the time when it clicks so it doesn't seem too sus", category = "Skyblock", subcategory = "Auto Rogue")
    public boolean legitRogue = false;

    @Property(type = PropertyType.SWITCH, name = "Boner Thrower", description = "Automatically throws every bonemerang in your hotbar (or inventory). \nThe keybind needs to be pressed to actually shoot", category = "Skyblock", subcategory = "Boner")
    public boolean boner = false;

    @Property(type = PropertyType.SLIDER, name = "Throw delay", description = "Throw delay in ms", category = "Skyblock", subcategory = "Boner", min = 0, max = 1000, increment = 1)
    public int bonerDelay = 60;

    @Property(type = PropertyType.SLIDER, name = "Main Slot", description = "Leave on 0 if you wanna use old slot", category = "Skyblock", subcategory = "Boner", min = 0, max = 8)
    public int bonerMainSlot = 0;

    @Property(type = PropertyType.CHECKBOX, name = "Boner Inv Mode", description = risky + "Throws boners from your inventory", category = "Skyblock", subcategory = "Boner", hidden = true)
    public boolean bonerInvMode = false;

    @Property(type = PropertyType.SWITCH, name = "Jesus", description = "I had to add this. Makes you walk on water. A bit broken rn lol", category = "Movement")
    public boolean jesusHack = false;

    @Property(type = PropertyType.SWITCH, name = "Sprint", description = "Sprints.", category = "Movement", subcategory = "Sprint")
    public boolean toggleSprint = false;

    @Property(type = PropertyType.SWITCH, name = "Omni Sprint", description = "Sprints no matter which direction you're looking", category = "Movement", subcategory = "Sprint")
    public boolean omniSprint = false;

    @Property(type = PropertyType.SWITCH, name = "Keep Sprint", category = "Movement", subcategory = "Sprint")
    public boolean keepSprint = false;

    @Property(type = PropertyType.SWITCH, name = "NoFall", description = "Removes fall damage", category = "Movement")
    public boolean noFall = false;

    @Property(type = PropertyType.SWITCH, name = "Parkour", description = "Automatically jumps as soon as you reach the edge of a block. Perfect for shitters that wanna cheat in parkour", category = "Movement")
    public boolean parkour = false;

    @Property(type = PropertyType.SWITCH, name = "Toggle Fly", category = "Movement", subcategory = "Flight")
    public boolean flight = false;

    @Property(type = PropertyType.DECIMAL_SLIDER, name = "Fly Speed", category = "Movement", minF = 0.1f, maxF = 10f, subcategory = "Flight")
    public float flySpeed = 1.0f;

    @Property(type = PropertyType.SWITCH, name = "Toggle BHop", description = "Jumps and goes fast", category = "Movement", subcategory = "BHop")
    public boolean bHop = false;

    @Property(type = PropertyType.SELECTOR, name = "BHop Mode", category = "Movement", options = { "1 Block Jump", "Vanilla Jump", "Small Ass Jump" }, subcategory = "BHop")
    public int bHopMode = 0;

    @Property(type = PropertyType.DECIMAL_SLIDER, name = "BHop Speed", category = "Movement", subcategory = "BHop", minF = 0.1F, maxF = 15.0F)
    public float bHopSpeed = 2.0F;

    @Property(type = PropertyType.SWITCH, name = "InvMove", description = risky + "Will most likely ban", category = "Player")
    public boolean invMove = false;

    @Property(type = PropertyType.SWITCH, name = "Timer", description = "Makes the game go faster", category = "Player", subcategory = "Timer")
    public boolean timer = false;

    @Property(type = PropertyType.DECIMAL_SLIDER, name = "Timer Multiplier", category = "Player", minF = 0.1f, maxF = 5.0f, subcategory = "Timer")
    public float timerMult = 1.0f;

    @Property(type = PropertyType.SWITCH, name = "Retardation", description = "Just adds a lot of random funny features", category = "Funny", searchTags = {"funny", "retard"})
    public boolean retard = true;

    @Property(type = PropertyType.SWITCH, name = "Anti Throw", description = "Will prevent you from throwing puzzles in dungeons", category = "Funny", searchTags = {"throw", "puzzle", "solver"})
    public boolean antiThrow = false;

    @Property(type = PropertyType.SWITCH, name = "Discord Rich Presence", description = "Makes you 100x cooler", category = "Other")
    public boolean discordRPC = true;

    @Property(type = PropertyType.TEXT, name = "Custom Prefix", description = "Changes the prefix for the custom commands\nI don't think it works with /", category = "Other")
    public String customPrefix = "-";

    @Property(type = PropertyType.SWITCH, name = "Boob", description = "Will contain nsfw imagery. Do NOT enable this if you share your pc with a 5 year old child", category = "Other")
    public boolean boob = false;

    @Property(type = PropertyType.SWITCH, name = "ArrayList", description = "Toggle arraylist", category = "Other", hidden = true)
    public boolean arrayList = false;

    @Property(type = PropertyType.SWITCH, name = "Nick Hider", description = "Changes your name to something else (client side only)", category = "Other", subcategory = "Nick Hider")
    public boolean nickHider = true;

    @Property(type = PropertyType.TEXT, name = "Name", category = "Other", subcategory = "Nick Hider")
    public String nickHiderName = "Jesus";

    // hidden shit

    @Property(type = PropertyType.NUMBER, name = "a volume", description = "amount of db to increase/decrease by", category = "Dev", max = 5, min = -50, hidden = true)
    public int aVolume = -40;

    @Property(type = PropertyType.NUMBER, name = "balls time", category = "Dev", min = 0, max = 20000, hidden = true)
    public int balls = 6150;

    @Property(type = PropertyType.NUMBER, name = "dont worry abt this", category = "Dev", min = 1, max = 10000, hidden = true)
    public int retardmsg = 5000;

    public Config() {
        super(new File("./jesus/config.toml"), "JesusClient", (PropertyCollector)new JVMAnnotationPropertyCollector(), new ConfigSorting());
        initialize();
    }
}
