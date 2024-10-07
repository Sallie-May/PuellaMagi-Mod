package com.salliemay.uwu.movement;

import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;

public class Speed {

    public static void applyMovement() {
        final Minecraft mc = Minecraft.getInstance();

        if (SallieMod.SpeedEnabled) {
            double forward = mc.player.movementInput.moveForward;
            double strafe = mc.player.movementInput.moveStrafe;

            double speed = SallieMod.SpeedMultiplier;

            float yaw = mc.player.rotationYaw;

            double motionX = 0;
            double motionZ = 0;

            if (forward != 0 || strafe != 0) {
                motionX = (-Math.sin(Math.toRadians(yaw)) * forward + Math.cos(Math.toRadians(yaw)) * strafe) * speed;
                motionZ = (Math.cos(Math.toRadians(yaw)) * forward + Math.sin(Math.toRadians(yaw)) * strafe) * speed;
            }

            double motionY = mc.player.getMotion().y;

            mc.player.setMotion(motionX, motionY, motionZ);

            mc.player.fallDistance = 0.0f;
        }
    }

}
