package com.salliemay.uwu;
import com.salliemay.uwu.combat.Aura;
import com.salliemay.uwu.combat.Aimbot;
import com.salliemay.uwu.misc.Respawn;
import com.salliemay.uwu.movement.*;
import com.salliemay.uwu.visual.*;
import com.salliemay.uwu.visual.TargetHUD;
import com.salliemay.uwu.world.Nuker;
import com.salliemay.uwu.world.StashLogger;
import com.salliemay.uwu.config.ConfigManager;

import java.awt.Desktop;
import java.net.URI;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.GameType;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import com.salliemay.uwu.gui.ClickGui;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;


@Mod(SallieMod.MOD_ID)
public class SallieMod {
    public static final String MOD_ID = "salliemay";
    private static final Logger LOGGER = LogManager.getLogger();
    public static int healthTextColor = 0xFF64E0;

    private static final ConfigManager.Config config = ConfigManager.loadConfig();
    private static Jesus jesus = new Jesus();
    private static GlowESP Glow = new GlowESP();


    public static final KeyBinding teleportKey = new KeyBinding("VClip", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding HClipKey = new KeyBinding("Hclip", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding teleportToggleKey = new KeyBinding("RDM Teleport", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding KillAura = new KeyBinding("Killaura", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding AimbotKey = new KeyBinding("Aimbot", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding toggleParticlesKey = new KeyBinding("Item Laser", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding NukerKey = new KeyBinding("Nuker", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding StashKey = new KeyBinding("Stash", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding CMDSpammer = new KeyBinding("CMDSpammer", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding SpinKey = new KeyBinding("Spin", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding FakeCreativeKey = new KeyBinding("FakeCreative", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding RGBCameraKeys = new KeyBinding("RGBCamera", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding VelocityKey = new KeyBinding("Velocity", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    private static final KeyBinding NoBadEffectKey = new KeyBinding("NoBadEffect", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding NoHurtCamKey = new KeyBinding("NoHurtCam", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding FlightKey = new KeyBinding("Fly", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding NoFall = new KeyBinding("NoFall", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding AutoSprintKey = new KeyBinding("AutoSprint", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding NoFogKey = new KeyBinding("NoFog", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding JesusKey = new KeyBinding("Jesus", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding SpeedKey = new KeyBinding("Speed", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding TrueSightKey = new KeyBinding("TrueSight", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding AmbienceKey = new KeyBinding("Ambience", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding AirJumpKey = new KeyBinding("AirJump", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding GlowESPKey = new KeyBinding("GlowESP", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding SpiderKey = new KeyBinding("Spider", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding RespawnKey = new KeyBinding("AutoRespawn", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    public static final KeyBinding StepKey = new KeyBinding("Step", GLFW.GLFW_KEY_UNKNOWN, "SallieConfig");
    private static String targetPlayerName = null;
    public static final KeyBinding toggleClickGuiKey = new KeyBinding("Open Click GUI", GLFW.GLFW_KEY_I, "SallieConfig");


    private final com.salliemay.uwu.config.FriendManager friendManager = new com.salliemay.uwu.config.FriendManager();

    public static boolean randomTeleportEnabled = config.randomTeleportEnabled;
    public static boolean NoFallEnabled = config.NoFall;
    public static boolean killauraEnabled = config.killauraEnabled;
    public static boolean autoTeleportEnabled = config.autoTeleportEnabled;
    public static boolean aimbotEnabled = config.aimbotEnabled;
    public static boolean nukerEnabled = config.nukerEnabled;
    public static boolean AirJumpEnabled = config.AirJumpEnabled;
    public static boolean crasher = config.crasher;
    public static boolean SpiderEnabled = config.SpiderEnabled;
    public static boolean spin = config.spin;
    public static boolean RespawnEnabled = config.RespawnEnabled;
    public static boolean fakeCreativeEnabled = config.fakeCreativeEnabled;
    public static boolean noWeatherEnabled = config.noWeatherEnabled;
    public static boolean noBadEffectEnabled = config.noBadEffectEnabled;
    public static boolean velocity = config.velocity;
    public static boolean JesusEnabled = config.Jesus;
    public static boolean StepEnabled = config.StepEnabled;
    public static boolean AmbienceEnabled = config.AmbienceEnabled;
    public static long timeOfDay = config.timeOfDay;
    public static float StepHeight = config.StepHeight;

    public static boolean NoHurtCamEnabled = config.noHurtCamEnabled;
    public static int rotationMode = config.rotationMode;
    public static int healthlimit = config.healthlimit;

    public static float SpeedMultiplier = config.SpeedMultiplier;
    public static boolean SpeedEnabled = config.SpeedEnabled;

    public static boolean AutoSprintEnabled = config.AutoSprintEnabled;
    private static float currentYaw = 0;
    private static final float yawIncrement = 45;


    private static boolean hasTeleported = config.hasTeleported;

    public static RGBCam rgbModule;

    private String suffix = config.suffix;
    public static int commandDelay = config.commandDelay;
    public static int tickCounter = 0;
    public static String ToucheCMD = "";

    public static boolean suffixDisabled = config.suffixDisabled;
    public static double teleportHeight = config.teleportHeight;
    public static double HclipFar = config.hclipFar;
    public static float FlySpeed = config.flySpeed;
    public static double AuraRange = config.auraRange;
    public static boolean StashEnabled = config.stashEnabled;
    public static boolean GlowESPEnabled = config.GlowESPEnabled;
    public static boolean RGBCamEnabled = config.rgbCamEnabled;
    public static boolean CMDSpammerEnabled = config.cmdSpammerEnabled;
    public static boolean particlesEnabled = config.particlesEnabled;
    public static boolean flightEnabled = config.flightEnabled;
    public static double aimbotRange = config.aimbotrange;
    public static boolean NoFogEnabled = config.NoFogEnabled;

    public static boolean TrueSightEnabled = config.TrueSightEnabled;

    private static final Aimbot aimbot = new Aimbot();
    private static final Aura entityAttacker = new Aura();
    private static boolean showModules = config.showModules;
    public static boolean NoBadEffect = config.noBadEffectEnabled;



    private static ClickGui clickGui;

    private static final long MORNING = 1000L;
    private static final long DAY = 6000L;
    private static final long SUNSET = 13000L;
    private static final long NIGHT = 1;


    public SallieMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(HealthOverlay.class);
        MinecraftForge.EVENT_BUS.register(Fly.class);
        MinecraftForge.EVENT_BUS.register(Aura.class);
        MinecraftForge.EVENT_BUS.register(Aimbot.class);
        MinecraftForge.EVENT_BUS.register(Spider.class);
        MinecraftForge.EVENT_BUS.register(Respawn.class);
        MinecraftForge.EVENT_BUS.register(new StashLogger());
        MinecraftForge.EVENT_BUS.register(this);
        rgbModule = new RGBCam();

    }


    private void doClientStuff(FMLClientSetupEvent event) {
        try {
            ClientRegistry.registerKeyBinding(teleportKey);
            ClientRegistry.registerKeyBinding(HClipKey);
            ClientRegistry.registerKeyBinding(teleportToggleKey);
            ClientRegistry.registerKeyBinding(toggleParticlesKey);
            ClientRegistry.registerKeyBinding(KillAura);
            ClientRegistry.registerKeyBinding(AimbotKey);
            ClientRegistry.registerKeyBinding(NukerKey);
            ClientRegistry.registerKeyBinding(StashKey);
            ClientRegistry.registerKeyBinding(CMDSpammer);
            ClientRegistry.registerKeyBinding(SpinKey);
            ClientRegistry.registerKeyBinding(toggleClickGuiKey);
            ClientRegistry.registerKeyBinding(StepKey);
            ClientRegistry.registerKeyBinding(FakeCreativeKey);
            ClientRegistry.registerKeyBinding(RGBCameraKeys);
            ClientRegistry.registerKeyBinding(NoBadEffectKey);
            ClientRegistry.registerKeyBinding(VelocityKey);
            ClientRegistry.registerKeyBinding(NoHurtCamKey);
            ClientRegistry.registerKeyBinding(FlightKey);
            ClientRegistry.registerKeyBinding(NoFall);
            ClientRegistry.registerKeyBinding(JesusKey);
            ClientRegistry.registerKeyBinding(AirJumpKey);
            ClientRegistry.registerKeyBinding(SpeedKey);
            ClientRegistry.registerKeyBinding(TrueSightKey);
            ClientRegistry.registerKeyBinding(AmbienceKey);
            ClientRegistry.registerKeyBinding(SpiderKey);

        } catch (Exception e) {
            LOGGER.error("Error during client setup: ", e);
        }
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        try {
            LOGGER.info("HELLO from server starting");
        } catch (Exception e) {
            LOGGER.error("Error during server starting: ", e);
        }
    }


    @SubscribeEvent
    public void onBlocksRegistry(RegistryEvent.Register<?> blockRegistryEvent) {
        try {
            LOGGER.info("HELLO from Register Block");
        } catch (Exception e) {
            LOGGER.error("Error during block registry: ", e);
        }
    }

    private static final Minecraft mc = Minecraft.getInstance();

    private static final float EXTENDED_REACH = 10.0F;

    private static boolean isTeleporting = false;
    private static double targetX, targetY, targetZ;
    private static double teleportDistance = 5.0;
    private static List<String> activeModules = new ArrayList<>();



    @SubscribeEvent
    public void onChat(ClientChatEvent event) {
        try {
            LOGGER.info("onChat method triggered.");
            if (Minecraft.getInstance().player == null) {
                LOGGER.warn("Player is null when processing chat.");
                return;
            }

            String message = event.getMessage();
            LOGGER.info("Received chat message: " + message);

            if (message.startsWith("?color ")) {
                String colorString = message.substring(7);

                if (colorString.matches("^[0-9a-fA-F]{6}$") || colorString.matches("^#[0-9a-fA-F]{6}$")) {
                    try {
                        if (!colorString.startsWith("#")) {
                            colorString = "#" + colorString;
                        }

                        Color color = Color.decode(colorString);
                        healthTextColor = color.getRGB();

                        Minecraft.getInstance().player.sendMessage(
                                new StringTextComponent("Health text color set to '" + colorString + "'"),
                                Minecraft.getInstance().player.getUniqueID()
                        );
                    } catch (NumberFormatException e) {
                        Minecraft.getInstance().player.sendMessage(
                                new StringTextComponent("Invalid color code. Please use a valid hex code."),
                                Minecraft.getInstance().player.getUniqueID()
                        );
                    }
                } else {
                    Minecraft.getInstance().player.sendMessage(
                            new StringTextComponent("Invalid color format. Please use a valid 6-character hex code (e.g., FF5733 or #FF5733)."),
                            Minecraft.getInstance().player.getUniqueID()
                    );
                }
                event.setCanceled(true);
            }
            if (message.startsWith("?aimbot ")) {
                String[] parts = message.split(" ");
                if (parts.length == 2) {
                    try {
                        double newRange = Double.parseDouble(parts[1]);
                        aimbotRange = newRange;
                        config.aimbotrange = aimbotRange;

                        ConfigManager.saveConfig(config);

                        Minecraft.getInstance().player.sendMessage(
                                new StringTextComponent("Aimbot range set to " + aimbotRange),
                                Minecraft.getInstance().player.getUniqueID()
                        );
                    } catch (NumberFormatException e) {
                        Minecraft.getInstance().player.sendMessage(
                                new StringTextComponent("Invalid range. Please enter a valid number."),
                                Minecraft.getInstance().player.getUniqueID()
                        );
                    }
                } else {
                    Minecraft.getInstance().player.sendMessage(
                            new StringTextComponent("Usage: ?aimbot <number>"),
                            Minecraft.getInstance().player.getUniqueID()
                    );
                }
                event.setCanceled(true);
            }

            if (message.equalsIgnoreCase("?autoteleport on")) {
                autoTeleportEnabled = true;
                config.autoTeleportEnabled = autoTeleportEnabled;

                ConfigManager.saveConfig(config);

                Minecraft.getInstance().player.sendMessage(
                        new StringTextComponent("Auto teleport on low health enabled."),
                        Minecraft.getInstance().player.getUniqueID()
                );
                event.setCanceled(true);
            }
            if (message.equalsIgnoreCase("?autoteleport on")) {
                autoTeleportEnabled = true;
                config.autoTeleportEnabled = autoTeleportEnabled;

                ConfigManager.saveConfig(config);

                Minecraft.getInstance().player.sendMessage(
                        new StringTextComponent("Auto teleport on low health enabled."),
                        Minecraft.getInstance().player.getUniqueID()
                );
                event.setCanceled(true);
            }

            if (message.startsWith("?step ")) {
                String[] parts = message.split(" ");
                if (parts.length == 2) {
                    try {
                        float newStepHeight = Float.parseFloat(parts[1]);

                        StepHeight = newStepHeight;
                        config.StepHeight = StepHeight;
                        ConfigManager.saveConfig(config);

                        Minecraft.getInstance().player.sendMessage(
                                new StringTextComponent("Step height set to " + newStepHeight + "."),
                                Minecraft.getInstance().player.getUniqueID()
                        );
                    } catch (NumberFormatException e) {
                        Minecraft.getInstance().player.sendMessage(
                                new StringTextComponent("Invalid step height. Please enter a valid number."),
                                Minecraft.getInstance().player.getUniqueID()
                        );
                    }
                } else {
                    Minecraft.getInstance().player.sendMessage(
                            new StringTextComponent("Usage: ?step <value>"),
                            Minecraft.getInstance().player.getUniqueID()
                    );
                }
                event.setCanceled(true);
            }


            if (message.startsWith("?killaura ")) {
                String[] parts = message.split(" ");
                if (parts.length == 2) {
                    try {
                        double newRange = Double.parseDouble(parts[1]);
                        AuraRange = newRange;
                        config.auraRange = AuraRange;

                        ConfigManager.saveConfig(config);

                        Minecraft.getInstance().player.sendMessage(
                                new StringTextComponent("Killaura range set to " + AuraRange),
                                Minecraft.getInstance().player.getUniqueID()
                        );
                    } catch (NumberFormatException e) {
                        Minecraft.getInstance().player.sendMessage(
                                new StringTextComponent("Invalid range. Please enter a valid number."),
                                Minecraft.getInstance().player.getUniqueID()
                        );
                    }
                } else {
                    Minecraft.getInstance().player.sendMessage(
                            new StringTextComponent("Usage: ?killaura <number>"),
                            Minecraft.getInstance().player.getUniqueID()
                    );
                }
                event.setCanceled(true);
            }


            if (message.equalsIgnoreCase("?autoteleport off")) {
                autoTeleportEnabled = false;
                config.autoTeleportEnabled = autoTeleportEnabled;

                ConfigManager.saveConfig(config);

                Minecraft.getInstance().player.sendMessage(
                        new StringTextComponent("Auto teleport on low health disabled."),
                        Minecraft.getInstance().player.getUniqueID()
                );
                event.setCanceled(true);
            }
            if (message.startsWith("?time ")) {
                String timeArg = message.substring(6).toUpperCase();
                try {
                    long newTime = Long.parseLong(timeArg);
                    timeOfDay = newTime % 24000;
                    mc.player.sendMessage(new StringTextComponent("Time set to " + newTime), mc.player.getUniqueID());
                } catch (NumberFormatException e) {
                    switch (timeArg) {
                        case "MORNING":
                            timeOfDay = MORNING;
                            break;
                        case "DAY":
                            timeOfDay = DAY;
                            break;
                        case "SUNSET":
                            timeOfDay = SUNSET;
                            break;
                        case "NIGHT":
                            timeOfDay = NIGHT;
                            break;
                        default:
                            mc.player.sendMessage(new StringTextComponent(TextFormatting.RED + "Invalid time! Use an integer or one of: MORNING, DAY, SUNSET, NIGHT."), mc.player.getUniqueID());
                            return;
                    }
                    config.timeOfDay = timeOfDay;

                    ConfigManager.saveConfig(config);

                    mc.player.sendMessage(new StringTextComponent("Time set to " + timeArg), mc.player.getUniqueID());
                }
                event.setCanceled(true);
            }

            if (message.equalsIgnoreCase("?discord")) {
                ConfigManager.saveConfig(config);

                Minecraft.getInstance().player.sendMessage(
                        new StringTextComponent("https://discord.gg/Dr9T8kA7uM to join !\n?discord open (to open the webpage directly)"),
                        Minecraft.getInstance().player.getUniqueID()
                );
                event.setCanceled(true);
            }

            if (message.equalsIgnoreCase("?discord open")) {
                try {
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        Desktop.getDesktop().browse(new URI("https://discord.gg/Dr9T8kA7uM"));
                    } else {
                        Minecraft.getInstance().player.sendMessage(
                                new StringTextComponent("Opening the browser is not supported on this platform."),
                                Minecraft.getInstance().player.getUniqueID()
                        );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Minecraft.getInstance().player.sendMessage(
                            new StringTextComponent("Failed to open the browser: " + e.getMessage()),
                            Minecraft.getInstance().player.getUniqueID()
                    );
                }
                event.setCanceled(true);
            }


            if (message.equalsIgnoreCase("?help")) {
                StringBuilder helpMessage = new StringBuilder();

                helpMessage.append(TextFormatting.GOLD + "**FurRiotMod Help**\n");
                helpMessage.append(TextFormatting.AQUA + "**Available Commands:**\n");

                helpMessage.append(TextFormatting.RED + "**COMBAT COMMANDS**\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?aimbot <number>: Change Aimbot range!! !\n");

                helpMessage.append(TextFormatting.BLUE + "**MOVEMENT COMMANDS**\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?vclip <number>: Set VCLIP height (default: 2.0)\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?hclip <number>: Set HCLIP height (default: 2.0)\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?goto <Coordinate>: Will make you go to the coordinate indicated\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?goto stop: To stop traveling\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?autoteleport <on/off>: Pretty logic\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?spin <1/2>: 1 is packet based, 2 is head based\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?speed <number>: To modify speed (default: 3.0)\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?step <number>: To modify speed (default: 3.0)\n");

                helpMessage.append(TextFormatting.GREEN + "**VISUAL COMMANDS**\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?color <hexCode>: Set health text color (e.g., ?color FF5733)\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?suffix <text>: Change the message suffix (e.g., ?suffix uwu)\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?suffixdisable: Disable the message suffix\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?suffixenable: Enable the message suffix (if disabled)\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?gradient <index>: Will make module go LGBTQ+!\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?gradientlist: Get all gradient colors!\n");

                helpMessage.append(TextFormatting.AQUA + "**UTILITY COMMANDS**\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?cmdspammer delay <ticks>: Change the delay between spamming commands (in ticks)\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?cmdspammer command <command>: Set the command to be spammed\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?discord : Give discord URL\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?friendslist : Get the friends list\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?friend add (username) : add user into the friend list\n");
                helpMessage.append(TextFormatting.YELLOW + "- ?friend remove (username) : Remove the user from friend\n");

                helpMessage.append(TextFormatting.AQUA + "**KEY COMMANDS**\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + teleportKey.getKey() + " TO VCLIP (USE ?vclip to modify)\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + HClipKey.getKey() + " TO HCLIP (USE ?hclip to modify)\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + teleportToggleKey.getKey() + " TO RandomTeleport\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + toggleParticlesKey.getKey() + " TO Laser ESP\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + AimbotKey.getKey() + " TO enable Aimbot\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + CMDSpammer.getKey() + " TO enable CMD Spammer\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + FlightKey.getKey() + " TO enable Fly\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + NoFall.getKey() + " TO enable NoFall\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + VelocityKey.getKey() + " TO enable Velocity\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + RGBCameraKeys.getKey() + " TO enable RGBCamera\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + CMDSpammer.getKey() + " TO enable Spin\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + NoHurtCamKey.getKey() + " TO enable NoHurtCam\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + NoBadEffectKey.getKey() + " TO enable NoBadEffect\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + FakeCreativeKey.getKey() + " TO enable NoBadEffect\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + JesusKey.getKey() + " TO enable Jesus\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + SpeedKey.getKey() + " TO enable Speed\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + StepKey.getKey() + " TO enable Step\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + SpiderKey.getKey() + " TO enable Spider\n");
                helpMessage.append(TextFormatting.YELLOW + "- PRESS " + AirJumpKey.getKey() + " TO enable AirJump\n");

                helpMessage.append(TextFormatting.AQUA + "**BONUS Stuff :**\n");
                helpMessage.append(TextFormatting.YELLOW + "**Automatically teleport if less than" + healthlimit + "\n");
                helpMessage.append(TextFormatting.YELLOW + "**On top left corner you can see your health\n");

                Minecraft.getInstance().player.sendMessage(new StringTextComponent(helpMessage.toString()), Minecraft.getInstance().player.getUniqueID());
                event.setCanceled(true);
            }

            if (message.equalsIgnoreCase("?follow stop")) {
                targetPlayerName = null;
                Minecraft.getInstance().player.sendMessage(
                        new StringTextComponent("Stopped following."),
                        Minecraft.getInstance().player.getUniqueID()
                );
                event.setCanceled(true);
            }

            if (message.startsWith("?follow ")) {
                String playerName = message.substring(8).trim();
                targetPlayerName = playerName;
                Minecraft.getInstance().player.sendMessage(
                        new StringTextComponent("Now following " + playerName),
                        Minecraft.getInstance().player.getUniqueID()
                );
                event.setCanceled(true);
            }


            if (message.startsWith("?crash")) {
                crasher = !crasher;
                config.crasher = crasher;

                ConfigManager.saveConfig(config);

                Minecraft.getInstance().player.sendMessage(
                        new StringTextComponent("Crash enabled"),
                        Minecraft.getInstance().player.getUniqueID()
                );
                event.setCanceled(true);
            }

            if (message.startsWith("?goto ")) {
                String[] parts = message.split(" ");
                if (parts.length == 4) {
                    try {
                        targetX = Double.parseDouble(parts[1]);
                        targetY = Double.parseDouble(parts[2]);
                        targetZ = Double.parseDouble(parts[3]);
                        isTeleporting = true;

                        Minecraft.getInstance().player.sendMessage(
                                new StringTextComponent("Teleporting to (" + targetX + ", " + targetY + ", " + targetZ + ") in steps."),
                                Minecraft.getInstance().player.getUniqueID()
                        );
                    } catch (NumberFormatException e) {
                        Minecraft.getInstance().player.sendMessage(
                                new StringTextComponent("Invalid coordinates. Please enter valid numbers."),
                                Minecraft.getInstance().player.getUniqueID()
                        );
                    }
                } else if (parts.length == 2 && parts[1].equalsIgnoreCase("stop")) {
                    if (isTeleporting) {
                        isTeleporting = false;
                        Minecraft.getInstance().player.sendMessage(
                                new StringTextComponent("Teleportation stopped."),
                                Minecraft.getInstance().player.getUniqueID()
                        );
                    } else {
                        Minecraft.getInstance().player.sendMessage(
                                new StringTextComponent("No active teleportation to stop."),
                                Minecraft.getInstance().player.getUniqueID()
                        );
                    }
                } else {
                    Minecraft.getInstance().player.sendMessage(
                            new StringTextComponent("Usage: ?goto <x> <y> <z> or ?goto stop"),
                            Minecraft.getInstance().player.getUniqueID()
                    );
                }
                event.setCanceled(true);
            }
            if (message.startsWith("?friend add ")) {
                String friendName = message.substring(12).trim();
                friendManager.addFriend(friendName);
                event.setCanceled(true);
            }

            if (message.startsWith("?friend remove ")) {
                String friendName = message.substring(15).trim();
                friendManager.removeFriend(friendName);
                event.setCanceled(true);
            }

            if (message.equalsIgnoreCase("?friends")) {
                StringBuilder friendList = new StringBuilder("Friends: ");
                for (String friend : friendManager.getFriends()) {
                    friendList.append(friend).append(", ");
                }

                if (friendList.length() > 8) {
                    friendList.setLength(friendList.length() - 2);
                } else {
                    friendList.append("None");
                }

                Minecraft.getInstance().player.sendMessage(new StringTextComponent(friendList.toString()), Minecraft.getInstance().player.getUniqueID());
                event.setCanceled(true);
            }

            if (message.startsWith("?suffix ")) {
                suffix = message.substring(8);
                config.suffix = suffix;

                ConfigManager.saveConfig(config);

                Minecraft.getInstance().player.sendMessage(new StringTextComponent("Suffix changed to '" + suffix + "'"), Minecraft.getInstance().player.getUniqueID());
                event.setCanceled(true);
            }




            if (message.startsWith("?vclip")) {
                try {
                    String heightString = message.split(" ")[1];
                    teleportHeight = Double.parseDouble(heightString);
                    config.teleportHeight = teleportHeight;

                    ConfigManager.saveConfig(config);

                    Minecraft.getInstance().player.sendMessage(new StringTextComponent("Teleport height set to '" + teleportHeight + "'"), Minecraft.getInstance().player.getUniqueID());
                } catch (NumberFormatException e) {
                    Minecraft.getInstance().player.sendMessage(new StringTextComponent("Invalid height. Please enter a valid number."), Minecraft.getInstance().player.getUniqueID());
                } catch (ArrayIndexOutOfBoundsException e) {
                    Minecraft.getInstance().player.sendMessage(new StringTextComponent("Please specify a height."), Minecraft.getInstance().player.getUniqueID());
                }
                event.setCanceled(true);
            }
            if (message.startsWith("?health")) {
                String[] parts = message.split(" ");
                if (parts.length > 1) {
                    try {
                        int healthLimit = Integer.parseInt(parts[1]);
                        config.healthlimit = healthLimit;
                        ConfigManager.saveConfig(config);

                        Minecraft.getInstance().player.sendMessage(new StringTextComponent("Health limit updated to: " + healthLimit), Minecraft.getInstance().player.getUniqueID());
                    } catch (NumberFormatException e) {
                        Minecraft.getInstance().player.sendMessage(new StringTextComponent("Invalid health limit: " + parts[1]), Minecraft.getInstance().player.getUniqueID());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        Minecraft.getInstance().player.sendMessage(new StringTextComponent("No health limit specified. Please provide a number."), Minecraft.getInstance().player.getUniqueID());
                    }
                } else {
                    Minecraft.getInstance().player.sendMessage(new StringTextComponent("Actual limit : " + healthlimit + "Usage: ?health <limit> tp change"), Minecraft.getInstance().player.getUniqueID());
                }

                event.setCanceled(true);

            }

            if (message.startsWith("?hclip")) {
                try {
                    String far = message.split(" ")[1];
                    HclipFar = Double.parseDouble(far);
                    config.hclipFar = HclipFar;

                    ConfigManager.saveConfig(config);

                    Minecraft.getInstance().player.sendMessage(new StringTextComponent("Hclip distance set to '" + HclipFar + "'"), Minecraft.getInstance().player.getUniqueID());
                } catch (NumberFormatException e) {
                    Minecraft.getInstance().player.sendMessage(new StringTextComponent("Invalid distance. Please enter a valid number."), Minecraft.getInstance().player.getUniqueID());
                } catch (ArrayIndexOutOfBoundsException e) {
                    Minecraft.getInstance().player.sendMessage(new StringTextComponent("Please specify a distance."), Minecraft.getInstance().player.getUniqueID());
                }
                event.setCanceled(true);
            }

            if (message.startsWith("?speed")) {
                try {
                    String[] parts = message.split(" ");

                    if (parts.length < 2) {
                        throw new IllegalArgumentException("Missing speed multiplier value.");
                    }

                    float speedMultiplier = Float.parseFloat(parts[1]);

                    SallieMod.SpeedMultiplier = speedMultiplier;
                    ConfigManager.saveConfig(config);

                    Minecraft.getInstance().player.sendMessage(
                            new StringTextComponent("Speed multiplier set to " + speedMultiplier),
                            Minecraft.getInstance().player.getUniqueID()
                    );

                } catch (NumberFormatException e) {
                    Minecraft.getInstance().player.sendMessage(
                            new StringTextComponent("Invalid number format! Please enter a valid number."),
                            Minecraft.getInstance().player.getUniqueID()
                    );
                } catch (IllegalArgumentException e) {
                    Minecraft.getInstance().player.sendMessage(
                            new StringTextComponent(e.getMessage()),
                            Minecraft.getInstance().player.getUniqueID()
                    );
                } catch (Exception e) {
                    Minecraft.getInstance().player.sendMessage(
                            new StringTextComponent("Invalid command format."),
                            Minecraft.getInstance().player.getUniqueID()
                    );
                }

                event.setCanceled(true);
            }


            if (message.startsWith("?tp ")) {
                String[] parts = message.split(" ");

                if (parts.length == 4) {
                    try {
                        double x = Double.parseDouble(parts[1]);
                        double y = Double.parseDouble(parts[2]);
                        double z = Double.parseDouble(parts[3]);

                        Minecraft.getInstance().player.connection.sendPacket(
                                new CPlayerPacket.PositionPacket(x, y, z, Minecraft.getInstance().player.isOnGround())
                        );

                        Minecraft.getInstance().player.setPosition(x, y, z);

                        Minecraft.getInstance().player.sendMessage(
                                new StringTextComponent("Teleported to (" + x + ", " + y + ", " + z + ")"),
                                Minecraft.getInstance().player.getUniqueID()
                        );
                    } catch (NumberFormatException e) {
                        Minecraft.getInstance().player.sendMessage(
                                new StringTextComponent("Invalid coordinates. Please enter valid numbers."),
                                Minecraft.getInstance().player.getUniqueID()
                        );
                    }
                }
                event.setCanceled(true);
            }
            if (message.equalsIgnoreCase("?gradientlist")) {
                String gradientList = ModuleOverlay.getGradientList();
                Minecraft.getInstance().player.sendMessage(
                        new StringTextComponent("Available Gradients:\n" + gradientList),
                        Minecraft.getInstance().player.getUniqueID()

                );

                event.setCanceled(true);

            }

            if (message.startsWith("?gradient ")) {
                String[] parts = message.split(" ");
                if (parts.length == 2) {
                    try {
                        int index = Integer.parseInt(parts[1]);
                        ModuleOverlay.setGradientIndex(index);
                        Minecraft.getInstance().player.sendMessage(
                                new StringTextComponent("Gradient changed to: " + ModuleOverlay.GRADIENT_NAMES[index]),
                                Minecraft.getInstance().player.getUniqueID()
                        );
                    } catch (NumberFormatException e) {
                        Minecraft.getInstance().player.sendMessage(
                                new StringTextComponent("Invalid index. Please enter a number."),
                                Minecraft.getInstance().player.getUniqueID()
                        );
                    } catch (ArrayIndexOutOfBoundsException e) {
                        Minecraft.getInstance().player.sendMessage(
                                new StringTextComponent("Index out of bounds. Use ?gradientlist to see available options."),
                                Minecraft.getInstance().player.getUniqueID()
                        );
                    }
                    event.setCanceled(true);
                }
            }

            if (message.startsWith("?spin")) {
                String[] parts = message.split(" ");
                if (parts.length == 2) {
                    try {
                        int mode = Integer.parseInt(parts[1]);

                        if (mode == 1 || mode == 2) {
                            rotationMode = mode;
                            config.rotationMode = rotationMode;

                            String modeMessage = (mode == 1) ? "Packet-based rotation" : "Head-based rotation";
                            Minecraft.getInstance().player.sendMessage(new StringTextComponent("Rotation mode changed to: " + modeMessage), Minecraft.getInstance().player.getUniqueID());
                        } else {
                            Minecraft.getInstance().player.sendMessage(new StringTextComponent("Invalid mode. Use 1 for packet-based, 2 for head-based."), Minecraft.getInstance().player.getUniqueID());
                        }
                    } catch (NumberFormatException e) {
                        Minecraft.getInstance().player.sendMessage(new StringTextComponent("Invalid input. Please enter 1 for packet-based or 2 for head-based rotation."), Minecraft.getInstance().player.getUniqueID());
                    }

                    event.setCanceled(true);
                }
            }
            if (message.startsWith("?suffixdisable")) {
                suffixDisabled = true;
                config.suffixDisabled = suffixDisabled;

                ConfigManager.saveConfig(config);
                Minecraft.getInstance().player.sendMessage(new StringTextComponent("Suffix disabled."), Minecraft.getInstance().player.getUniqueID());
                event.setCanceled(true);
            }
            if (message.startsWith("?noweather")) {
                noWeatherEnabled = !noWeatherEnabled;
                config.noWeatherEnabled = noWeatherEnabled;

                ConfigManager.saveConfig(config);

                String status = noWeatherEnabled ? "enabled" : "disabled";
                Minecraft.getInstance().player.sendMessage(new StringTextComponent("NoWeather " + status + "."), Minecraft.getInstance().player.getUniqueID());
                event.setCanceled(true);
            }

            if (message.toLowerCase().startsWith("?fly ")) {
                try {
                    String[] parts = message.split(" ");
                    if (parts.length == 2) {
                        float speed = Float.parseFloat(parts[1]);

                        if (speed < 0.01F || speed > 1000.0F) {
                            Minecraft.getInstance().player.sendMessage(new StringTextComponent("Speed must be between 0.01 and 1000.0!"), Minecraft.getInstance().player.getUniqueID());
                        } else {
                            FlySpeed = speed;

                            Minecraft.getInstance().player.sendMessage(new StringTextComponent("Fly speed changed to " + FlySpeed), Minecraft.getInstance().player.getUniqueID());
                        }
                    }

                } catch (NumberFormatException e) {
                    Minecraft.getInstance().player.sendMessage(new StringTextComponent("Invalid Fly Speed. Please enter a valid number."), Minecraft.getInstance().player.getUniqueID());
                }
                event.setCanceled(true);

            }


            if (message.startsWith("?suffixenable")) {
                suffixDisabled = false;
                Minecraft.getInstance().player.sendMessage(new StringTextComponent("Suffix enabled."), Minecraft.getInstance().player.getUniqueID());
                event.setCanceled(true);
            }

            if (!suffixDisabled && !message.startsWith("#") && !message.startsWith(".") && !message.startsWith("/")) {
                event.setMessage(message + suffix);
            }
        } catch (Exception e) {
            LOGGER.error("Error during chat event processing: ", e);
        }
    }


    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {


        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft mc = Minecraft.getInstance();


            if (mc.player != null && NoBadEffect) {
                List<Effect> effectsToRemove = new ArrayList<>();

                for (EffectInstance effectInstance : mc.player.getActivePotionEffects()) {
                    Effect effect = effectInstance.getPotion();
                    if (isNegativeEffect(effect)) {
                        effectsToRemove.add(effect);
                    }
                }

                for (Effect effect : effectsToRemove) {
                    mc.player.removePotionEffect(effect);
                }
            }
        }
    }

    private boolean isNegativeEffect(Effect effect) {
        return effect == Effects.BLINDNESS ||
                effect == Effects.POISON ||
                effect == Effects.WITHER ||
                effect == Effects.NAUSEA ||
                effect == Effects.SLOWNESS ||
                effect == Effects.WEAKNESS ||
                effect == Effects.HUNGER;

    }




    @SubscribeEvent
    public static void onRenderFog(EntityViewRenderEvent.RenderFogEvent event) {
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity && entity.getType() == EntityType.BAT) {
            World world = entity.getEntityWorld();
            PlayerEntity player = (PlayerEntity) event.getWorld().getClosestPlayer(entity.getPosX(), entity.getPosY(), entity.getPosZ(), 100, false);
            if (player != null) {
                entity.setInvisible(true);
                entity.setSilent(true);
            }
        }
    }




    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.world != null && particlesEnabled) {
            mc.world.getAllEntities().forEach(entity -> {
                if (entity instanceof ItemEntity) {
                    ItemEntity itemEntity = (ItemEntity) entity;
                    Vector3d itemPos = itemEntity.getPositionVec();
                    spawnParticles(mc.world, itemPos);
                }
            });
        }

    }


    private void spawnParticles(World world, Vector3d itemPos) {
        double particleY = itemPos.y;
        double targetHeight = 255.0;
        while (particleY < targetHeight) {
            world.addParticle(ParticleTypes.END_ROD,
                    itemPos.x,
                    particleY,
                    itemPos.z,
                    0, 0, 0);
            particleY += 0.5;
        }
    }

    private static final double MAX_TELEPORT_DISTANCE = 10.0;


    private static double approachZero(double current, double target) {
        if (Math.abs(current) < 1) return 0;
        double step = Math.signum(current) * teleportDistance;
        return Math.abs(current) < Math.abs(step) ? target : current - step;
    }
    private static double approachVerticalPosition(double currentY, double targetY) {
        if (currentY < targetY) {
            return Math.min(currentY + teleportDistance, targetY);
        } else {
            return Math.max(currentY - teleportDistance, targetY);
        }
    }

    private static boolean checkForCollision(double x, double y, double z) {
        int glitchCount = 0;

        if (Math.abs(Minecraft.getInstance().player.getPosX() - x) < 0.01) glitchCount++;
        if (Math.abs(Minecraft.getInstance().player.getPosY() - y) < 0.01) glitchCount++;
        if (Math.abs(Minecraft.getInstance().player.getPosZ() - z) < 0.01) glitchCount++;

        return glitchCount >= 3;
    }

    private static void followPlayer() {
        if (targetPlayerName != null) {
            Entity targetPlayer = findPlayerByName(targetPlayerName);
            if (targetPlayer != null) {
                double deltaX = targetPlayer.getPosX() - Minecraft.getInstance().player.getPosX();
                double deltaZ = targetPlayer.getPosZ() - Minecraft.getInstance().player.getPosZ();
                double deltaY = targetPlayer.getPosY() - Minecraft.getInstance().player.getPosY();
                double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ + deltaY * deltaY);

                if (distance > 5.0) {
                    double forwardX = deltaX / distance;
                    double forwardZ = deltaZ / distance;
                    double forwardY = deltaY / distance;

                    double newX = Minecraft.getInstance().player.getPosX() + forwardX * 5.0;
                    double newZ = Minecraft.getInstance().player.getPosZ() + forwardZ * 5.0;
                    double newY = Minecraft.getInstance().player.getPosY() + forwardY * 5.0;

                    Minecraft.getInstance().player.connection.sendPacket(new CPlayerPacket.PositionPacket(newX, newY, newZ, true));
                    Minecraft.getInstance().player.setPosition(newX, newY, newZ);
                }
            }
        }
    }
    private static void NoFalling() {
        ClientPlayerEntity players = net.minecraft.client.Minecraft.getInstance().player;
        if (players != null) {
            players.connection.sendPacket(new CPlayerPacket(true));
        }
    }
    private static Entity findPlayerByName(String name) {
        for (Entity entity : Minecraft.getInstance().world.getAllEntities()) {
            if (entity instanceof PlayerEntity && entity.getName().getString().equalsIgnoreCase(name)) {
                return entity;
            }
        }
        return null;
    }
    @Mod.EventBusSubscriber(modid = SallieMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientEventHandler {

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            try {
                if (event.phase == TickEvent.Phase.START) {
                    ClientPlayerEntity player = Minecraft.getInstance().player;
                    if (player != null && player.connection != null) {
                        float health = player.getHealth();
                        MatrixStack matrixStack = new MatrixStack();

                        handleHealthWarning(player, matrixStack, health);
                        handleAutoTeleport(player, health);

                        if (isTeleporting && player != null) {
                            double distance = Math.sqrt(
                                    Math.pow(player.getPosX() - targetX, 2) +
                                            Math.pow(player.getPosY() - targetY, 2) +
                                            Math.pow(player.getPosZ() - targetZ, 2)
                            );

                            if (distance > 25.0) {
                                float yaw = player.rotationYaw;
                                double radians = Math.toRadians(yaw);
                                double forwardX = -Math.sin(radians);
                                double forwardZ = Math.cos(radians);

                                double newX = player.getPosX() + forwardX * teleportDistance;
                                double newZ = player.getPosZ() + forwardZ * teleportDistance;

                                newX = approachZero(newX, targetX);
                                newZ = approachZero(newZ, targetZ);
                                double newY = approachVerticalPosition(player.getPosY(), targetY);

                                if (checkForCollision(newX, newY, newZ)) {
                                    newX -= forwardX * 10;
                                    newY += 20;
                                }

                                player.connection.sendPacket(new CPlayerPacket.PositionPacket(newX, newY, newZ, true));
                                player.setPosition(newX, newY, newZ);
                            } else {
                                isTeleporting = false;
                                player.sendMessage(
                                        new StringTextComponent("Reached destination at (" + targetX + ", " + targetY + ", " + targetZ + ")."),
                                        player.getUniqueID()
                                );
                            }
                        }

                        if (flightEnabled) {
                            Fly.applyMovement();
                        }

                        if (JesusEnabled) {
                            jesus.checkWaterMovement();
                        }

                        if (SpeedEnabled) {
                            Speed.applyMovement();
                        }



                        if (targetPlayerName != null) {
                            followPlayer();
                        }

                        if (randomTeleportEnabled) {
                            randomTeleport(player);
                        }

                        if (GlowESPEnabled) {
                            Glow.toggle();
                        }

                        if (killauraEnabled) {
                            entityAttacker.attackNearbyEntities();
                        }

                        if (nukerEnabled) {
                            Nuker.tick();
                        }



                        if (CMDSpammerEnabled) {
                            String command = ToucheCMD.replaceAll("/", "");

                            command = "/" + command;

                            player.sendChatMessage(command);
                        }


                        if (aimbotEnabled) {
                            aimbot.alwaysLookAtClosestPlayer();
                        }
                        if (AutoSprintEnabled) {
                            AutoSprint.tick();
                        }
                        if (player.hurtTime > 0 && velocity) {
                            player.maxHurtTime  = 0;
                            player.setMotion(0, player.getMotion().y, 0);


                        }

                        if (spin) {
                            handleSpinRotation(player);
                        }
                        if (fakeCreativeEnabled) {
                            MakeCreative(player);
                        }

                        if (TrueSightEnabled) {
                            TrueSight.revealEntities();
                        }

                        if (AirJumpEnabled) {
                            AirJump.performAirJump();
                        }

                        if (NoFogEnabled) {
                            NoFog.disableFog();
                        }

                        if (NoFallEnabled) {
                            NoFalling();
                            mc.player.fallDistance = 0.0F;

                        }

                        if (noWeatherEnabled) {
                            if (Minecraft.getInstance().world != null) {
                                Minecraft.getInstance().world.setThunderStrength(0);
                                Minecraft.getInstance().world.setRainStrength(0);
                            }
                        }

                    }
                }
            } catch (Exception e) {
                LOGGER.error("Error during client tick event: ", e);
            }
        }


        @SubscribeEvent
        public static void onKeyPress(InputEvent.KeyInputEvent event) {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            if (player == null) return;

            if (AimbotKey.isPressed()) {
                aimbotEnabled = !aimbotEnabled;
                config.aimbotEnabled = aimbotEnabled;
                player.sendMessage(getFormattedMessage(aimbotEnabled ? "Aimbot enabled." : "Aimbot disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }
            if (FakeCreativeKey.isPressed()) {
                fakeCreativeEnabled = !fakeCreativeEnabled;
                config.fakeCreativeEnabled = fakeCreativeEnabled;

                ConfigManager.saveConfig(config);

                if (!fakeCreativeEnabled) {
                    MakeSurvival(player);
                    player.sendMessage(getFormattedMessage("Fake Creative disabled."), player.getUniqueID());
                } else {
                    player.sendMessage(getFormattedMessage("Fake Creative enabled."), player.getUniqueID());
                }
            }

            if (NoFogKey.isPressed()) {
                NoFogEnabled = !NoFogEnabled;
                config.NoFogEnabled = NoFogEnabled;

                player.sendMessage(getFormattedMessage(NoFogEnabled ? "NoFog enabled." : "Nuker disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }

            if (RespawnKey.isPressed()) {
                RespawnEnabled = !RespawnEnabled;
                config.RespawnEnabled = RespawnEnabled;

                player.sendMessage(getFormattedMessage(NoFogEnabled ? "NoFog enabled." : "Nuker disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }
            if (GlowESPKey.isPressed()) {
                GlowESPEnabled = !GlowESPEnabled;
                config.GlowESPEnabled = GlowESPEnabled;

                player.sendMessage(getFormattedMessage(GlowESPEnabled ? "GlowESP enabled." : "GlowESP disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }
            if (SpiderKey.isPressed()) {
                SpiderEnabled = !SpiderEnabled;
                config.SpiderEnabled = SpiderEnabled;

                player.sendMessage(getFormattedMessage(SpiderEnabled ? "Spider enabled." : "Spider disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }

            if (AirJumpKey.isPressed()) {
                AirJumpEnabled = !AirJumpEnabled;
                config.AirJumpEnabled = AirJumpEnabled;

                player.sendMessage(getFormattedMessage(AirJumpEnabled ? "AirJump enabled." : "AirJump disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }
            if (AmbienceKey.isPressed()) {
                AmbienceEnabled = !AmbienceEnabled;
                config.AmbienceEnabled = AmbienceEnabled;

                player.sendMessage(getFormattedMessage(AmbienceEnabled ? "Ambience enabled." : "Ambience disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }
            if (TrueSightKey.isPressed()) {
                TrueSightEnabled = !TrueSightEnabled;
                config.TrueSightEnabled = TrueSightEnabled;

                player.sendMessage(getFormattedMessage(TrueSightEnabled ? "TrueSight enabled." : "TrueSight disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }

            if (NukerKey.isPressed()) {
                nukerEnabled = !nukerEnabled;
                config.nukerEnabled = nukerEnabled;

                player.sendMessage(getFormattedMessage(nukerEnabled ? "Nuker enabled." : "Nuker disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }

            if (toggleClickGuiKey.isPressed()) {
                ClickGui clickGui = new ClickGui();
                clickGui.toggle();

            }
            if (SpinKey.isPressed()) {
                spin = !spin;
                config.spin = spin;
                player.sendMessage(getFormattedMessage(spin ? "Spin enabled." : "Spin disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }

            if (JesusKey.isPressed()) {
                JesusEnabled = !JesusEnabled;
                config.Jesus = JesusEnabled;
                player.sendMessage(getFormattedMessage(JesusEnabled ? "Jesus enabled." : "Jesus disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }

            if (SpeedKey.isPressed()) {
                SpeedEnabled = !SpeedEnabled;
                config.SpeedEnabled = SpeedEnabled;
                player.sendMessage(getFormattedMessage(SpeedEnabled ? "Speed enabled." : "Speed disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }

            if (VelocityKey.isPressed()) {
                velocity = !velocity;
                config.velocity = velocity;

                player.sendMessage(getFormattedMessage(velocity ? "Velocity enabled." : "Velocity disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }

            if (AutoSprintKey.isPressed()) {
                AutoSprintEnabled = !AutoSprintEnabled;
                config.AutoSprintEnabled = AutoSprintEnabled;
                player.sendMessage(getFormattedMessage(AutoSprintEnabled ? "AutoSprint enabled." : "AutoSprint disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }

            if (StepKey.isPressed()) {
                StepEnabled = !StepEnabled;
                config.StepEnabled = StepEnabled;
                player.sendMessage(getFormattedMessage(StepEnabled ? "Step enabled." : "Step disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }

            if (FlightKey.isPressed()) {
                flightEnabled = !flightEnabled;
                config.flightEnabled = flightEnabled;
                player.sendMessage(getFormattedMessage(flightEnabled ? "Flight enabled." : "Flight disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }

            if (CMDSpammer.isPressed()) {
                CMDSpammerEnabled = !CMDSpammerEnabled;
                config.cmdSpammerEnabled = CMDSpammerEnabled;
                player.sendMessage(getFormattedMessage(CMDSpammerEnabled ? "CMDSpammer enabled" : "CMDSpammer disabled"), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }

            if (KillAura.isPressed()) {
                killauraEnabled = !killauraEnabled;
                config.killauraEnabled = killauraEnabled;

                player.sendMessage(getFormattedMessage(killauraEnabled ? "Killaura enabled." : "Killaura disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }

            if (teleportKey.isPressed()) {
                double newY = player.getPosY() + SallieMod.teleportHeight;
                player.setPosition(player.getPosX(), newY, player.getPosZ());
                config.teleportHeight = newY;

                ConfigManager.saveConfig(config);
            }

            if (NoFall.isPressed()) {
                NoFallEnabled = !NoFallEnabled;
                config.NoFall = NoFallEnabled;
                ConfigManager.saveConfig(config);
            }

            if (toggleParticlesKey.isPressed()) {
                particlesEnabled = !particlesEnabled;
                config.particlesEnabled = particlesEnabled;
                player.sendMessage(getFormattedMessage(particlesEnabled ? "Item Laser enabled." : "Item Laser disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }

            if (HClipKey.isPressed()) {
                float yaw = mc.player.rotationYaw;
                double radians = Math.toRadians(yaw);
                double forwardX = -Math.sin(radians);
                double forwardZ = Math.cos(radians);
                double newX = mc.player.getPosX() + forwardX * SallieMod.HclipFar;
                double newZ = mc.player.getPosZ() + forwardZ * SallieMod.HclipFar;
                double newY = mc.player.getPosY();
                CPlayerPacket.PositionPacket positionPacket = new CPlayerPacket.PositionPacket(newX, newY, newZ, true);
                mc.player.connection.sendPacket(positionPacket);
                mc.player.setPosition(newX, newY, newZ);
            }

            if (teleportToggleKey.isPressed()) {
                randomTeleportEnabled = !randomTeleportEnabled;
                config.randomTeleportEnabled = randomTeleportEnabled;
                player.sendMessage(getFormattedMessage(randomTeleportEnabled ? "RandomTeleport enabled." : "RandomTeleport disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }

            if (NoHurtCamKey.isPressed()) {
                NoHurtCamEnabled = !NoHurtCamEnabled;
                config.noHurtCamEnabled = NoHurtCamEnabled;
                player.sendMessage(getFormattedMessage(NoHurtCamEnabled ? "NoHurtCam enabled." : "NoHurtCam disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }
            if (StashKey.isPressed()) {
                StashEnabled = !StashEnabled;
                config.stashEnabled = StashEnabled;
                player.sendMessage(getFormattedMessage(StashEnabled ? "Stash enabled." : "Stash disabled."), player.getUniqueID());
                ConfigManager.saveConfig(config);
            }
        }

        private static StringTextComponent getFormattedMessage(String message) {
            return new StringTextComponent("PuellaMagi: " + message);
        }}


    private static void handleSpinRotation(ClientPlayerEntity player) {
        currentYaw += yawIncrement;
        if (currentYaw >= 360) {
            currentYaw -= 360;
        }
        if (rotationMode == 1) {
            float newYaw = player.rotationYaw + currentYaw;
            float newPitch = player.rotationPitch;
            player.connection.sendPacket(new CPlayerPacket.RotationPacket(newYaw, newPitch, true));
        } else if (rotationMode == 2) {
            player.rotationYawHead = player.rotationYaw + currentYaw;
            player.rotationPitch = player.rotationPitch;
        }
    }

    private static void handleHealthWarning(ClientPlayerEntity player, MatrixStack matrixStack, float health) {
        if (health < config.healthlimit) {
            String alertMessage = "ALERT YOU CAN GET ONE-SHOT";
            int alertColor = 0x8B0000;
            int x = 20;
            int y = 10;
            Minecraft.getInstance().fontRenderer.drawStringWithShadow(matrixStack, alertMessage, x, y, alertColor);
        }
    }


    private static void handleAutoTeleport(ClientPlayerEntity player, float health) {
        ResourceLocation dimension = player.world.getDimensionKey().getLocation();
        if (autoTeleportEnabled && dimension.equals(World.OVERWORLD.getLocation())) {
            if (health < healthlimit && !hasTeleported) {
                player.sendChatMessage("/team home");
                hasTeleported = true;
                ConfigManager.saveConfig(config);


            } else if (health >= healthlimit) {
                hasTeleported = false;
            }
        }
    }


    private static void MakeCreative(ClientPlayerEntity player) {
        Minecraft.getInstance().playerController.setGameType(GameType.CREATIVE);

    }


    private static void MakeSurvival(ClientPlayerEntity player) {
        Minecraft.getInstance().playerController.setGameType(GameType.SURVIVAL);

    }


    private static void randomTeleport(ClientPlayerEntity player) {
        double randomValue = Math.random() * 100;

        if (randomValue < 47) {
            double newX = player.getPosX() + (Math.random() < 0.5 ? 10 : -10);
            player.connection.sendPacket(new CPlayerPacket.PositionPacket(newX, player.getPosY(), player.getPosZ(), player.isOnGround()));
        } else if (randomValue < 94) {
            double newZ = player.getPosZ() + (Math.random() < 0.5 ? 10 : -10);
            player.connection.sendPacket(new CPlayerPacket.PositionPacket(player.getPosX(), player.getPosY(), newZ, player.isOnGround()));
        } else if (randomValue < 100) {
            double newY = player.getPosY() + (Math.random() < 0.5 ? 10 : -10);
            player.connection.sendPacket(new CPlayerPacket.PositionPacket(player.getPosX(), newY, player.getPosZ(), player.isOnGround()));
        }
    }




}