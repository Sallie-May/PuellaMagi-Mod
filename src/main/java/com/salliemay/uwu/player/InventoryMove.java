package com.salliemay.uwu.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class InventoryMove {
    private static final Minecraft mc = Minecraft.getInstance();

    // Movement key flags
    private static boolean isMovingForward = false;
    private static boolean isMovingBackward = false;
    private static boolean isMovingLeft = false;
    private static boolean isMovingRight = false;

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (mc.currentScreen instanceof ContainerScreen && !(mc.currentScreen instanceof ChatScreen)) {
            KeyBinding forwardKey = mc.gameSettings.keyBindForward;
            KeyBinding backKey = mc.gameSettings.keyBindBack;
            KeyBinding leftKey = mc.gameSettings.keyBindLeft;
            KeyBinding rightKey = mc.gameSettings.keyBindRight;

            // Update movement state based on key presses
            isMovingForward = forwardKey.isKeyDown();
            isMovingBackward = backKey.isKeyDown();
            isMovingLeft = leftKey.isKeyDown();
            isMovingRight = rightKey.isKeyDown();

        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (mc.player != null && mc.currentScreen instanceof ContainerScreen && !(mc.currentScreen instanceof ChatScreen)) {
            mc.player.setMotion(0, mc.player.getMotion().y, 0);

            float speed = 0.1F;

            if (isMovingForward) {
                mc.player.setMotion(mc.player.getMotion().x, mc.player.getMotion().y, -speed); // Move forward
            }
            if (isMovingBackward) {
                mc.player.setMotion(mc.player.getMotion().x, mc.player.getMotion().y, speed); // Move backward
            }
            if (isMovingLeft) {
                mc.player.setMotion(-speed, mc.player.getMotion().y, mc.player.getMotion().z); // Strafe left
            }
            if (isMovingRight) {
                mc.player.setMotion(speed, mc.player.getMotion().y, mc.player.getMotion().z); // Strafe right
            }

            if (mc.gameSettings.keyBindJump.isPressed() && mc.player.isOnGround()) {
                mc.player.jump();
                System.out.println("Player jumped");
            }

            System.out.println("Player Motion X: " + mc.player.getMotion().x);
            System.out.println("Player Motion Y: " + mc.player.getMotion().y);
            System.out.println("Player Motion Z: " + mc.player.getMotion().z);
        }
    }
}
