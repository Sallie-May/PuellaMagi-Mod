package com.salliemay.uwu.visual;

import com.google.common.collect.EvictingQueue;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SUpdateTimePacket;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TPSOverlay {
    private static final Logger LOGGER = LogManager.getLogger("ServerTPS");

    private static final EvictingQueue<Float> clientTicks = EvictingQueue.create(20);
    private static final EvictingQueue<Float> serverTPS = EvictingQueue.create(3);

    private static long systemTime1 = 0;
    private static long systemTime2 = 0;
    private static long serverTime = 0;

    public TPSOverlay() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientConnectedToServer(ClientPlayerNetworkEvent.LoggedInEvent event) {
        LOGGER.debug("Client connected, starting TPS recording...");
        clientTicks.clear();
        serverTPS.clear();
        resetTimeVariables();
    }

    private void resetTimeVariables() {
        systemTime1 = 0;
        systemTime2 = 0;
        serverTime = 0;
    }

    public static void onServerTick(SUpdateTimePacket packet) {
        if (systemTime1 == 0) {
            systemTime1 = System.currentTimeMillis();
            serverTime = packet.getTotalWorldTime();
        } else {
            long newSystemTime = System.currentTimeMillis();
            long newServerTime = packet.getTotalWorldTime();

            float tps = (((float) (newServerTime - serverTime)) / (((float) (newSystemTime - systemTime1)) / 50.0f)) * 20.0f;
            serverTPS.add(tps);

            systemTime1 = newSystemTime;
            serverTime = newServerTime;
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            systemTime2 = System.currentTimeMillis();
        } else {
            long newSystemTime = System.currentTimeMillis();
            float newClientTick = ((float) newSystemTime - systemTime2);
            clientTicks.add(newClientTick);

            renderTPSOverlay();
        }
    }

    public static float calculateClientTick() {
        return calculateAverage(clientTicks);
    }

    public static float calculateServerTPS() {
        return calculateAverage(serverTPS);
    }

    private static float calculateAverage(EvictingQueue<Float> queue) {
        float sum = 0.0f;
        for (Float f : queue) {
            sum += f;
        }
        return queue.size() > 0 ? sum / queue.size() : 0.0f;
    }

    private void renderTPSOverlay() {
        if (!Minecraft.getInstance().isGamePaused()) {
            LOGGER.debug("Rendering TPS overlay...");
            MatrixStack matrixStack = new MatrixStack();
            String tpsText = String.format("TPS: %.2f", calculateServerTPS());

            int xPos = 10;
            int yPos = 30;
            int width = Minecraft.getInstance().getMainWindow().getScaledWidth();
            int height = Minecraft.getInstance().getMainWindow().getScaledHeight();
            int color = 0x00FF00;

            xPos = width - 10 - Minecraft.getInstance().fontRenderer.getStringWidth(tpsText);
            yPos = height - 30;

            Minecraft.getInstance().fontRenderer.drawStringWithShadow(matrixStack, tpsText, xPos, yPos, color);
        }
    }

}
