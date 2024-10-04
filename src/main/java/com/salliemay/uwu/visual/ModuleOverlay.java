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
            "Nonbinary Flag",
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

        if (SallieMod.killauraEnabled) {
            activeModules.add("Killaura (Enabled)");
        }

        if (SallieMod.CMDSpammerEnabled) {
            activeModules.add("Command Spammer (Enabled)");
        }
        if (SallieMod.Spin) {
            activeModules.add("Spin (Enabled)");
        }

        if (SallieMod.particlesEnabled) {
            activeModules.add("Item Laser (Enabled)");
        }

        if (SallieMod.randomTeleportEnabled) {
            activeModules.add("Random Teleport (Enabled)");
        }

        if (SallieMod.aimbotEnabled) {
            activeModules.add("Aimbot Enabled (Range : "+SallieMod.aimbotRange+" )");
        }

        if (!SallieMod.suffixDisabled) {
            activeModules.add("Suffix (Enabled)");
        }

        if (SallieMod.NukerEnabled) {
            activeModules.add("Nuker (Enabled)");
        }

        if (SallieMod.StashEnabled) {
            activeModules.add("StashFinder (Enabled)");
        }
        if (SallieMod.FakeCreativeEnabled) {
            activeModules.add("Fake Creative (Enabled)");
        }

        if (SallieMod.NoWeatherEnabled) {
            activeModules.add("NoWeather (Enabled)");
        }

        activeModules.add("Teleport Height: " + SallieMod.teleportHeight);
        activeModules.add("Hclip Distance: " + SallieMod.HclipFar);

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
        float segmentLength = (float) gradientColors.length / 1.0f; // Since we're only interpolating over the whole string
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
