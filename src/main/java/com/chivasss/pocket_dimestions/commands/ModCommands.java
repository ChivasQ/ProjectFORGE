package com.chivasss.pocket_dimestions.commands;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.command.ConfigCommand;

public class ModCommands {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        new WeatherCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}
