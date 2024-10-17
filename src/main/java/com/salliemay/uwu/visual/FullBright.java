package com.salliemay.uwu.visual;

import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class FullBright {


    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && SallieMod.FullBrightEnabled) {
            mc.gameSettings.gamma = 100.0f;
        }
        else {
            mc.gameSettings.gamma = 0.5f;
        }


        }
}
