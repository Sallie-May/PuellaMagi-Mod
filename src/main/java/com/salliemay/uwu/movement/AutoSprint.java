package com.salliemay.uwu.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class AutoSprint {
    private static final Minecraft mc = Minecraft.getInstance();
    public static void tick() {
        PlayerEntity player = mc.player;
        if (player != null && player.isAlive()) {
            if (player.getFoodStats().getFoodLevel() > 6) {
                player.setSprinting(true);
            } else {
                player.setSprinting(false);
            }

        }
    }
}
