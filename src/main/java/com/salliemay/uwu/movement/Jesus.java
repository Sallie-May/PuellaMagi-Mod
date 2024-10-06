package com.salliemay.uwu.movement;

import com.salliemay.uwu.SallieMod;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Jesus {

    private final Minecraft mc = Minecraft.getInstance();

    public void checkWaterMovement() {
        ClientPlayerEntity player = mc.player;

        if (player == null) {
            return;
        }

        World world = player.world;
        BlockPos posBelowPlayer = new BlockPos(player.getPosX(), player.getPosY() - 0.5, player.getPosZ());

        if (world.getBlockState(posBelowPlayer).getBlock() == Blocks.WATER ||
                world.getBlockState(posBelowPlayer).getBlock() == Blocks.KELP ||
                world.getBlockState(posBelowPlayer).getBlock() == Blocks.KELP_PLANT) {

            player.setOnGround(true);

            if (player.getPosY() < posBelowPlayer.getY() + 1) {
                player.setPosition(player.getPosX(), posBelowPlayer.getY() + 1, player.getPosZ());
            }

            if (player.getMotion().y < 0) {
                player.setMotion(player.getMotion().x, 0, player.getMotion().z);
            }
        }
    }
}
