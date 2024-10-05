package com.salliemay.uwu.movement;
import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;

public class Fly {

    public static void applyMovement() {
        final Minecraft mc = Minecraft.getInstance();
        mc.player.noClip = true;
        mc.player.fallDistance = 0f;
        mc.player.abilities.isFlying = false;

        mc.player.setMotion(0, 0, 0);

        mc.player.jumpMovementFactor = SallieMod.FlySpeed;

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.setMotion(mc.player.getMotion().add(0, SallieMod.FlySpeed, 0));
        }

        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.setMotion(mc.player.getMotion().add(0, -SallieMod.FlySpeed, 0));
        }
    }
}
