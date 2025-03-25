package com.chivasss.pocket_dimestions.mixin;

import com.chivasss.pocket_dimestions.util.PlayerUtils;
import com.chivasss.pocket_dimestions.weather.WeatherManager;
import com.chivasss.pocket_dimestions.weather.WeatherType;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraShakeMixin {


    @Inject(method = "Lnet/minecraft/client/Camera;setup(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;ZZF)V", at = @At("TAIL"))
    private void injectCameraShake(BlockGetter pLevel, Entity pEntity, boolean pDetached, boolean pThirdPersonReverse, float pPartialTick, CallbackInfo ci) {
        Player player = Minecraft.getInstance().player;
        BlockPos blockPos = player.blockPosition();
        if (WeatherManager.getActiveWeather(WeatherType.EMISSION) == null) return;
        if (WeatherManager.getActiveWeather(WeatherType.EMISSION).getWeatherType() == WeatherType.EMISSION) {
            int blocksAbove = PlayerUtils.blocksAbove(player, 3);


            double shakeAmount = (double) Math.abs(blocksAbove - 3) / 9;
            double offsetX = (Math.random() - 0.5) * shakeAmount;
            double offsetY = (Math.random() - 0.5) * shakeAmount;
            double offsetZ = (Math.random() - 0.5) * shakeAmount;

            Camera camera = (Camera) (Object) this;
            camera.setPosition(camera.getPosition().x + offsetX, camera.getPosition().y + offsetY, camera.getPosition().z + offsetZ);
            camera.setRotation((float) (camera.getYRot() + offsetX*10), (float) ( camera.getXRot() + offsetY*10));
        }
    }

    private static float approach(float current, float target, float delta) {
        if (current < target) {
            return Math.min(current + delta, target);
        } else {
            return Math.max(current - delta, target);
        }
    }
}