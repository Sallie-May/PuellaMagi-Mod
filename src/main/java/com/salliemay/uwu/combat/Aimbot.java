package com.salliemay.uwu.combat;

import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class Aimbot {

    private float originalYaw;
    private float originalPitch;

    public void alwaysLookAtClosestPlayer() {
        PlayerEntity player = Minecraft.getInstance().player;

        if (player == null || !player.isAlive()) {
            return;
        }

        double radius = SallieMod.aimbotRange;
        AxisAlignedBB boundingBox = new AxisAlignedBB(player.getPosition()).grow(radius);
        List<PlayerEntity> closestPlayers = new ArrayList<>();
        double closestDistance = Double.MAX_VALUE;

        for (Entity entity : player.world.getEntitiesWithinAABB(Entity.class, boundingBox)) {
            if (entity instanceof PlayerEntity && entity != player) {
                PlayerEntity otherPlayer = (PlayerEntity) entity;

                if (otherPlayer.isAlive()) {
                    double distance = player.getDistance(otherPlayer);

                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestPlayers.clear();
                        closestPlayers.add(otherPlayer);
                    } else if (distance == closestDistance) {
                        closestPlayers.add(otherPlayer);
                    }
                }
            }
        }

        if (!closestPlayers.isEmpty()) {
            PlayerEntity closestPlayer = closestPlayers.get(0);

            faceEntity(player, closestPlayer);
        }
    }

    private void faceEntity(PlayerEntity player, Entity entity) {
        originalYaw = player.rotationYaw;
        originalPitch = player.rotationPitch;

        double dx = entity.getPosX() - player.getPosX();
        double dy = entity.getPosYEye() - player.getPosYEye();
        double dz = entity.getPosZ() - player.getPosZ();

        double distance = MathHelper.sqrt(dx * dx + dz * dz);
        float yaw = (float) (MathHelper.atan2(dz, dx) * (180D / Math.PI)) - 90.0F;
        float pitch = (float) -(MathHelper.atan2(dy, distance) * (180D / Math.PI));

        player.rotationYaw = yaw;
        player.rotationPitch = pitch;

    }}