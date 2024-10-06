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
    private static final int MAX_RANGE = 7;  // Max range of block breaking
    private static final int DEFAULT_RANGE = 4; // Default range value
    private static int range = DEFAULT_RANGE;  // Adjustable range
    private static final long BREAK_DELAY = 100; // Delay between breaks in milliseconds
    private static long lastBreakTime = 0; // Tracks the time of the last break

    private static final Random random = new Random();

    public static void breakNearbyBlocks() {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        World world = mc.world;

        if (player == null || !player.isAlive() || world == null) return;

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
            breakNextBlock();
        }
    }
    public static void tick() {
        if (isBreaking && !blocksToBreak.isEmpty() && System.currentTimeMillis() - lastBreakTime >= BREAK_DELAY) {
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
                lastBreakTime = System.currentTimeMillis();
            } else {
                blocksToBreak.add(blockPos);
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
        if (one == two)
            return one;

        final double delta = two - one;
        final double offset = random.nextFloat() * delta;
        return one + offset;
    }
}
