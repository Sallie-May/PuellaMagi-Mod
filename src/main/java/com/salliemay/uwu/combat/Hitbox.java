package com.salliemay.uwu.combat;

import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class Hitbox {

    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (SallieMod.HitBox) {
            LivingEntity entity = event.getEntityLiving();

            double size = 3;

            AxisAlignedBB newBoundingBox = new AxisAlignedBB(
                    entity.getPosX() - size,
                    entity.getPosY(),
                    entity.getPosZ() - size,
                    entity.getPosX() + size,
                    entity.getPosY() + entity.getHeight(),
                    entity.getPosZ() + size
            );

            entity.setBoundingBox(newBoundingBox);
        }
    }
}