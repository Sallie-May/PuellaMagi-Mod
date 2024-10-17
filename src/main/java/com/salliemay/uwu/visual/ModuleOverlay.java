package com.salliemay.uwu.visual;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ModuleOverlay {

    private static final Minecraft mc = Minecraft.getInstance();
    private static final List<String> activeModules = new ArrayList<>();

    private static final int[][] GRADIENT_COLORS = {
            {0x5BCEFA, 0xF5A9B8, 0xFFFFFF, 0xF5A9B8, 0x5BCEFA},
            {0xD60270, 0xD60270, 0x9B4F96, 0x0038A8, 0x0038A8},
            {0xFF218C, 0xFFD800, 0x21B1FF},
            {0xB57EDC, 0xFFFFFF, 0x4A8123},
            {0xFCF434, 0xFFFFFF, 0x9C59D1, 0x2C2C2C},
    };

    public static final String[] GRADIENT_NAMES = {
            "Transgender Flag",
            "Bisexual Flag",
            "Pansexual Flag",
            "Genderqueer Flag",
            "Non-binary Flag",
    };

    private static int currentGradientIndex = 0;

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            renderActiveModulesOverlay(event.getMatrixStack());
        }
    }

    private static void renderActiveModulesOverlay(MatrixStack matrixStack) {
        if (mc.player == null) return;

        activeModules.clear();

        List<String> combatModules = new ArrayList<>();
        if (SallieMod.killauraEnabled) combatModules.add("Kill-aura (Enabled)");
        if (SallieMod.aimbotEnabled) combatModules.add("Aim-bot Enabled (Range : " + SallieMod.aimbotRange + ")");
        if (SallieMod.velocity) combatModules.add("Velocity (Enabled)");

        List<String> movementModules = new ArrayList<>();
        if (SallieMod.StepEnabled) movementModules.add("Step (Enabled)");
        if (SallieMod.SpiderEnabled) movementModules.add("Spider (Enabled)");
        if (SallieMod.SpeedEnabled) movementModules.add("Speed (Enabled) (Speed: " + SallieMod.SpeedMultiplier + ")");
        if (SallieMod.flightEnabled) movementModules.add("Flight (Enabled)");
        if (SallieMod.AutoSprintEnabled) movementModules.add("Auto Sprint (Enabled)");
        if (SallieMod.AirJumpEnabled) movementModules.add("Air Jump (Enabled)");
        if (SallieMod.spin) movementModules.add("Spin (Enabled)");

        List<String> worldModules = new ArrayList<>();
        if (SallieMod.noWeatherEnabled) worldModules.add("NoWeather (Enabled)");
        if (SallieMod.fakeCreativeEnabled) worldModules.add("Fake Creative (Enabled)");
        if (SallieMod.StashEnabled) worldModules.add("StashFinder (Enabled)");
        worldModules.add("Teleport Height: " + SallieMod.teleportHeight);
        worldModules.add("Hclip Distance: " + SallieMod.HclipFar);

        List<String> playerModules = new ArrayList<>();
        if (SallieMod.NoHurtCamEnabled) playerModules.add("NoHurtCam (Enabled)");
        if (SallieMod.NoFallEnabled) playerModules.add("NoFall (Enabled)");
        if (SallieMod.autoTeleportEnabled) playerModules.add("Auto-teleport (Enabled)");
        if (SallieMod.RespawnEnabled) playerModules.add("Respawn (Enabled)");
        if (SallieMod.TrueSightEnabled) playerModules.add("TrueSight (Enabled)");
        if (SallieMod.isHeadLessEnabled) playerModules.add("Headless (Enabled)");

        List<String> visualModules = new ArrayList<>();
        if (SallieMod.GlowESPEnabled) visualModules.add("GlowESP (Enabled)");
        if (SallieMod.particlesEnabled) visualModules.add("ItemESP (Enabled)");
        if (SallieMod.FullBrightEnabled) visualModules.add("FullBright (Enabled)");
        if (SallieMod.AmbienceEnabled) visualModules.add("Ambience (Enabled)");

        List<String> miscModules = new ArrayList<>();
        if (SallieMod.randomTeleportEnabled) miscModules.add("Random Teleport (Enabled)");
        if (SallieMod.noBadEffectEnabled) miscModules.add("NoBadEffect (Enabled)");
        if (!SallieMod.suffixDisabled) miscModules.add("Suffix (Enabled)");
        if (SallieMod.nukerEnabled) miscModules.add("Nuker (Enabled)");
        if (SallieMod.CMDSpammerEnabled) combatModules.add("Command Spammer (Enabled)");

        if (!combatModules.isEmpty()) {
            activeModules.add("Combat Modules:");
            activeModules.addAll(combatModules);
        }
        if (!movementModules.isEmpty()) {
            activeModules.add("Movement Modules:");
            activeModules.addAll(movementModules);
        }
        if (!worldModules.isEmpty()) {
            activeModules.add("World Modules:");
            activeModules.addAll(worldModules);
        }
        if (!playerModules.isEmpty()) {
            activeModules.add("Player Modules:");
            activeModules.addAll(playerModules);
        }
        if (!visualModules.isEmpty()) {
            activeModules.add("Visual Modules:");
            activeModules.addAll(visualModules);
        }
        if (!miscModules.isEmpty()) {
            activeModules.add("Miscellaneous Modules:");
            activeModules.addAll(miscModules);
        }

        renderModules(matrixStack);
    }



    private static void renderModules(MatrixStack matrixStack) {
        int screenWidth = mc.getMainWindow().getScaledWidth();
        int yOffset = 10;
        int xOffset = screenWidth - 10;

        for (String module : activeModules) {
            renderTextWithGradient(matrixStack, module, xOffset, yOffset);
            yOffset += 10;
        }
    }

    private static void renderTextWithGradient(MatrixStack matrixStack, String text, int xOffset, int yOffset) {
        int screenWidth = mc.getMainWindow().getScaledWidth();

        int totalWidth = mc.fontRenderer.getStringWidth(text);

        if (xOffset + totalWidth > screenWidth) {
            xOffset = screenWidth - totalWidth - 10;
        }

        int textLength = text.length();
        int startX = xOffset;

        int[] gradientColors = GRADIENT_COLORS[currentGradientIndex];

        for (int i = 0; i < textLength; i++) {
            char c = text.charAt(i);

            float position = (float) i / (float) (textLength - 1);
            int color = getColorForPosition(position, gradientColors);

            mc.fontRenderer.drawStringWithShadow(matrixStack, Character.toString(c), startX, yOffset, color);

            startX += mc.fontRenderer.getStringWidth(Character.toString(c));
        }
    }

    private static int getColorForPosition(float position, int[] gradientColors) {
        int startSegment = (int) (position * (gradientColors.length - 1));
        int endSegment = Math.min(startSegment + 1, gradientColors.length - 1);

        float ratio = (position * (gradientColors.length - 1)) - startSegment;

        return blendColors(gradientColors[startSegment], gradientColors[endSegment], ratio);
    }

    private static int blendColors(int color1, int color2, float ratio) {
        ratio = Math.min(1.0f, Math.max(0.0f, ratio));

        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;

        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;

        int r = (int) (r1 + (r2 - r1) * ratio);
        int g = (int) (g1 + (g2 - g1) * ratio);
        int b = (int) (b1 + (b2 - b1) * ratio);

        return (r << 16) | (g << 8) | b;
    }

    public static void setGradientIndex(int index) {
        if (index >= 0 && index < GRADIENT_COLORS.length) {
            currentGradientIndex = index;
        }
    }

    public static String getGradientList() {
        StringBuilder gradientList = new StringBuilder();
        for (int i = 0; i < GRADIENT_NAMES.length; i++) {
            gradientList.append(i).append(": ").append(GRADIENT_NAMES[i]).append("\n");
        }
        return gradientList.toString();
    }
}
