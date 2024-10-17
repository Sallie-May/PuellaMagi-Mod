package com.salliemay.uwu.visual;

import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.AbstractGui;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = SallieMod.MOD_ID, value = Dist.CLIENT)
public class SessionStats {

    private static long startTime = System.currentTimeMillis();
    private static long endTime = -1;

    private static final List<String> linesLeft = Arrays.asList("Play time");
    private static final Minecraft mc = Minecraft.getInstance();

    public SessionStats() {
    }

    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.END && SallieMod.SessionStatsEnabled) {
            renderSessionStats();
        }
    }

    public static void renderSessionStats() {
        if (mc.world == null) return;

        MatrixStack matrixStack = new MatrixStack();

        float x = 5;
        float y = 100;
        float padding = 5;
        float width = 140;

        float totalHeight = linesLeft.size() * (mc.fontRenderer.FONT_HEIGHT + 6) + padding * 2;

        drawRect(matrixStack, x, y, width, totalHeight, new Color(30, 30, 30, 150).getRGB());

        for (int i = 0; i < linesLeft.size(); i++) {
            int offset = i * (mc.fontRenderer.FONT_HEIGHT + 6);
            mc.fontRenderer.drawString(matrixStack, linesLeft.get(i), x + padding, y + padding + offset, Color.WHITE.getRGB());
        }

        int[] playTime = getPlayTime();
        String playtimeString = String.format("%dh %dm %ds", playTime[0], playTime[1], playTime[2]);
        mc.fontRenderer.drawString(matrixStack, playtimeString, x + width - mc.fontRenderer.getStringWidth(playtimeString) - padding, y + padding, Color.WHITE.getRGB());
    }

    private static void drawRect(MatrixStack matrixStack, float x, float y, float width, float height, int color) {
        int alpha = (color >> 24 & 255);
        int red = (color >> 16 & 255);
        int green = (color >> 8 & 255);
        int blue = (color & 255);

        AbstractGui.fill(matrixStack, (int)x, (int)y, (int)(x + width), (int)(y + height), (alpha << 24 | red << 16 | green << 8 | blue));
    }

    public static int[] getPlayTime() {
        long diff = getTimeDiff();
        long diffSeconds = 0, diffMinutes = 0, diffHours = 0;
        if (diff > 0) {
            diffSeconds = diff / 1000 % 60;
            diffMinutes = diff / (60 * 1000) % 60;
            diffHours = diff / (60 * 60 * 1000);
        }
        return new int[]{(int) diffHours, (int) diffMinutes, (int) diffSeconds};
    }

    public static long getTimeDiff() {
        return (endTime == -1 ? System.currentTimeMillis() : endTime) - startTime;
    }

    public static void reset() {
        startTime = System.currentTimeMillis();
        endTime = -1;
    }

    @SubscribeEvent
    public static void onPlayerDisconnect(PlayerEvent.PlayerLoggedOutEvent event) {
        reset();
    }
}
