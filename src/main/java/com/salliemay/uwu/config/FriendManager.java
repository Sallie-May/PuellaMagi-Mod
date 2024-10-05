package com.salliemay.uwu.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FriendManager {
    private static final String FRIENDS_FILE = "friends.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Set<String> friends = new HashSet<>();

    public FriendManager() {
        loadFriends();
    }

    public void addFriend(String username) {
        if (friends.add(username)) {
            saveFriends();
            Minecraft.getInstance().player.sendMessage(new StringTextComponent(username + " has been added as a friend."), Minecraft.getInstance().player.getUniqueID());
        } else {
            Minecraft.getInstance().player.sendMessage(new StringTextComponent(username + " is already in your friends list."), Minecraft.getInstance().player.getUniqueID());
        }
    }

    public void removeFriend(String username) {
        if (friends.remove(username)) {
            saveFriends();
            Minecraft.getInstance().player.sendMessage(new StringTextComponent(username + " has been removed from your friends."), Minecraft.getInstance().player.getUniqueID());
        } else {
            Minecraft.getInstance().player.sendMessage(new StringTextComponent(username + " is not in your friends list."), Minecraft.getInstance().player.getUniqueID());
        }
    }

    public Set<String> getFriends() {
        loadFriends();
        return friends;
    }

    private void loadFriends() {
        File file = new File(FRIENDS_FILE);
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                String[] loadedFriends = GSON.fromJson(reader, String[].class);
                if (loadedFriends != null) {
                    friends.clear();
                    for (String friend : loadedFriends) {
                        friends.add(friend);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFriends() {
        try (FileWriter writer = new FileWriter(FRIENDS_FILE)) {
            GSON.toJson(friends.toArray(new String[0]), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
