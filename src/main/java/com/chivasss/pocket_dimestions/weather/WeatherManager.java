package com.chivasss.pocket_dimestions.weather;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class WeatherManager {
    private static final Map<WeatherType, Weather> activeWeather = new HashMap<>();

    public static void startWeather(Level level, Weather weather) {
        if (!activeWeather.containsKey(weather.getWeatherType())) {
            weather.start(level);
            activeWeather.put(weather.getWeatherType(), weather);
        }
    }

    public static void update(Level level) {
        Iterator<Map.Entry<WeatherType, Weather>> iterator = activeWeather.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<WeatherType, Weather> entry = iterator.next();
            Weather weather = entry.getValue();

            weather.update(level);

            if (weather.getRemainingTime() <= 0) {
                weather.stop(level);
                iterator.remove();
            }
        }
    }

    public static void stopWeather(Level level, Weather weather) {
        weather.stop(level);
        activeWeather.remove(weather.getWeatherType());
    }

    public static Weather getActiveWeather(WeatherType type) {
        try{
            return activeWeather.get(type);
        } catch (Exception e) {
            return null;
        }


    }

    public void resetAllWeather(Level level) {
        activeWeather.values().forEach(weather -> weather.stop(level));
        activeWeather.clear();
    }
}