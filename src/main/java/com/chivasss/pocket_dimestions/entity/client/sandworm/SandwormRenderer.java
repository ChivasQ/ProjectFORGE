package com.chivasss.pocket_dimestions.entity.client.sandworm;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.entity.client.Test1EntityModel;
import com.chivasss.pocket_dimestions.entity.custom.sandworm.Sandworm;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.Optional;



public class SandwormRenderer<T extends Entity> extends EntityRenderer<Sandworm> {
    private static final ResourceLocation SANDWORM_LOCATION = new ResourceLocation(PocketDim.MODID, "textures/entity/texture_sandworm.png");
    private static final ResourceLocation TURRET_LOCATION = new ResourceLocation(PocketDim.MODID, "textures/entity/quartz_collumn.png");
    //private static final RenderType BEAM_RENDER_TYPE = RenderType.entityCutoutNoCull(TURRET_LOCATION);
    private static final ResourceLocation TURRET_BEAM_LOCATION = new ResourceLocation(PocketDim.MODID,"textures/entity/turret_beam.png");
    private static final RenderType BEAM_RENDER_TYPE = RenderType.entityCutoutNoCull(TURRET_BEAM_LOCATION);
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(SANDWORM_LOCATION);
    protected final EntityModel<T> model;

    private static void vertex(VertexConsumer pConsumer, Matrix4f matrix4f, Matrix3f p_253881_, float pX, float pY, float pZ, int pRed, int pGreen, int pBlue, float pU, float pV) {
        pConsumer.vertex(matrix4f, pX, pY, pZ).color(pRed, pGreen, pBlue, 255).uv(pU, pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(p_253881_, 0.0F, 1.0F, 0.0F).endVertex();
    }

    public SandwormRenderer(EntityRendererProvider.Context pContext, ModelLayerLocation pLayer) {
        super(pContext);
        this.model = new SandwormModel<>(pContext.bakeLayer(pLayer));
    }

//    @Override
//    public void render(T pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
//        pPoseStack.pushPose();
//        pPoseStack.scale(-1.0F, -1.0F, 1.0F);
//        VertexConsumer vertexconsumer = pBuffer.getBuffer(RENDER_TYPE);
//        this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
//        pPoseStack.popPose();
//        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
//    }


//    @Override
//    public void render(Sandworm pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
//                pPoseStack.pushPose();
//        pPoseStack.scale(-1.0F, -1.0F, 1.0F);
//        VertexConsumer vertexconsumer = pBuffer.getBuffer(RENDER_TYPE);
//        this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
//        pPoseStack.popPose();
//        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
//    }

    @Override
    public ResourceLocation getTextureLocation(Sandworm pEntity) {
        return SANDWORM_LOCATION;
    }



}
