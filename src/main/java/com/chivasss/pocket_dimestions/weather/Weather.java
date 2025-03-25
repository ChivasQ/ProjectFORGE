package com.chivasss.pocket_dimestions.weather;

import com.chivasss.pocket_dimestions.network.PacketHandler;
import com.chivasss.pocket_dimestions.network.S2CSetCustomWeather;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class Weather {
    WeatherType weatherType;
    int duration;
    private int remainingTime;

    public Weather(int duration, WeatherType type) {
        this.duration = duration;
        this.weatherType = type;
        this.remainingTime = duration;
    }

    public void start(Level level) {
        Minecraft.getInstance().player.sendSystemMessage(Component.literal(weatherType.name() + Minecraft.getInstance().player.getUUID() + " * " + duration));
        S2CSetCustomWeather packet = new S2CSetCustomWeather(weatherType);
        PacketHandler.sendToAllClients(packet);
    }

    public void update(Level level) {
        if (remainingTime > 0) {
            tick(level);
            remainingTime--;
        } else {
            stop(level);
        }
    }

    public void tick(Level level) {

    }

    public void stop(Level level) {
        Minecraft.getInstance().player.sendSystemMessage(Component.literal("End of: " + weatherType.name() + Minecraft.getInstance().player.getUUID()));
        S2CSetCustomWeather packet = new S2CSetCustomWeather(WeatherType.CLEAR);
        PacketHandler.sendToAllClients(packet);
    }

    public WeatherType getWeatherType() {
        if (weatherType == null) return WeatherType.CLEAR;
        return weatherType;
    }

    public int getRemainingTime() {
        return remainingTime;
    }
}
