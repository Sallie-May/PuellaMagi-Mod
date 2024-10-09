package com.salliemay.uwu.movement;

import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Step {

    private static final Minecraft mc = Minecraft.getInstance();
    private static boolean isEnabled = SallieMod.StepEnabled;
    private static float StepHeight = SallieMod.StepHeight;


    // Event to set the step height
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        ClientPlayerEntity player = Minecraft.getInstance().player;

        if (isEnabled) {
            if (player != null) {
                player.stepHeight = StepHeight;
            }
        } else {
            if (player != null) {
                player.stepHeight = 0.6f;
            }
        }
    }
}
