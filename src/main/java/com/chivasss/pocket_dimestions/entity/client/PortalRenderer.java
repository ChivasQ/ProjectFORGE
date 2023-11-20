package com.chivasss.pocket_dimestions.entity.client;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.entity.custom.PortalEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;


@OnlyIn(Dist.CLIENT)
public class PortalRenderer extends EntityRenderer<PortalEntity> {
    private static final ResourceLocation FRAME_1 = new ResourceLocation(PocketDim.MODID,
            "textures/entity/pocket_portal_1.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(FRAME_1);

    public PortalRenderer(EntityRendererProvider.Context p_173962_) {
        super(p_173962_);
    }

    private static void vertex(VertexConsumer p_114090_, Matrix4f matrix4f, Matrix3f matrix3f, int i, float v, int i1, int i2, int i3) {
        p_114090_.vertex(matrix4f, v - 0.5F, (float) i1, 0.0F) //MOVE
                .color(255, 255, 255, 255).uv((float) i2, (float) i3)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(i)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }

    protected int getBlockLightLevel(PortalEntity p_114087_, BlockPos p_114088_) {
        return 15;
    }

    public void render(PortalEntity render, float v, float v1, PoseStack poseStack, MultiBufferSource bufferSource, int vertex) {
        poseStack.pushPose();
        poseStack.scale(8.0F, 4F, 1.0F);
//        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RENDER_TYPE);
        vertex(vertexconsumer, matrix4f, matrix3f, vertex, 0F, 0, 0, 1);
        vertex(vertexconsumer, matrix4f, matrix3f, vertex, 1F, 0, 1, 1);
        vertex(vertexconsumer, matrix4f, matrix3f, vertex, 1F, 1, 1, 0);
        vertex(vertexconsumer, matrix4f, matrix3f, vertex, 0F, 1, 0, 0);
        poseStack.popPose();
        super.render(render, v, v1, poseStack, bufferSource, vertex);
    }

    public ResourceLocation getTextureLocation(PortalEntity entity) {


        return FRAME_1;
    }

}
