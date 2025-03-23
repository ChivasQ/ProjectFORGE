package com.chivasss.pocket_dimestions.weather;


import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WeatherEventHandler {

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (!event.level.isClientSide()) {

            if (shouldStartMyWeather(event.level)) {
                startMyCustomWeather(event.level);
            }
        }
    }

    private static boolean shouldStartMyWeather(Level world) {
        System.out.println("ha");
        return Math.random() < 0.01;
    }

    private static void startMyCustomWeather(Level world) {
        System.out.println("Starting custom weather event!");

        // Или добавляем частицы или что-то ещё
    }
}