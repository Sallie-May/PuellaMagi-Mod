package com.salliemay.uwu.world;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.Block;

import java.util.LinkedList;
import java.util.Queue;

public class Nuker {
    private static Queue<BlockPos> blocksToBreak = new LinkedList<>();
    private static boolean isBreaking = false;

    public static void breakNearbyBlocks() {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;

        if (player == null || !player.isAlive()) return;

        BlockPos playerPos = player.getPosition();
        int range = 3;

        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos blockPos = playerPos.add(x, y, z);
                    if (mc.world.isBlockLoaded(blockPos) && !mc.world.getBlockState(blockPos).isAir(mc.world, blockPos)) {
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
        if (isBreaking) {
            breakNextBlock();
        }
    }

    private static void breakNextBlock() {
        Minecraft mc = Minecraft.getInstance();
        World world = mc.world;
        PlayerEntity player = mc.player;

        if (blocksToBreak.isEmpty()) {
            isBreaking = false;
            return;
        }

        BlockPos blockPos = blocksToBreak.poll();

        if (blockPos != null) {
            Block block = world.getBlockState(blockPos).getBlock();

            if (!world.getBlockState(blockPos).isAir(world, blockPos)) {
                player.swingArm(mc.player.getActiveHand());

            }
        }
    }
}
