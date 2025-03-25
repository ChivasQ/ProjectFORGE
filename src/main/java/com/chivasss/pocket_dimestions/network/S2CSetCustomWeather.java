package com.chivasss.pocket_dimestions.network;

import com.chivasss.pocket_dimestions.weather.WeatherManager;
import com.chivasss.pocket_dimestions.weather.WeatherType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CSetCustomWeather {
    private final WeatherType weather;

    public S2CSetCustomWeather(WeatherType weather) {
        this.weather = weather;
    }

    public S2CSetCustomWeather(FriendlyByteBuf byteBuf) {
        this.weather = byteBuf.readEnum(WeatherType.class);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        Level level = player.level();
        if (level == null) return;
        Vec3 vec3 = player.getEyePosition();
//        if (weather == WeatherType.EMISSION) {
//            WeatherManager.setActive(true);
//        } else {
//            WeatherManager.setActive(false);
//        }
        for (int i = 0; i < 24; i++) {
            Vec3 vec2 = new Vec3(1,0,0).yRot(i*15);
            Vec3 vec31 = vec2.add(vec3);
            level.addParticle(ParticleTypes.CLOUD, vec31.x, vec31.y, vec31.z, vec2.x/5, vec2.y/5,vec2.z/5);
        }
        //ShaderExample.applyDistortion();
    }

    public void encode(FriendlyByteBuf byteBuf) {
        byteBuf.writeEnum(weather);
    }
}
