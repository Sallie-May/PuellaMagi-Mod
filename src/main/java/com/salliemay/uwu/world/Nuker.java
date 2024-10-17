package com.salliemay.uwu.world;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.client.multiplayer.PlayerController;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Nuker {
    private static final Queue<BlockPos> blocksToBreak = new LinkedList<>();
    private static boolean isBreaking = false;
    private static final int MAX_RANGE = 4;
    private static final int DEFAULT_RANGE = 4;
    private static int range = DEFAULT_RANGE;
    private static final long BREAK_DELAY = 100;
    private static long lastBreakTime = 0;

    private static final Random random = new Random();
    private static final int BLOCKS_PER_TICK = 3;

    public static void reset() {
        blocksToBreak.clear();
        isBreaking = false;
        lastBreakTime = 0;
        range = DEFAULT_RANGE;
    }

    public static void breakNearbyBlocks() {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        World world = mc.world;

        if (player == null || world == null || !player.isAlive()) {
            return;
        }

        BlockPos playerPos = player.getPosition();

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
            breakNextBlocks();
        }
    }

    public static void tick() {
        if (isBreaking && !blocksToBreak.isEmpty() && System.currentTimeMillis() - lastBreakTime >= BREAK_DELAY) {
            breakNextBlocks();
        }
    }

    private static void breakNextBlocks() {
        Minecraft mc = Minecraft.getInstance();
        World world = mc.world;
        PlayerEntity player = mc.player;
        PlayerController controller = mc.playerController;

        if (world == null || player == null || controller == null || blocksToBreak.isEmpty()) {
            isBreaking = false;
            return;
        }

        int blocksBroken = 0;

        while (blocksBroken < BLOCKS_PER_TICK && !blocksToBreak.isEmpty()) {
            BlockPos blockPos = blocksToBreak.poll();

            if (blockPos != null && !world.isAirBlock(blockPos)) {
                boolean successful = controller.onPlayerDamageBlock(blockPos, player.getHorizontalFacing());

                if (successful) {
                    player.swingArm(player.getActiveHand());
                    lastBreakTime = System.currentTimeMillis();
                    blocksBroken++;
                } else {
                    blocksToBreak.add(blockPos);
                }
            }
        }

        if (blocksToBreak.isEmpty()) {
            isBreaking = false;
        }
    }

    public static void setRange(int newRange) {
        range = Math.max(1, Math.min(newRange, MAX_RANGE));
    }

    private static double randomPoint(final double one, final double two) {
        if (one == two) {
            return one;
        }

        final double delta = two - one;
        final double offset = random.nextDouble() * delta;
        return one + offset;
    }
}
