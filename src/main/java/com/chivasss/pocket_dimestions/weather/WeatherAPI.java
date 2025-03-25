package com.chivasss.pocket_dimestions.weather;


import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.server.ServerLifecycleHooks;

public class WeatherAPI {

    public static void setWeather(WeatherType type, int duration) {
        ServerLevel level = ServerLifecycleHooks.getCurrentServer().overworld();
        if (level == null) return;

        switch (type) {
            case CLEAR:
                level.setWeatherParameters(duration, 0, false, false);
                break;
            case RAIN:
                level.setWeatherParameters(0, duration, true, false);
                break;
            case THUNDER:
                level.setWeatherParameters(0, duration, true, true);
                break;
            case EMISSION:

                break;
        }
    }
}