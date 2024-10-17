package com.salliemay.uwu.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.salliemay.uwu.SallieMod;
import com.salliemay.uwu.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class ClickGui extends Screen {
    private boolean isVisible = false;
    private static final Minecraft mc = Minecraft.getInstance();
    private ConfigManager.Config config;

    private MovableCategory combatCategory, movementCategory, worldCategory, miscCategory, visualCategory;
    private boolean draggingCategory = false;
    private int dragOffsetX, dragOffsetY;
    private MovableCategory selectedCategory = null;

    public ClickGui() {
        super(new StringTextComponent("Sallie Click GUI"));
        this.config = ConfigManager.loadConfig();

        combatCategory = new MovableCategory("Combat", 20, 20);
        movementCategory = new MovableCategory("Movement", 150, 20);
        worldCategory = new MovableCategory("World", 280, 20);
        miscCategory = new MovableCategory("Misc", 410, 20);
        visualCategory = new MovableCategory("Visual", 540, 20);

        initializeButtons();
    }

    private void initializeButtons() {
        combatCategory.addButton(new ModuleButton("KillAura", config.killauraEnabled));
        combatCategory.addButton(new ModuleButton("Aimbot", config.aimbotEnabled));
        combatCategory.addButton(new ModuleButton("Velocity", config.velocity));

        movementCategory.addButton(new ModuleButton("Flight", config.flightEnabled));
        movementCategory.addButton(new ModuleButton("Spin", config.spin));
        movementCategory.addButton(new ModuleButton("AutoSprint", config.AutoSprintEnabled));
        movementCategory.addButton(new ModuleButton("Jesus", config.Jesus));
        movementCategory.addButton(new ModuleButton("AirJump", config.AirJumpEnabled));
        movementCategory.addButton(new ModuleButton("NoFall", config.NoFall));
        movementCategory.addButton(new ModuleButton("AutoTeleport", config.autoTeleportEnabled));
        movementCategory.addButton(new ModuleButton("Step", config.StepEnabled));
        movementCategory.addButton(new ModuleButton("Spider", config.SpiderEnabled));

        worldCategory.addButton(new ModuleButton("Nuker", config.nukerEnabled));
        worldCategory.addButton(new ModuleButton("StashLogger", config.stashEnabled));


        visualCategory.addButton(new ModuleButton("FullBright", config.FullBrightEnabled));
        visualCategory.addButton(new ModuleButton("RGB Cam", config.rgbCamEnabled));
        visualCategory.addButton(new ModuleButton("NoWeather", config.noWeatherEnabled));
        visualCategory.addButton(new ModuleButton("TrueSight", config.TrueSightEnabled));
        visualCategory.addButton(new ModuleButton("NoFog", config.NoFogEnabled));
        visualCategory.addButton(new ModuleButton("GlowESP", config.GlowESPEnabled));
        visualCategory.addButton(new ModuleButton("ItemESP (Particles)", config.particlesEnabled));
        visualCategory.addButton(new ModuleButton("TargetHUD", config.TargetHUDEnabled));

        miscCategory.addButton(new ModuleButton("SessionStats", config.SessionStatsEnabled));
        miscCategory.addButton(new ModuleButton("Headless", config.isHeadLessEnabled));
        miscCategory.addButton(new ModuleButton("CMD Spammer", config.cmdSpammerEnabled));
        miscCategory.addButton(new ModuleButton("No Bad Effect", config.noBadEffectEnabled));


    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.fillGradient(matrixStack, 0, 0, width, height, 0xAA000000, 0xAA000000);

        combatCategory.render(matrixStack, mouseX, mouseY);
        movementCategory.render(matrixStack, mouseX, mouseY);
        worldCategory.render(matrixStack, mouseX, mouseY);
        miscCategory.render(matrixStack, mouseX, mouseY);
        visualCategory.render(matrixStack, mouseX, mouseY);
    }

    public boolean isPauseScreen() {
        return false;
    }

    public void toggle() {
        isVisible = !isVisible;
        if (isVisible) {
            mc.displayGuiScreen(this);
        } else {
            mc.displayGuiScreen(null);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);

        if (button == 0) {
            if (combatCategory.isHovered((int) mouseX, (int) mouseY)) {
                startDragging(combatCategory, (int) mouseX, (int) mouseY);
                return true;
            }
            if (movementCategory.isHovered((int) mouseX, (int) mouseY)) {
                startDragging(movementCategory, (int) mouseX, (int) mouseY);
                return true;
            }
            if (worldCategory.isHovered((int) mouseX, (int) mouseY)) {
                startDragging(worldCategory, (int) mouseX, (int) mouseY);
                return true;
            }
            if (miscCategory.isHovered((int) mouseX, (int) mouseY)) {
                startDragging(miscCategory, (int) mouseX, (int) mouseY);
                return true;
            }
            if (visualCategory.isHovered((int) mouseX, (int) mouseY)) {
                startDragging(visualCategory, (int) mouseX, (int) mouseY);
                return true;
            }
        }

        if (combatCategory.mouseClicked((int) mouseX, (int) mouseY, button)) {
            return true;
        }
        if (movementCategory.mouseClicked((int) mouseX, (int) mouseY, button)) {
            return true;
        }
        if (worldCategory.mouseClicked((int) mouseX, (int) mouseY, button)) {
            return true;
        }
        if (miscCategory.mouseClicked((int) mouseX, (int) mouseY, button)) {
            return true;
        }
        if (visualCategory.mouseClicked((int) mouseX, (int) mouseY, button)) {
            return true;
        }

        return false;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (draggingCategory && button == 0) {
            draggingCategory = false;
            selectedCategory = null;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (draggingCategory && selectedCategory != null) {
            selectedCategory.move((int) mouseX - dragOffsetX, (int) mouseY - dragOffsetY);
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    private void startDragging(MovableCategory category, int mouseX, int mouseY) {
        draggingCategory = true;
        selectedCategory = category;
        dragOffsetX = mouseX - category.x;
        dragOffsetY = mouseY - category.y;
    }

    private class MovableCategory {
        private String name;
        private int x, y;
        private final int width = 100;
        private final int height = 20;
        private List<ModuleButton> buttons = new ArrayList<>();

        public MovableCategory(String name, int x, int y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }

        public void render(MatrixStack matrixStack, int mouseX, int mouseY) {
            fill(matrixStack, x, y, x + width, y + height, 0xDD333333);
            drawCenteredString(matrixStack, font, name, x + width / 2, y + 5, 0xFFFFFF);

            int buttonY = y + height;
            for (ModuleButton button : buttons) {
                button.render(matrixStack, x, buttonY, mouseX, mouseY);
                buttonY += 20;
            }
        }

        public boolean isHovered(int mouseX, int mouseY) {
            return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        }

        public void move(int newX, int newY) {
            this.x = newX;
            this.y = newY;
        }

        public void addButton(ModuleButton button) {
            buttons.add(button);
        }

        public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
            int buttonY = y + height;
            for (ModuleButton button : buttons) {
                if (button.mouseClicked(x, buttonY, mouseX, mouseY, mouseButton)) {
                    return true;
                }
                buttonY += 20;
            }
            return false;
        }
    }

    private class ModuleButton {
        private final String name;
        private boolean enabled;

        public ModuleButton(String name, boolean initialState) {
            this.name = name;
            this.enabled = initialState;
        }

        public void render(MatrixStack matrixStack, int buttonX, int buttonY, int mouseX, int mouseY) {
            boolean hovered = mouseX >= buttonX && mouseX <= buttonX + 100 && mouseY >= buttonY && mouseY <= buttonY + 20;

            int color = enabled ? 0xFF66CC66 : 0xFF333333;
            if (hovered) {
                color = 0xFF888888;
            }

            fill(matrixStack, buttonX, buttonY, buttonX + 100, buttonY + 20, color);
            drawCenteredString(matrixStack, font, name, buttonX + 50, buttonY + 5, 0xFFFFFF);
        }

        public boolean mouseClicked(int buttonX, int buttonY, int mouseX, int mouseY, int mouseButton) {
            boolean hovered = mouseX >= buttonX && mouseX <= buttonX + 100 && mouseY >= buttonY && mouseY <= buttonY + 20;
            if (hovered && mouseButton == 0) {
                enabled = !enabled;
                updateConfig();
                sendToggleMessage();
                return true;
            }
            return false;
        }

        private void sendToggleMessage() {
            String message = TextFormatting.YELLOW + name + " has been " + (enabled ? "enabled" : "disabled") + "!";
            mc.player.sendMessage(new StringTextComponent(message), mc.player.getUniqueID());
        }

        private void updateConfig() {
            switch (name) {
                case "Hitbox Multiplier":
                    config.spin = enabled;
                    SallieMod.spin = enabled;
                    break;
                case "Spin":
                    config.spin = enabled;
                    SallieMod.spin = enabled;
                    break;
                case "SessionStats":
                    config.SessionStatsEnabled = enabled;
                    SallieMod.SessionStatsEnabled = enabled;
                    break;
                case "TargetHUD":
                    config.TargetHUDEnabled = enabled;
                    SallieMod.TargetHUDEnabled = enabled;
                    break;
                case "FullBright":
                    config.FullBrightEnabled = enabled;
                    SallieMod.FullBrightEnabled = enabled;
                    break;
                case "Headless":
                    config.isHeadLessEnabled = enabled;
                    SallieMod.isHeadLessEnabled = enabled;
                    break;
                case "Velocity":
                    config.velocity = enabled;
                    SallieMod.velocity = enabled;
                    break;
                case "ItemESP (Particles)":
                    config.particlesEnabled = enabled;
                    SallieMod.particlesEnabled = enabled;
                    break;
                case "NoWeather":
                    config.noWeatherEnabled = enabled;
                    SallieMod.noWeatherEnabled = enabled;
                    break;
                case "AutoTeleport":
                    config.autoTeleportEnabled = enabled;
                    SallieMod.autoTeleportEnabled = enabled;
                    break;
                case "KillAura":
                    config.killauraEnabled = enabled;
                    SallieMod.killauraEnabled = enabled;
                    break;
                case "Aimbot":
                    config.aimbotEnabled = enabled;
                    SallieMod.aimbotEnabled = enabled;
                    break;
                case "Flight":
                    config.flightEnabled = enabled;
                    SallieMod.flightEnabled = enabled;
                    break;
                case "AutoSprint":
                    config.AutoSprintEnabled = enabled;
                    SallieMod.AutoSprintEnabled = enabled;
                    break;
                case "Jesus":
                    config.Jesus = enabled;
                    SallieMod.JesusEnabled = enabled;
                    break;
                case "AirJump":
                    config.AirJumpEnabled = enabled;
                    SallieMod.AirJumpEnabled = enabled;
                    break;
                case "NoFall":
                    config.NoFall = enabled;
                    SallieMod.NoFallEnabled = enabled;
                    break;
                case "Step":
                    config.StepEnabled = enabled;
                    SallieMod.StepEnabled = enabled;
                    break;
                case "Spider":
                    config.SpiderEnabled = enabled;
                    SallieMod.SpiderEnabled = enabled;
                    break;
                case "Nuker":
                    config.nukerEnabled = enabled;
                    SallieMod.nukerEnabled = enabled;
                    break;
                case "StashLogger":
                    config.stashEnabled = enabled;
                    SallieMod.StashEnabled = enabled;
                    break;
                case "CMD Spammer":
                    config.cmdSpammerEnabled = enabled;
                    SallieMod.CMDSpammerEnabled = enabled;
                    break;
                case "Particles":
                    config.particlesEnabled = enabled;
                    SallieMod.particlesEnabled = enabled;
                    break;
                case "No Bad Effect":
                    config.noBadEffectEnabled = enabled;
                    SallieMod.noBadEffectEnabled = enabled;
                    break;
                case "RGB Cam":
                    config.rgbCamEnabled = enabled;
                    break;
                case "TrueSight":
                    config.TrueSightEnabled = enabled;
                    SallieMod.TrueSightEnabled = enabled;
                    break;

                case "NoFog":
                    config.NoFogEnabled = enabled;
                    SallieMod.NoFogEnabled = enabled;
                    break;
                case "GlowESP":
                    config.GlowESPEnabled = enabled;
                    SallieMod.GlowESPEnabled = enabled;
                    break;
                default:
                    break;
            }


            ConfigManager.saveConfig(config);
        }
    }
}
