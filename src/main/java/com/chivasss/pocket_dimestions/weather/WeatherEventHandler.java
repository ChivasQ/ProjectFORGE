package com.chivasss.pocket_dimestions.weather;


import com.chivasss.pocket_dimestions.network.PacketHandler;
import com.chivasss.pocket_dimestions.network.S2CBreakBlockPacket;
import com.chivasss.pocket_dimestions.network.S2CSetCustomWeather;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class WeatherEventHandler {

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (!event.level.isClientSide()
                && event.side == LogicalSide.SERVER
                && event.phase == TickEvent.Phase.END
                && event.level.dimension() == Level.OVERWORLD) {

                WeatherManager.update(event.level);

        }
    }

    private static boolean shouldStartMyWeather(Level world) {
        System.out.println("ha");
        return Math.random() < 0.01;
    }

    private static void startMyCustomWeather(Level world) {
        System.out.println("Starting custom weather event!");
        S2CSetCustomWeather packet = new S2CSetCustomWeather(WeatherType.EMISSION);
        PacketHandler.sendToAllClients(packet);
    }

    private static void stopMyCustomWeather(Level world) {
        System.out.println("Stopping custom weather event!");
        S2CSetCustomWeather packet = new S2CSetCustomWeather(WeatherType.CLEAR);
        PacketHandler.sendToAllClients(packet);
    }
}