package com.salliemay.uwu.misc;

import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SallieMod.MOD_ID)
public final class Respawn {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final boolean isEnabled = SallieMod.RespawnEnabled;

    @SubscribeEvent
    public void onPlayerTick(PlayerEvent.PlayerRespawnEvent event) {
        if (isEnabled && mc.currentScreen instanceof DeathScreen) {
            if (mc.player != null) {
                mc.player.respawnPlayer();
            }
        }
    }
}
