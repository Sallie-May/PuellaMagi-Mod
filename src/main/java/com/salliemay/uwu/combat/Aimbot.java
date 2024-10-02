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

    // Declare variables to hold original yaw and pitch
    private float originalYaw;
    private float originalPitch;

    public void alwaysLookAtClosestPlayer() {
        PlayerEntity player = Minecraft.getInstance().player;

        // Check if player is null or not alive
        if (player == null || !player.isAlive()) {
            return; // Exit early if the player is invalid
        }

        double radius = SallieMod.aimbotRange;
        AxisAlignedBB boundingBox = new AxisAlignedBB(player.getPosition()).grow(radius);
        List<PlayerEntity> closestPlayers = new ArrayList<>();
        double closestDistance = Double.MAX_VALUE;

        // Find the closest player entity within the bounding box
        for (Entity entity : player.world.getEntitiesWithinAABB(Entity.class, boundingBox)) {
            if (entity instanceof PlayerEntity && entity != player) {
                PlayerEntity otherPlayer = (PlayerEntity) entity;

                // Check if the player is alive before proceeding
                if (otherPlayer.isAlive()) {
                    double distance = player.getDistance(otherPlayer);

                    // If this player is closer than the current closest, update closest player
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestPlayers.clear();
                        closestPlayers.add(otherPlayer);
                    } else if (distance == closestDistance) {
                        closestPlayers.add(otherPlayer); // Handle multiple players at the same distance
                    }
                }
            }
        }

        if (!closestPlayers.isEmpty()) {
            PlayerEntity closestPlayer = closestPlayers.get(0); // Pick the first closest player

            // Make the player look at the head of the closest player
            faceEntity(player, closestPlayer);
        }
    }

    private void faceEntity(PlayerEntity player, Entity entity) {
        // Save the original yaw and pitch
        originalYaw = player.rotationYaw; // Store the current yaw
        originalPitch = player.rotationPitch; // Store the current pitch

        double dx = entity.getPosX() - player.getPosX();
        double dy = entity.getPosYEye() - player.getPosYEye(); // Use YEye to target the head
        double dz = entity.getPosZ() - player.getPosZ();

        double distance = MathHelper.sqrt(dx * dx + dz * dz); // Horizontal distance
        float yaw = (float) (MathHelper.atan2(dz, dx) * (180D / Math.PI)) - 90.0F;
        float pitch = (float) -(MathHelper.atan2(dy, distance) * (180D / Math.PI));

        // Update player's yaw and pitch to look at the closest player's head
        player.rotationYaw = yaw;
        player.rotationPitch = pitch;

    }}