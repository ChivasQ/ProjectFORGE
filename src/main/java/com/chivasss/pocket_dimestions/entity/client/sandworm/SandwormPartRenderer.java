package com.chivasss.pocket_dimestions.entity.client.sandworm;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.entity.custom.sandworm.SandwormPart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;


public class SandwormPartRenderer<T extends SandwormPart> extends EntityRenderer<T> {
    private static final ResourceLocation SANDWORM_LOCATION = new ResourceLocation(PocketDim.MODID, "textures/entity/worm_texture.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(SANDWORM_LOCATION);
    protected final SandwormPartModel model;

    protected SandwormPartRenderer(EntityRendererProvider.Context pContext, SandwormPartModel model) {
        super(pContext);
        this.model = model;
    }

//    @Override
//    public void render(T pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
//        pPoseStack.pushPose();
//
//
//
//        this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
//        pPoseStack.popPose();
//    }

    @Override
    public void render(T part, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RENDER_TYPE);
        double lerpx = Mth.lerp(partialTick, part.xo, part.getX());
        double lerpy = Mth.lerp(partialTick, part.yo, part.getY());
        double lerpz = Mth.lerp(partialTick, part.zo, part.getZ());
        //poseStack.translate(lerpx - parent.lerpx, lerpy - parent.lerpy, lerpz - parent.lerpz);

        poseStack.mulPose(Axis.YN.rotationDegrees(entityYaw));
//        poseStack.mulPose(parent.resetX);

        float lerpXRot = Mth.lerp(partialTick, part.xRotO, part.getXRot());
//        poseStack.mulPose(Axis.of(new Vector3f((float) Math.cos(rad1), 0, (float) Math.sin(rad1))).rotationDegrees(-lerpXRot));
        poseStack.mulPose(Axis.XN.rotationDegrees(lerpXRot));
        //poseStack.scale(scale, scale, scale);

        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return SANDWORM_LOCATION;
    }
}
