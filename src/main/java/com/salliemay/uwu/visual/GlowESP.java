package com.salliemay.uwu.visual;

import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;


@Mod.EventBusSubscriber
public class GlowESP {
    private static final Minecraft mc = Minecraft.getInstance();
    @SubscribeEvent
    public void onRender(RenderPlayerEvent e) {
        if (SallieMod.GlowESPEnabled) {
            e.getEntity().setGlowing(true);
        }
    }
}
