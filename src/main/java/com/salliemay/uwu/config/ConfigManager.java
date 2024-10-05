package com.salliemay.uwu.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    private static final String CONFIG_FILE = "config.json";
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
        public double auraRange = 15.0;
        public boolean stashEnabled = false;
        public boolean hasTeleported = false;
        public boolean rgbCamEnabled = false;
        public boolean cmdSpammerEnabled = false;
        public boolean particlesEnabled = false;
        public boolean suffixDisabled = true;
        public boolean showModules = true;
        public boolean NoFall = false;
        public int rotationMode = 1;
        public int aimbotrange = 15;
        public int healthlimit = 1000;
        public String suffix = " | PuellaMagi | Trans right !";
        public int commandDelay = 1;
    }

    public static Config loadConfig() {
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists()) {
            System.out.println("Config file not found. Creating a new one.");
            saveConfig(new Config());
        }

        try (FileReader reader = new FileReader(configFile)) {
            return GSON.fromJson(reader, Config.class);
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
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
