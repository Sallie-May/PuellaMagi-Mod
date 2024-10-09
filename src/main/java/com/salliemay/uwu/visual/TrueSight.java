package com.salliemay.uwu.visual;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;

public class TrueSight {

    private static boolean showPlayers = true;
    private static boolean showAnimals = true;
    private static boolean showNeutral = true;
    private static boolean showSelf = true;
    private static boolean showMobs = true;

    static final Minecraft mc = Minecraft.getInstance();

    public static void revealEntities() {

        if (mc.world == null || mc.player == null) return;

        for (Entity entity : mc.world.getAllEntities()) {
            if (isValidEntity(entity)) {
                entity.setInvisible(false);
            }
        }
    }

    private static boolean isValidEntity(Entity entity) {

        if (entity == mc.player && showSelf && showPlayers) return true;
        if (entity instanceof PlayerEntity && showPlayers) return true;
        if (entity instanceof AnimalEntity && showAnimals) return true;
        if (entity instanceof VillagerEntity && showNeutral) return true;
        return entity instanceof MonsterEntity && showMobs;
    }

}
