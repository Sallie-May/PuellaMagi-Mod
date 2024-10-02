package com.salliemay.uwu.visual;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class HealthOverlay {

    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            renderHealthOverlay(event.getMatrixStack());
        }
    }

    private static void renderHealthOverlay(MatrixStack matrixStack) {
        if (mc.player == null) return;

        float currentHealth = mc.player.getHealth();
        float maxHealth = mc.player.getMaxHealth();

        String healthText = String.format("Health: %.1f / %.1f", currentHealth, maxHealth);

        int xPos = 10;
        int yPos = 10;
        int color = SallieMod.healthTextColor;

        mc.fontRenderer.drawStringWithShadow(matrixStack, healthText, xPos, yPos, color);

        if (currentHealth < 400) {
            String alertMessage = "YOU CAN BE ONE-SHOTTED";
            int alertColor = 0xFF0000;

            int alertXPos = xPos;
            int alertYPos = yPos + 10;

            mc.fontRenderer.drawStringWithShadow(matrixStack, alertMessage, alertXPos, alertYPos, alertColor);
        }
    }
}
