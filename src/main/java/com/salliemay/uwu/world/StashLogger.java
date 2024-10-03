package com.salliemay.uwu.world;

import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.chunk.IChunk;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.block.BlockState;

@Mod.EventBusSubscriber()
public class StashLogger {

    private final int shulkersThreshold = 1;
    private final int chunkRadius = 8;

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        if (!SallieMod.StashEnabled) {
            return;
        }

        int centerChunkX = event.getChunk().getPos().x;
        int centerChunkZ = event.getChunk().getPos().z;

        int totalShulkerCount = 0;

        for (int chunkXOffset = -chunkRadius; chunkXOffset <= chunkRadius; chunkXOffset++) {
            for (int chunkZOffset = -chunkRadius; chunkZOffset <= chunkRadius; chunkZOffset++) {
                int chunkX = centerChunkX + chunkXOffset;
                int chunkZ = centerChunkZ + chunkZOffset;

                IChunk chunk = event.getWorld().getChunk(chunkX, chunkZ);
                totalShulkerCount += countShulkersInChunk(chunk);
            }
        }

        if (totalShulkerCount >= shulkersThreshold) {
            sendNotification(totalShulkerCount, centerChunkX, centerChunkZ);
        }
    }

    private int countShulkersInChunk(IChunk chunk) {
        int shulkerCount = 0;

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 256; y++) {
                for (int z = 0; z < 16; z++) {
                    BlockPos pos = new BlockPos(chunk.getPos().x * 16 + x, y, chunk.getPos().z * 16 + z);
                    if (isShulkerBox(chunk.getBlockState(pos))) {
                        shulkerCount++;
                    }
                }
            }
        }
        return shulkerCount;
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
    }
}
