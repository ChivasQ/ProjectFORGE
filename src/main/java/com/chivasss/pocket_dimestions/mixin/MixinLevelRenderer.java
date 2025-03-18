package com.chivasss.pocket_dimestions.mixin;

import com.chivasss.pocket_dimestions.PocketDim;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FogType;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {
    private static final ResourceLocation OMUAMUA_LOCATION = new ResourceLocation(PocketDim.MODID, "textures/environment/omuamua.png");
    @Unique
    private final Minecraft minecraft = Minecraft.getInstance();
    
    @Inject(method = "renderSky", at = @At("TAIL"))
    private void onRenderSky(PoseStack pPoseStack, Matrix4f pProjectionMatrix, float pPartialTick, Camera pCamera, boolean pIsFoggy, Runnable pSkyFogSetup, CallbackInfo ci) {
        // TODO: maybe render asteroid when spyglass is used?
        //System.out.println("haha");
        if (pIsFoggy) {
            return;
        }
        FogType fogtype = pCamera.getFluidInCamera();
        if (! (fogtype != FogType.POWDER_SNOW && fogtype != FogType.LAVA)) {
            return;
        }

        if (this.minecraft.level.effects().skyType() == DimensionSpecialEffects.SkyType.NORMAL) {
            BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
            RenderSystem.depthMask(false);
            pPoseStack.pushPose();
            float f11 = 1.0F - minecraft.level.getRainLevel(pPartialTick);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f11);
            pPoseStack.translate(100, 0, 0);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(- 45.0F));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(minecraft.level.getTimeOfDay(pPartialTick) * 360.0F * 5));
            Matrix4f matrix4f1 = pPoseStack.last().pose();
            float f12 = 15.0F;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, OMUAMUA_LOCATION);
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(matrix4f1, - f12, 100.0F, - f12).uv(0.0F, 0.0F).endVertex();
            bufferbuilder.vertex(matrix4f1, f12, 100.0F, - f12).uv(1.0F, 0.0F).endVertex();
            bufferbuilder.vertex(matrix4f1, f12, 100.0F, f12).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(matrix4f1, - f12, 100.0F, f12).uv(0.0F, 1.0F).endVertex();
            BufferUploader.drawWithShader(bufferbuilder.end());
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            pPoseStack.popPose();
            RenderSystem.depthMask(true);
        }
    }
}

