package cum.jesus.jesusclient.qol.modules.combat;

import cum.jesus.jesusclient.JesusClient;
import cum.jesus.jesusclient.events.MotionUpdateEvent;
import cum.jesus.jesusclient.mixins.PlayerSPAccessor;
import cum.jesus.jesusclient.qol.modules.Module;
import cum.jesus.jesusclient.utils.RotationUtils;
import cum.jesus.jesusclient.utils.SkyblockUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import java.util.*;
import java.util.stream.Collectors;

public class KillAura extends Module {
    public static EntityLivingBase target;
    private boolean players = JesusClient.config.kaPlayers;
    private boolean mobs = JesusClient.config.kaMobs;
    private boolean walls = JesusClient.config.kaWalls;
    private boolean teams = JesusClient.config.kaTeam;
    private boolean toggleOnLoad = JesusClient.config.kaDisable;
    private boolean toggleInGui = JesusClient.config.kaGui;
    private boolean antiNPC = JesusClient.config.kaAntiNpc;
    private boolean block = JesusClient.config.kaBlock;
    private double range = JesusClient.config.kaReach;
    private double rotationRange = JesusClient.config.kaRotationRange;
    private double fov = JesusClient.config.kaFov;
    private int mode = JesusClient.config.kaMode;
    private boolean wasDown;
    private boolean attack = false;

    public KillAura() {
        super("KillAura", JesusClient.config.killAura);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (JesusClient.mc.thePlayer == null || JesusClient.mc.theWorld == null)
            return;
        if (Mouse.isButtonDown(2) && JesusClient.mc.currentScreen == null) {
            if (JesusClient.mc.pointedEntity != null && !wasDown && !(JesusClient.mc.pointedEntity instanceof net.minecraft.entity.item.EntityArmorStand) && JesusClient.mc.pointedEntity instanceof EntityLivingBase) {
                wasDown = true;
            }
        } else {
            wasDown = false;
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onMovePre(MotionUpdateEvent.Pre event) {
        if (!JesusClient.config.killAura || attack) {
            target = null;
            return;
        }
        target = getTarget();
        if (target != null) {
            float[] angles = RotationUtils.getServerAngles((Entity)target);
            event.yaw = ((PlayerSPAccessor)JesusClient.mc.thePlayer).getLastReportedYaw() - MathHelper.wrapAngleTo180_float((float)Math.max(-180.0D, Math.min((((PlayerSPAccessor)JesusClient.mc.thePlayer).getLastReportedYaw() - angles[0]), 180.0D)));
            event.pitch = ((PlayerSPAccessor)JesusClient.mc.thePlayer).getLastReportedPitch() - MathHelper.wrapAngleTo180_float((float)Math.max(-180.0D, Math.min((((PlayerSPAccessor)JesusClient.mc.thePlayer).getLastReportedPitch() - angles[1]), 180.0D)));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onMovePost(MotionUpdateEvent.Post event) {
        if (target != null && JesusClient.mc.thePlayer.ticksExisted % 2 == 0) {
            SkyblockUtils.updateItemNoEvent();
            if (JesusClient.mc.thePlayer.getDistanceToEntity((Entity)target) < range) {
                if (JesusClient.mc.thePlayer.isUsingItem())
                    JesusClient.mc.playerController.onStoppedUsingItem((EntityPlayer)JesusClient.mc.thePlayer);
                JesusClient.mc.thePlayer.swingItem();
                float[] angles = RotationUtils.getServerAngles((Entity)target);
                if (Math.abs(((PlayerSPAccessor)JesusClient.mc.thePlayer).getLastReportedPitch() - angles[1]) < 25.0F && Math.abs(((PlayerSPAccessor)JesusClient.mc.thePlayer).getLastReportedYaw() - angles[0]) < 15.0F)
                    JesusClient.mc.playerController.attackEntity((EntityPlayer)JesusClient.mc.thePlayer, (Entity)target);
                if (JesusClient.mc.thePlayer.isUsingItem() && JesusClient.mc.thePlayer.getHeldItem() != null && JesusClient.mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword && block)
                    JesusClient.mc.playerController.sendUseItem((EntityPlayer)JesusClient.mc.thePlayer, (World)JesusClient.mc.theWorld, JesusClient.mc.thePlayer.getHeldItem());
            }
        }
    }

    private EntityLivingBase getTarget() {
        if ((JesusClient.mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer && toggleInGui) || JesusClient.mc.theWorld == null)
            return null;
        List<Entity> validTargets = (List<Entity>)JesusClient.mc.theWorld.getLoadedEntityList().stream().filter(entity -> entity instanceof EntityLivingBase).filter(entity -> isValid((EntityLivingBase)entity)).sorted(Comparator.comparingDouble(e -> e.getDistanceToEntity((Entity)JesusClient.mc.thePlayer))).collect(Collectors.toList());
        switch (mode) {
            case 1: // Health
                validTargets.sort(Comparator.comparingDouble(e -> ((EntityLivingBase)e).getHealth()));
                break;
            case 2: // Hurt
                validTargets.sort(Comparator.comparing(e -> Integer.valueOf(((EntityLivingBase)e).hurtTime)));
                break;
            case 3: // HP Reverse
                validTargets.sort(Comparator.<Entity>comparingDouble(e -> ((EntityLivingBase)e).getHealth()).reversed());
        }
        Iterator<Entity> iterator = validTargets.iterator();
        if (iterator.hasNext()) {
            Entity entity = iterator.next();
            return (EntityLivingBase)entity;
        }
        return null;
    }

    private boolean isValid(EntityLivingBase entity) {
        if (entity == JesusClient.mc.thePlayer || ((entity instanceof EntityPlayer || entity instanceof net.minecraft.entity.boss.EntityWither || entity instanceof net.minecraft.entity.passive.EntityBat) && entity.isInvisible()) || entity instanceof net.minecraft.entity.item.EntityArmorStand || (!JesusClient.mc.thePlayer.canEntityBeSeen((Entity)entity) && !walls) || entity.getHealth() <= 0.0F || entity.getDistanceToEntity((Entity)JesusClient.mc.thePlayer) > ((target != null && target != entity) ? range : Math.max(rotationRange, range)) || !RotationUtils.isWithinFOV(entity,fov + 5.0D) || !RotationUtils.isWithinPitch(entity, fov + 5.0D))
            return false;
        if ((entity instanceof net.minecraft.entity.monster.EntityMob || entity instanceof net.minecraft.entity.passive.EntityAmbientCreature || entity instanceof net.minecraft.entity.passive.EntityWaterMob || entity instanceof net.minecraft.entity.passive.EntityAnimal || entity instanceof net.minecraft.entity.monster.EntitySlime) && !mobs)
            return false;
        if (entity instanceof EntityPlayer && ((SkyblockUtils.isTeam(entity, (EntityLivingBase)JesusClient.mc.thePlayer) && teams) || (SkyblockUtils.isNPC((Entity)entity) && antiNPC) || !players))
            return false;
        return !(entity instanceof net.minecraft.entity.passive.EntityVillager);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (JesusClient.config.killAura && toggleOnLoad)
            JesusClient.config.killAura = !JesusClient.config.killAura;
    }
}
