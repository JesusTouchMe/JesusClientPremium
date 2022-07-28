package cum.jesus.jesusclient.utils;

import cum.jesus.jesusclient.JesusClient;

public class MovementUtils {
    public static MilliTimer strafeTimer = new MilliTimer();

    public static float getSpeed() {
        return (float)Math.sqrt(JesusClient.mc.thePlayer.motionX * JesusClient.mc.thePlayer.motionX + JesusClient.mc.thePlayer.motionZ * JesusClient.mc.thePlayer.motionZ);
    }

    public static float getSpeed(double x, double z) {
        return (float)Math.sqrt(x * x + z * z);
    }

    public static void strafe(boolean ignoreDelay) {
        strafe(getSpeed(), ignoreDelay);
    }

    public static boolean isMoving() {
        return (JesusClient.mc.thePlayer != null && (JesusClient.mc.thePlayer.moveForward != 0.0F || JesusClient.mc.thePlayer.moveStrafing != 0.0F));
    }

    public static boolean hasMotion() {
        return (JesusClient.mc.thePlayer.motionX != 0.0D && JesusClient.mc.thePlayer.motionZ != 0.0D && JesusClient.mc.thePlayer.motionY != 0.0D);
    }

    public static void strafe(float speed, boolean ignoreDelay) {
        if (!isMoving() || (!strafeTimer.hasTimePassed(200L) && !ignoreDelay))
            return;
        double yaw = getDirection();
        JesusClient.mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        JesusClient.mc.thePlayer.motionZ = Math.cos(yaw) * speed;
        strafeTimer.updateTime();
    }

    public static void strafe(float speed, float yaw) {
        if (!isMoving() || !strafeTimer.hasTimePassed(150L))
            return;
        JesusClient.mc.thePlayer.motionX = -Math.sin(Math.toRadians(yaw)) * speed;
        JesusClient.mc.thePlayer.motionZ = Math.cos(Math.toRadians(yaw)) * speed;
        strafeTimer.updateTime();
    }

    public static void forward(double length) {
        double yaw = Math.toRadians(JesusClient.mc.thePlayer.rotationYaw);
        JesusClient.mc.thePlayer.setPosition(JesusClient.mc.thePlayer.posX + -Math.sin(yaw) * length, JesusClient.mc.thePlayer.posY, JesusClient.mc.thePlayer.posZ + Math.cos(yaw) * length);
    }

    public static double getDirection() {
        return Math.toRadians(getYaw());
    }

    public static float getYaw() {
        float rotationYaw = JesusClient.mc.thePlayer.rotationYaw;
        if (JesusClient.mc.thePlayer.moveForward < 0.0F)
            rotationYaw += 180.0F;
        float forward = 1.0F;
        if (JesusClient.mc.thePlayer.moveForward < 0.0F) {
            forward = -0.5F;
        } else if (JesusClient.mc.thePlayer.moveForward > 0.0F) {
            forward = 0.5F;
        }
        if (JesusClient.mc.thePlayer.moveStrafing > 0.0F)
            rotationYaw -= 90.0F * forward;
        if (JesusClient.mc.thePlayer.moveStrafing < 0.0F)
            rotationYaw += 90.0F * forward;
        return rotationYaw;
    }
}