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
import net.minecraftforge.entity.PartEntity;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Optional;

import static com.chivasss.pocket_dimestions.entity.custom.Test1Entity.owner;


public class SandwormRenderer<T extends Entity> extends EntityRenderer<Sandworm> {
    private static final ResourceLocation SANDWORM_LOCATION = new ResourceLocation(PocketDim.MODID, "textures/entity/worm_texture.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(SANDWORM_LOCATION);
    protected final SandwormHeadModel model;
    protected final SandwormModel partModel;


    public SandwormRenderer(EntityRendererProvider.Context pContext, ModelLayerLocation pLayer) {
        super(pContext);
        this.model = new SandwormHeadModel<>(pContext.bakeLayer(pLayer));
        this.partModel = new SandwormModel<>(pContext.bakeLayer(pLayer));
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

    private static float GetZPAngle(Vec3 entityPos, Vec3 partPos) {
        double deltaY = partPos.y - entityPos.y;
        double deltaXZ = Math.sqrt(Math.pow(partPos.x - entityPos.x, 2) + Math.pow(partPos.z - entityPos.z, 2));
        double angle = Math.atan2(deltaY, deltaXZ);
        return (float) Math.toDegrees(angle);
    }

    private static float GetYPAngle(Vec3 entityPos, Vec3 partPos) {
        double deltaZ = partPos.z - entityPos.z;
        double deltaX = partPos.x - entityPos.x;
        double angle = Math.atan2(deltaZ, deltaX);
        return (float) Math.toDegrees(angle) - 90;
    }

    @Override
    public void render(Sandworm pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        PartEntity[] parts = pEntity.getParts();
        VertexConsumer vertexconsumer = pBuffer.getBuffer(RENDER_TYPE);
        Vec3 entityPos = pEntity.getPosition(pPartialTick);

        pPoseStack.pushPose();
        pPoseStack.translate(0, 0.5, 0);
        pPoseStack.scale(1.0F, -1.0F, 1.0F);

        float xRot = pEntity.getViewXRot(pPartialTick) + 90;
        float yRot = pEntity.getViewYRot(pPartialTick);

        pPoseStack.mulPose(Axis.YP.rotationDegrees(-yRot));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(-xRot));

        pPoseStack.translate(0, -1.5, 0);

        this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();

        for (int i = 0; i < parts.length; i++) {
            pPoseStack.pushPose();

            Vec3 partPos = parts[i].getPosition(pPartialTick);

            Vec3 subtracted = partPos.subtract(pEntity.getPosition(pPartialTick));

            pPoseStack.translate(subtracted.x, subtracted.y + 0.5, subtracted.z);
            pPoseStack.scale(-1.0F, -1.0F, 1.0F);

            pPoseStack.mulPose(Axis.YP.rotationDegrees(GetYPAngle(entityPos, partPos)));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(GetZPAngle(entityPos, partPos)+90));
            //pPoseStack.mulPose(Axis.YP.rotationDegrees(GetYPAngle1(entityPos, partPos)));

            //pPoseStack.mulPose(new Quaternionf().lookAlong(subtracted.normalize().toVector3f(), new Vector3f(0, 1, 0)));
            pPoseStack.translate(0, -1.5, 0);
            partModel.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            pPoseStack.popPose();
            entityPos = parts[i].getPosition(pPartialTick);
        }


    }

    private static float GetXPAngle1(Vec3 target, Vec3 vec3) {
        double d0 = target.x - vec3.x;
        double d1 = target.y - vec3.y;
        double d2 = target.z - vec3.z;
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);

        //this.setYRot(Mth.wrapDegrees((float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F));
        return Mth.wrapDegrees((float)(-(Mth.atan2(d1, d3) * (double)(180F / (float)Math.PI))));
    }
    private static float GetYPAngle1(Vec3 target, Vec3 vec3) {
        double d0 = target.x - vec3.x;
        double d1 = target.y - vec3.y;
        double d2 = target.z - vec3.z;
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);

        return Mth.wrapDegrees((float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(Sandworm pEntity) {
        return SANDWORM_LOCATION;
    }
}
