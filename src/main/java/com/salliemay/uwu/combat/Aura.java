package com.salliemay.uwu.combat;

import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.client.CUseEntityPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Aura {

    private long lastAttackTime = 0;
    private static final long ATTACK_COOLDOWN = 100;
    private static final double TELEPORT_DISTANCE = 5.0;
    private boolean isAttacking = false;
    private long attackEndTime = 0;
    private final Random random = new Random();


    public void attackNearbyEntities() {
        PlayerEntity player = Minecraft.getInstance().player;

        if (player == null || !player.isAlive()) {
            return;
        }

        if (System.currentTimeMillis() - lastAttackTime > ATTACK_COOLDOWN) {
            AxisAlignedBB boundingBox = new AxisAlignedBB(player.getPosition()).grow(SallieMod.AuraRange);
            List<PlayerEntity> closestEntities = new ArrayList<>();
            double closestDistance = Double.MAX_VALUE;

            for (Entity entity : player.world.getEntitiesWithinAABB(Entity.class, boundingBox)) {
                if (entity instanceof PlayerEntity && entity != player) {
                    PlayerEntity targetPlayer = (PlayerEntity) entity;

                    if (targetPlayer.isAlive()) {
                        double distance = player.getDistance(targetPlayer);

                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestEntities.clear();
                            closestEntities.add(targetPlayer);
                        } else if (distance == closestDistance) {
                            closestEntities.add(targetPlayer);
                        }
                    }
                }
            }

            if (!closestEntities.isEmpty()) {
                PlayerEntity closestPlayer = closestEntities.get(random.nextInt(closestEntities.size()));
                Vector3d originalPosition = player.getPositionVec();
                double distanceToClosest = player.getDistance(closestPlayer);

                if (!isAttacking) {
                    isAttacking = true;
                    attackEndTime = System.currentTimeMillis() + 100;
                }

                int attempts = 0;
                while (distanceToClosest > 5.0 && attempts < 100) {
                    Vector3d direction = new Vector3d(
                            closestPlayer.getPosX() - player.getPosX(),
                            0,
                            closestPlayer.getPosZ() - player.getPosZ()
                    ).normalize();

                    Vector3d teleportPosition = originalPosition.add(direction.scale(TELEPORT_DISTANCE));

                    player.setPosition(teleportPosition.x, teleportPosition.y, teleportPosition.z);

                    distanceToClosest = player.getDistance(closestPlayer);
                    attempts++;
                }

                if (distanceToClosest <= 5.0) {
                    sendAttackPacket(player, closestPlayer);
                    lastAttackTime = System.currentTimeMillis();
                }

                if (isAttacking && System.currentTimeMillis() > attackEndTime) {
                    Vector3d currentPos = player.getPositionVec();
                    Vector3d returnDirection = new Vector3d(
                            originalPosition.x - currentPos.x,
                            0,
                            originalPosition.z - currentPos.z
                    ).normalize();

                    Vector3d returnPosition = currentPos.add(returnDirection.scale(TELEPORT_DISTANCE));

                    player.setPosition(returnPosition.x, returnPosition.y, returnPosition.z);

                    if (currentPos.distanceTo(originalPosition) < TELEPORT_DISTANCE) {
                        player.setPosition(originalPosition.x, originalPosition.y, originalPosition.z);
                        isAttacking = false;
                    }
                }
            }
        }
    }

    private void sendAttackPacket(PlayerEntity player, LivingEntity target) {
        Minecraft.getInstance().getConnection().sendPacket(new CUseEntityPacket(target, true));
        player.attackTargetEntityWithCurrentItem(target);
    }

    private void faceEntity(PlayerEntity player, Entity entity) {
        double dx = entity.getPosX() - player.getPosX();
        double dy = entity.getPosYEye() - player.getPosYEye();
        double dz = entity.getPosZ() - player.getPosZ();

        double distance = MathHelper.sqrt(dx * dx + dz * dz);
        float pitch = (float) -(MathHelper.atan2(dy, distance) * (180D / Math.PI));
    }
}
