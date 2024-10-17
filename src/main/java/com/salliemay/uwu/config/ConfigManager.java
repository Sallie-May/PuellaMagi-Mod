package com.salliemay.uwu.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    private static final String CONFIG_FILE = "config.owo";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static class Config {
        public boolean randomTeleportEnabled = false;
        public boolean killauraEnabled = false;
        public boolean autoTeleportEnabled = true;
        public boolean aimbotEnabled = false;
        public boolean nukerEnabled = false;
        public boolean crasher = false;
        public boolean spin = false;
        public boolean fakeCreativeEnabled = false;
        public boolean noWeatherEnabled = true;
        public boolean noBadEffectEnabled = true;
        public boolean velocity = false;
        public boolean noHurtCamEnabled = true;
        public boolean flightEnabled = false;
        public double teleportHeight = 2.0;
        public double hclipFar = 2.0;
        public float flySpeed = 3.0f;
        public float StepHeight = 5.0f;
        public float SpeedMultiplier = 2.0f;
        public boolean SpeedEnabled = false;
        public boolean TrueSightEnabled = true;

        public double auraRange = 15.0;
        public boolean stashEnabled = false;
        public boolean hasTeleported = false;
        public boolean rgbCamEnabled = false;
        public boolean cmdSpammerEnabled = false;
        public boolean particlesEnabled = false;
        public boolean StepEnabled = true;
        public boolean suffixDisabled = true;
        public boolean showModules = true;
        public boolean NoFall = true;
        public long timeOfDay = 0;
        public boolean FullBrightEnabled = true;

        public boolean TargetHUDEnabled = false;

        public boolean AutoSprintEnabled = true;
        public boolean GlowESPEnabled = true;
        public boolean AmbienceEnabled = false;
        public boolean AirJumpEnabled = false;
        public boolean isHeadLessEnabled = false;
        public boolean SpiderEnabled = false;
        public boolean RespawnEnabled = false;
        public boolean NoFogEnabled = true;
        public boolean HitBoxEnabled = true;

        public boolean Jesus = false;
        public boolean SessionStatsEnabled = false;
        public int rotationMode = 1;
        public double aimbotrange = 15;
        public int healthlimit = 1000;
        public String suffix = " | PuellaMagi | Trans right !";
        public int commandDelay = 1;
    }

    public static Config loadConfig() {
        File configFile = new File(CONFIG_FILE);

        System.out.println("Looking for config at: " + configFile.getAbsolutePath());

        if (!configFile.exists()) {
            System.out.println("Config file not found. Creating a new one.");
            saveConfig(new Config());
        }

        try (FileReader reader = new FileReader(configFile)) {
            char[] buffer = new char[(int) configFile.length()];
            reader.read(buffer);
            reader.read(buffer);
            System.out.println("Config file contents: " + new String(buffer));

            return GSON.fromJson(new FileReader(configFile), Config.class);
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
            return new Config();
        } catch (Exception e) {
            System.err.println("Failed to parse config: " + e.getMessage());
            return new Config();
        }
    }

    public static void saveConfig(Config config) {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(config, writer);
            System.out.println("Config saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save config: " + e.getMessage());
        }
    }
}
