package com.chivasss.pocket_dimestions.weather;


import com.chivasss.pocket_dimestions.network.PacketHandler;
import com.chivasss.pocket_dimestions.network.S2CBreakBlockPacket;
import com.chivasss.pocket_dimestions.network.S2CSetCustomWeather;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WeatherEventHandler {
    static int c = 0;
    static boolean hasStarted = false;
    static int duration = 0;
    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (!event.level.isClientSide()) {
            if (c >= 20) {
                c = 0;
                if (shouldStartMyWeather(event.level)) {
                    hasStarted = true;
                }
                if (hasStarted) {
                    startMyCustomWeather(event.level);
                    duration++;
                }
                if (duration > 100) {
                    stopMyCustomWeather(event.level);
                    hasStarted = false;
                    duration = 0;
                }
            } else {
                c++;
            }
        }
    }

    private static boolean shouldStartMyWeather(Level world) {
        System.out.println("ha");
        return Math.random() < 0.01;
    }

    private static void startMyCustomWeather(Level world) {
        System.out.println("Starting custom weather event!");
        S2CSetCustomWeather packet = new S2CSetCustomWeather(S2CSetCustomWeather.CustomWeather.EMISSION);
        PacketHandler.sendToAllClients(packet);
    }

    private static void stopMyCustomWeather(Level world) {
        System.out.println("Stopping custom weather event!");
        S2CSetCustomWeather packet = new S2CSetCustomWeather(S2CSetCustomWeather.CustomWeather.NONE);
        PacketHandler.sendToAllClients(packet);
    }
}