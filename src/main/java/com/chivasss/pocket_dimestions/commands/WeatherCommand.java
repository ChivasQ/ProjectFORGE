package com.chivasss.pocket_dimestions.commands;

import com.chivasss.pocket_dimestions.weather.Emission;
import com.chivasss.pocket_dimestions.weather.WeatherManager;
import com.chivasss.pocket_dimestions.weather.WeatherType;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class WeatherCommand {
    private static final String PREFIX = "weather";
    public WeatherCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        // weather set [duration]
        dispatcher.register(Commands.literal(PREFIX)
                .then(Commands.literal("set")
                        .then(Commands.argument("duration", IntegerArgumentType.integer())
                .executes((context) -> setWeather(context.getSource(), IntegerArgumentType.getInteger(context, "duration"))))));
        // weather clear1
        dispatcher.register(Commands.literal(PREFIX)
                .then(Commands.literal("clear1")
                                .executes((context) -> setClear(context.getSource()))));
    }

    private int setWeather(CommandSourceStack source, int duration) {
        source.sendSystemMessage(Component.literal("set Emission"));
        WeatherManager.startWeather(source.getLevel(), new Emission(duration));
        return 1;
    }

    private int setClear(CommandSourceStack source) {
        source.sendSystemMessage(Component.literal("set Clear"));
        if (WeatherManager.getActiveWeather(WeatherType.EMISSION) == null) return 1;
        WeatherManager.stopWeather(source.getLevel(), WeatherManager.getActiveWeather(WeatherType.EMISSION));
        return 1;
    }
}
