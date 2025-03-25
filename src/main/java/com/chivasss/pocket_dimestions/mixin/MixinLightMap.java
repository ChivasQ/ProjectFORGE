package com.chivasss.pocket_dimestions.mixin;

import com.chivasss.pocket_dimestions.weather.WeatherManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LightTexture.class)
public class MixinLightMap {
    @Inject(method = "Lnet/minecraft/client/renderer/LightTexture;updateLightTexture(F)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/LightTexture;blockLightRedFlicker:F"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void doOurLightMap(float partialTicks, CallbackInfo ci, ClientLevel clientLevel, float v, float v1, float v2, float v3, float v4, float v5, float v6, Vector3f skyVector) {
        Player player = Minecraft.getInstance().player;
        BlockPos blockPos = player.blockPosition();
        if (WeatherManager.isActive()) {
            int blocksAbove = 0;
            for (int y = blockPos.getY(); y < player.level().getMaxBuildHeight(); y++) {
                if (clientLevel.getBlockState(new BlockPos(blockPos.getX(), y, blockPos.getZ())).isSolid())
                    blocksAbove++;
                if (blocksAbove >= 3) break;
            }
            if (blocksAbove == 0) skyVector.mul(1.0f, 0.0f, 0.0f);
            else if (blocksAbove == 1) skyVector.mul(0.7f, 0.3f, 0.3f);
            else if (blocksAbove == 2) skyVector.mul(0.7f, 0.7f, 0.7f);
            else return;
        }

    }
}
