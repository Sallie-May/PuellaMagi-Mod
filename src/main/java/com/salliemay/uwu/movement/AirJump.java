package com.salliemay.uwu.movement;

import net.minecraft.client.Minecraft;

public class AirJump {

    private static final Minecraft mc = Minecraft.getInstance();

    public static void performAirJump() {
        if (mc.player != null) {
            mc.player.setOnGround(true);
        }
    }
}
