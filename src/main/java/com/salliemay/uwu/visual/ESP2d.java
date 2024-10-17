package com.salliemay.uwu.visual;

import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;
import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;

import java.awt.*;

@Mod.EventBusSubscriber(modid = SallieMod.MOD_ID, value = Dist.CLIENT)
public class ESP2d {

    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void onRender2D(TickEvent.RenderTickEvent event) {

        if (event.phase == TickEvent.Phase.END) {
            if (mc.world == null) {
                return;
            }

            for (Entity entity : mc.world.getAllEntities()) {
                if (entity != mc.player) {
                    render2DESP(entity.getBoundingBox().offset(-entity.getPosX(), -entity.getPosY(), -entity.getPosZ()), Color.WHITE, 2f);
                }
            }
        }
    }

    private static void render2DESP(AxisAlignedBB axisAlignedBB, Color color, float lineWidth) {
        Vector3d[] corners = new Vector3d[]{
                new Vector3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ),
                new Vector3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ),
                new Vector3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ),
                new Vector3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ),
                new Vector3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ),
                new Vector3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ),
                new Vector3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ),
                new Vector3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ)
        };

        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;

        for (Vector3d corner : corners) {
            double[] screenCoords = getScreenCoords(corner);
            if (screenCoords[0] < 0 || screenCoords[1] < 0 || screenCoords[0] > mc.getMainWindow().getWidth() || screenCoords[1] > mc.getMainWindow().getHeight()) {
                continue;
            }
            minX = Math.min(screenCoords[0], minX);
            minY = Math.min(screenCoords[1], minY);
            maxX = Math.max(screenCoords[0], maxX);
            maxY = Math.max(screenCoords[1], maxY);
        }

        if (minX != Double.MAX_VALUE) {
            RenderSystem.pushMatrix();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableTexture();
            RenderSystem.color4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
            GL11.glLineWidth(lineWidth);
            drawRect(minX, minY, maxX, maxY);
            RenderSystem.enableTexture();
            RenderSystem.disableBlend();
            RenderSystem.popMatrix();
        }
    }

    private static double[] getScreenCoords(Vector3d vec3d) {
        double[] screenCoords = new double[2];
        double width = mc.getMainWindow().getWidth();
        double height = mc.getMainWindow().getHeight();

        Vector3d cameraPos = mc.getRenderManager().info.getProjectedView();

        screenCoords[0] = (vec3d.x - cameraPos.x) * (width / 2) + (width / 2);
        screenCoords[1] = (vec3d.y - cameraPos.y) * (height / -2) + (height / 2);
        return screenCoords;
    }

    private static void drawRect(double minX, double minY, double maxX, double maxY) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_LINE_LOOP, net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION);
        buffer.pos(minX, minY, 0).endVertex();
        buffer.pos(minX, maxY, 0).endVertex();
        buffer.pos(maxX, maxY, 0).endVertex();
        buffer.pos(maxX, minY, 0).endVertex();
        tessellator.draw();
    }
}
