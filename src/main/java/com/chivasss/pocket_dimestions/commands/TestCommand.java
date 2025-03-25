package com.chivasss.pocket_dimestions.commands;

import com.chivasss.pocket_dimestions.weather.Emission;
import com.chivasss.pocket_dimestions.weather.WeatherManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class TestCommand {
    public TestCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("weatherapi")
                .then(Commands.literal("setweather")
                        .then(Commands.argument("duration", IntegerArgumentType.integer())
                .executes((context) -> {
            return setWeather(context.getSource(), IntegerArgumentType.getInteger(context, "duration"));
        }))));
    }

    private int setWeather(CommandSourceStack source, int duration) throws CommandSyntaxException {
        source.sendSystemMessage(Component.literal("ikgbk;ljb;"));
        WeatherManager.startWeather(source.getLevel(), new Emission(duration));
        return 1;
    }
}
