package com.salliemay.uwu.visual;
import com.salliemay.uwu.SallieMod;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.mojang.blaze3d.matrix.MatrixStack;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class TargetHUD {

    private static final Minecraft mc = Minecraft.getInstance();
    private static LivingEntity targetEntity = null;
    private static long lastAttackTime = 0;

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL || targetEntity == null) {
            return;
        }
        if (SallieMod.TargetHUDEnabled) {
            if (targetEntity.isAlive()) {
                MatrixStack matrixStack = event.getMatrixStack();
                renderTargetHUD(matrixStack);
            }

        }
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        if (SallieMod.TargetHUDEnabled) {
            if (event.getTarget() instanceof LivingEntity) {
                targetEntity = (LivingEntity) event.getTarget();
                lastAttackTime = System.currentTimeMillis();
            }
        }
    }

    private static void renderTargetHUD(MatrixStack matrixStack) {
        int screenWidth = mc.getMainWindow().getScaledWidth();
        int screenHeight = mc.getMainWindow().getScaledHeight();

        int hudX = (screenWidth / 2) + 50;
        int hudY = (screenHeight / 2) + 20;

        AbstractGui.fill(matrixStack, hudX, hudY, hudX + 140, hudY + 60, 0x90000000);

        mc.fontRenderer.drawString(matrixStack, targetEntity.getName().getString(), hudX + 5, hudY + 5, 0xFFFFFF);

        float health = targetEntity.getHealth();
        float maxHealth = targetEntity.getMaxHealth();
        int healthBarWidth = (int) (120 * (health / maxHealth));
        AbstractGui.fill(matrixStack, hudX + 5, hudY + 25, hudX + 5 + healthBarWidth, hudY + 35, 0xFF00FF00);

        String healthText = String.format("%.1f / %.1f (%.1f%%)", health, maxHealth, (health / maxHealth) * 100);
        mc.fontRenderer.drawString(matrixStack, healthText, hudX + 5, hudY + 45, 0xFFFFFF);

    }

    @SubscribeEvent
    public static void onRenderTick(net.minecraftforge.event.TickEvent.RenderTickEvent event) {
        if (SallieMod.TargetHUDEnabled) {

            if (targetEntity != null && System.currentTimeMillis() - lastAttackTime > 5000) {
                targetEntity = null;
            }
        }
    }
}
