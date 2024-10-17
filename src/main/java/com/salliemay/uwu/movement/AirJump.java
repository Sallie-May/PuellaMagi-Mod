package com.salliemay.uwu.movement;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.salliemay.uwu.SallieMod.AirJumpEnabled;


@Mod.EventBusSubscriber
public class AirJump {

    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void onPlayerTick(PlayerEvent.PlayerRespawnEvent event) {
        if (mc.player != null && AirJumpEnabled) {
            mc.player.setOnGround(true);
        }
    }
}
