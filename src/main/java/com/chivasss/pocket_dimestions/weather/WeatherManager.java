package com.chivasss.pocket_dimestions.weather;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class WeatherManager {
    private static final Map<WeatherType, Weather> activeWeather = new ConcurrentHashMap<>();

    public static void startWeather(Level level, Weather weather) {
        if (!activeWeather.containsKey(weather.getWeatherType())) {
            weather.start(level);
            activeWeather.put(weather.getWeatherType(), weather);
        }
    }

    public static void update(Level level) {
        activeWeather.values().forEach(weather -> {
            weather.update(level);
            if (weather.getRemainingTime() <= 0) {
                stopWeather(level, weather);
            }
        });
    }

    public static void stopWeather(Level level, Weather weather) {
        weather.stop(level);
        activeWeather.remove(weather.getWeatherType());
    }

    public Weather getActiveWeather(WeatherType type) {
        return activeWeather.get(type);
    }

    public void resetAllWeather(Level level) {
        activeWeather.values().forEach(weather -> weather.stop(level));
        activeWeather.clear();
    }
}