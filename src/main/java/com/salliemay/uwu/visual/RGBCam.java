package com.salliemay.uwu.visual;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.Color;
import java.util.Random;

@Mod.EventBusSubscriber
public class RGBCam {

    private static final Minecraft mc = Minecraft.getInstance();
    private static final Random random = new Random();


    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && SallieMod.RGBCamEnabled) {
            renderOverlay(event.getMatrixStack());
        }
    }

    private static void renderOverlay(MatrixStack matrixStack) {
        if (mc.player == null || mc.world == null) return;

        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        int a = 50;

        Color primColor = new Color(r, g, b, a);

        int screenWidth = mc.getMainWindow().getScaledWidth();
        int screenHeight = mc.getMainWindow().getScaledHeight();

        Screen.fill(matrixStack, 0, 0, screenWidth, screenHeight, primColor.getRGB());
    }
}
