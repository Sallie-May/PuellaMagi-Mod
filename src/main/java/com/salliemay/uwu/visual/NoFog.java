package com.salliemay.uwu.visual;

import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NoFog {

    private static final Minecraft mc = Minecraft.getInstance();

    public static void disableFog() {
        if (SallieMod.NoFogEnabled) {

        }
    }

    @SubscribeEvent
    public static void onFogDensity(EntityViewRenderEvent.FogDensity event) {
        if (SallieMod.NoFogEnabled) {
            event.setDensity(0.0f);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onFogColor(EntityViewRenderEvent.FogColors event) {
        if (SallieMod.NoFogEnabled) {
            event.setCanceled(true);
        }
    }
}
