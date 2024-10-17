package com.salliemay.uwu.misc;

import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public final class Respawn {
    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void onPlayerTick(PlayerEvent.PlayerRespawnEvent event) {
        if (SallieMod.RespawnEnabled && mc.currentScreen instanceof DeathScreen) {
            if (mc.player != null) {
                mc.player.respawnPlayer();
            }
        }
    }
}
