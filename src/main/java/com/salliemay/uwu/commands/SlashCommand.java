package com.salliemay.uwu.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import java.awt.Color;
import com.salliemay.uwu.SallieMod;

@Mod.EventBusSubscriber
public class SlashCommand {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();

        dispatcher.register(
                Commands.literal("color")
                        .then(Commands.argument("color", StringArgumentType.word())
                                .executes(context -> {
                                    String colorString = context.getArgument("color", String.class);

                                    if (colorString.matches("^[0-9a-fA-F]{6}$") || colorString.matches("^#[0-9a-fA-F]{6}$")) {
                                        try {
                                            // Prepend # if not present
                                            if (!colorString.startsWith("#")) {
                                                colorString = "#" + colorString;
                                            }

                                            Color color = Color.decode(colorString);
                                            SallieMod.healthTextColor = color.getRGB();

                                            Minecraft.getInstance().player.sendMessage(
                                                    new StringTextComponent("Health text color set to '" + colorString + "'"),
                                                    Minecraft.getInstance().player.getUniqueID());


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

                                    return 0;
                                })
                        )
        );
    }
}
