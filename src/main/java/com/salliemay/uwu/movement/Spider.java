package com.salliemay.uwu.movement;

import com.salliemay.uwu.SallieMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Spider {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;

        if (SallieMod.SpiderEnabled && player != null) {
            spiderMovement(player);
        }
    }

    private static void spiderMovement(PlayerEntity player) {
        if (isNextToWall(player)) {
            player.setMotion(player.getMotion().getX(), 0.3D, player.getMotion().getZ());
        }
    }

    private static boolean isNextToWall(PlayerEntity player) {
        World world = player.world;
        BlockPos playerPos = new BlockPos(player.getPosX(), player.getPosY(), player.getPosZ());

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int zOffset = -1; zOffset <= 1; zOffset++) {
                if (Math.abs(xOffset) == Math.abs(zOffset)) continue;
                BlockPos blockPos = playerPos.add(xOffset, 0, zOffset);
                if (world.getBlockState(blockPos).isSolid()) {
                    return true;
                }
            }
        }
        return false;
    }
}
