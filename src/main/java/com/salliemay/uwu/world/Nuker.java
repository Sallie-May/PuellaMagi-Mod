package com.salliemay.uwu.world;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.client.multiplayer.PlayerController;
import java.util.LinkedList;
import java.util.Queue;

public class Nuker {
    private static final Queue<BlockPos> blocksToBreak = new LinkedList<>();
    private static boolean isBreaking = false;

    public static void breakNearbyBlocks() {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        World world = mc.world;

        if (player == null || !player.isAlive() || world == null) return;

        BlockPos playerPos = player.getPosition();
        int range = 3;

        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos blockPos = playerPos.add(x, y, z);
                    if (world.isBlockLoaded(blockPos) && !world.isAirBlock(blockPos)) {
                        blocksToBreak.add(blockPos);
                    }
                }
            }
        }

        if (!isBreaking && !blocksToBreak.isEmpty()) {
            isBreaking = true;
            breakNextBlock();
        }
    }

    public static void tick() {
        if (isBreaking && !blocksToBreak.isEmpty()) {
            breakNextBlock();
        }
    }

    private static void breakNextBlock() {
        Minecraft mc = Minecraft.getInstance();
        World world = mc.world;
        PlayerEntity player = mc.player;
        PlayerController controller = mc.playerController;

        if (world == null || player == null || controller == null || blocksToBreak.isEmpty()) {
            isBreaking = false;
            return;
        }

        BlockPos blockPos = blocksToBreak.poll();

        if (blockPos != null && !world.isAirBlock(blockPos)) {
            boolean successful = controller.onPlayerDamageBlock(blockPos, player.getHorizontalFacing());

            if (successful) {
                player.swingArm(player.getActiveHand());
            } else {
                blocksToBreak.add(blockPos);
            }
        }

        if (blocksToBreak.isEmpty()) {
            isBreaking = false;
        }
    }
}
