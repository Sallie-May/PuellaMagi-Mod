package com.salliemay.uwu.visual;

import com.salliemay.uwu.SallieMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class GlowESP {
    private final Minecraft mc = Minecraft.getInstance();
    private final boolean GlowESPEnabled = SallieMod.GlowESPEnabled;

    public void toggle() {
        if (GlowESPEnabled) {
            enableGlow();
        } else {
            disableGlow();
        }
    }

    private void enableGlow() {
        if (mc.world != null) {
            for (PlayerEntity playerEntity : mc.world.getPlayers()) {
                if (playerEntity != mc.player) {
                    playerEntity.setGlowing(true);
                }
            }
        }
    }

    private void disableGlow() {
        if (mc.world != null) {
            for (PlayerEntity playerEntity : mc.world.getPlayers()) {
                if (playerEntity != mc.player) {
                    playerEntity.setGlowing(false);
                }
            }
        }
    }
}
