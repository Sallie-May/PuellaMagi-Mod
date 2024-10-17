package com.salliemay.uwu.movement;

import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Step {

    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        ClientPlayerEntity player = mc.player;

        if (player != null && mc.world != null){
            if (SallieMod.StepEnabled) {
                player.stepHeight = SallieMod.StepHeight;
            } else {
                player.stepHeight = 0.6f;
            }
        }
    }
}
