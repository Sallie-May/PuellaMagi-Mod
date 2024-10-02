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


    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        if (!SallieMod.StashEnabled) {
           return;
        }
        IChunk chunk = event.getChunk();
        int shulkerCount = 0;

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 256; y++) {
                for (int z = 0; z < 16; z++) {
                    BlockPos pos = new BlockPos(event.getChunk().getPos().x * 16 + x, y, event.getChunk().getPos().z * 16 + z);
                    if (isShulkerBox(chunk.getBlockState(pos))) {
                        shulkerCount++;
                    }
                }
            }
        }

        if (shulkerCount >= shulkersThreshold) {
            sendNotification(shulkerCount, chunk.getPos().x, chunk.getPos().z);
        }
    }

    private boolean isShulkerBox(BlockState blockState) {
        return blockState.getBlock() instanceof ShulkerBoxBlock;
    }

    private void sendNotification(int shulkerCount, int chunkX, int chunkZ) {
        String message = String.format("%s shulker boxes located at X: %s, Z: %s", shulkerCount, chunkX * 16, chunkZ * 16);
        Minecraft.getInstance().player.sendMessage(
                new StringTextComponent("Found at: " + message),
                Minecraft.getInstance().player.getUniqueID()
        );
    }
}
