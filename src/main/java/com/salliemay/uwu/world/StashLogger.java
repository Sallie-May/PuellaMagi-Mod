package com.salliemay.uwu.world;

import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.chunk.IChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.BlockState;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class StashLogger {

    private final int chunkRadius = 8;
    private long lastNotificationTime = 0;
    private final long notificationCooldown = 600;
    private Set<BlockPos> shulkerBoxPositions = new HashSet<>();

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        if (!SallieMod.StashEnabled) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();

        int centerChunkX = event.getChunk().getPos().x;
        int centerChunkZ = event.getChunk().getPos().z;

        int reducedChunkRadius = 4;
        int totalShulkerCount = 0;

        for (int chunkXOffset = -reducedChunkRadius; chunkXOffset <= reducedChunkRadius; chunkXOffset++) {
            for (int chunkZOffset = -reducedChunkRadius; chunkZOffset <= reducedChunkRadius; chunkZOffset++) {
                int chunkX = centerChunkX + chunkXOffset;
                int chunkZ = centerChunkZ + chunkZOffset;

                IChunk chunk = event.getWorld().getChunk(chunkX, chunkZ);

                totalShulkerCount += countShulkersInChunk(chunk);
            }
        }

        int shulkersThreshold = 1;
        if (totalShulkerCount >= shulkersThreshold && canSendNotification()) {
            sendNotification(totalShulkerCount, centerChunkX, centerChunkZ);
        }
    }

    @SubscribeEvent
    public void onChunkUnload(ChunkEvent.Unload event) {
        IChunk chunk = event.getChunk();
        removeShulkersInChunk(chunk);
    }

    private int countShulkersInChunk(IChunk chunk) {
        int shulkerCount = 0;

        for (BlockPos pos : chunk.getTileEntitiesPos()) {
            BlockState blockState = chunk.getBlockState(pos);
            if (isShulkerBox(blockState)) {
                shulkerBoxPositions.add(pos);
                shulkerCount++;
            }
        }

        return shulkerCount;
    }

    private void removeShulkersInChunk(IChunk chunk) {
        int chunkX = chunk.getPos().x * 16;
        int chunkZ = chunk.getPos().z * 16;
        Iterator<BlockPos> iterator = shulkerBoxPositions.iterator();
        while (iterator.hasNext()) {
            BlockPos pos = iterator.next();
            if (pos.getX() >= chunkX && pos.getX() < chunkX + 16 && pos.getZ() >= chunkZ && pos.getZ() < chunkZ + 16) {
                iterator.remove();
            }
        }
    }

    private boolean isShulkerBox(BlockState blockState) {
        return blockState.getBlock() instanceof ShulkerBoxBlock;
    }

    private void sendNotification(int shulkerCount, int centerChunkX, int centerChunkZ) {
        String message = String.format("%s shulker boxes located within 8 chunks of center at X: %s, Z: %s", shulkerCount, centerChunkX * 16, centerChunkZ * 16);
        Minecraft.getInstance().player.sendMessage(
                new StringTextComponent("Found at: " + message),
                Minecraft.getInstance().player.getUniqueID()
        );
        lastNotificationTime = System.currentTimeMillis();
    }

    private boolean canSendNotification() {
        return (System.currentTimeMillis() - lastNotificationTime) >= notificationCooldown;
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        if (!SallieMod.StashEnabled) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        FontRenderer fontRenderer = mc.fontRenderer;

        int xOffset = 10;
        int yOffset = 90;

        removeNonExistentShulkers();

        fontRenderer.drawStringWithShadow(event.getMatrixStack(), "Shulker Boxes:", xOffset, yOffset, 0xFFFFFF);
        yOffset += 10;

        for (BlockPos pos : shulkerBoxPositions) {
            String posString = String.format("X: %d, Y: %d, Z: %d", pos.getX(), pos.getY(), pos.getZ());
            fontRenderer.drawStringWithShadow(event.getMatrixStack(), posString, xOffset, yOffset, 0xFFFFFF);
            yOffset += 10;
        }
    }

    private void removeNonExistentShulkers() {
        Iterator<BlockPos> iterator = shulkerBoxPositions.iterator();
        while (iterator.hasNext()) {
            BlockPos pos = iterator.next();
            BlockState blockState = Minecraft.getInstance().world.getBlockState(pos);
            if (!isShulkerBox(blockState)) {
                iterator.remove();
            }
        }
    }
}
