package com.chivasss.pocket_dimestions.block.entity.client;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.block.entity.ElectroTurretBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.List;
import java.util.Optional;

import static com.chivasss.pocket_dimestions.block.custom.ElectroTurretBlock.POWERED;


public class ElectroTurretBlockEntityRenderer implements BlockEntityRenderer<ElectroTurretBlockEntity> {
    private static final ResourceLocation ELECTRO_BEAM_LOCATION = new ResourceLocation(PocketDim.MODID, "textures/entity/electro_beam.png");
    private static final RenderType BEAM_RENDER_TYPE = RenderType.beaconBeam(ELECTRO_BEAM_LOCATION, false);

    public ElectroTurretBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    private static void vertex(VertexConsumer pConsumer, Matrix4f matrix4f, Matrix3f p_253881_, float pX, float pY, float pZ, int pRed, int pGreen, int pBlue, float pU, float pV) {
        pConsumer.vertex(matrix4f, pX, pY, pZ).color(pRed, pGreen, pBlue, 255).uv(pU, pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(p_253881_, 0.0F, 1.0F, 0.0F).endVertex();
    }

    private Vec3 getPosition(LivingEntity pLivingEntity, double pYOffset, float pPartialTick) {
        double d0 = Mth.lerp(pPartialTick, pLivingEntity.xOld, pLivingEntity.getX());
        double d1 = Mth.lerp(pPartialTick, pLivingEntity.yOld, pLivingEntity.getY()) + pYOffset;
        double d2 = Mth.lerp(pPartialTick, pLivingEntity.zOld, pLivingEntity.getZ());
        return new Vec3(d0, d1, d2);
    }

    @Override
    public boolean shouldRender(ElectroTurretBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }

    @Override
    public boolean shouldRenderOffScreen(ElectroTurretBlockEntity pBlockEntity) {
        return true;
    }

    @Override
    public void render(ElectroTurretBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        List<Entity> entityList = pBlockEntity.getEntityList();

        if (entityList != null && pBlockEntity.getBlockState().getValue(POWERED)) {
            for (Entity entity : entityList) {
                AABB entityBox = entity.getBoundingBox();
                Optional<Vec3> optional = entityBox.clip(pBlockEntity.getBlockPos().getCenter().add(0,1.2,0), entity.position());
                if (optional.isPresent()){
                    Vec3 vec3 = new Vec3(entity.getX(), entity.getY()+.5, entity.getZ());

                    float f1 = pPartialTick;
                    float f2 = f1 * 0.5F % 1.0F;
                    float f3 = 1.2F;
                    pPoseStack.pushPose();
                    pPoseStack.translate(0.5F, f3, 0.5F);

                    Vec3 vec31 = pBlockEntity.getBlockPos().getCenter();
                    Vec3 vec32 = vec3.subtract(vec31);
                    float f4 = (float) (vec32.length());
                    vec32 = vec32.normalize();
                    float f5 = (float) Math.acos(vec32.y);
                    float f6 = (float) Math.atan2(vec32.z, vec32.x);

                    pPoseStack.mulPose(Axis.YP.rotationDegrees((((float) Math.PI / 2F) - f6) * (180F / (float) Math.PI)));
                    pPoseStack.mulPose(Axis.XP.rotationDegrees(f5 * (180F / (float) Math.PI)));
                    float f7 = 0;
                    int red = 255;
                    int green = 255;
                    int blue = 255;
                    float f17 = 1F;
                    float f18 = 0.5F;
                    float f19 = Mth.cos(f7 + (float) Math.PI) * f18;
                    float f20 = Mth.sin(f7 + (float) Math.PI) * f18;
                    float f21 = Mth.cos(f7 + 0.0F) * f18;
                    float f22 = Mth.sin(f7 + 0.0F) * f18;
                    float f23 = Mth.cos(f7 + ((float) Math.PI / 2F)) * f18;
                    float f24 = Mth.sin(f7 + ((float) Math.PI / 2F)) * f18;
                    float f25 = Mth.cos(f7 + ((float) Math.PI * 1.5F)) * f18;
                    float f26 = Mth.sin(f7 + ((float) Math.PI * 1.5F)) * f18;

                    VertexConsumer vertexconsumer = pBuffer.getBuffer(BEAM_RENDER_TYPE);
                    PoseStack.Pose posestack$pose = pPoseStack.last();
                    Matrix4f matrix4f = posestack$pose.pose();
                    Matrix3f matrix3f = posestack$pose.normal();
                    vertex(vertexconsumer, matrix4f, matrix3f, f19, f4, f20, red, green, blue, f17, f4);
                    vertex(vertexconsumer, matrix4f, matrix3f, f19, 0.0F, f20, red, green, blue, f17, 0);
                    vertex(vertexconsumer, matrix4f, matrix3f, f21, 0.0F, f22, red, green, blue, 0.0F, 0);
                    vertex(vertexconsumer, matrix4f, matrix3f, f21, f4, f22, red, green, blue, 0.0F, f4);
                    vertex(vertexconsumer, matrix4f, matrix3f, f23, f4, f24, red, green, blue, f17, f4);
                    vertex(vertexconsumer, matrix4f, matrix3f, f23, 0.0F, f24, red, green, blue, f17, 0);
                    vertex(vertexconsumer, matrix4f, matrix3f, f25, 0.0F, f26, red, green, blue, 0.0F, 0);
                    vertex(vertexconsumer, matrix4f, matrix3f, f25, f4, f26, red, green, blue, 0.0F, f4);


                    pPoseStack.popPose();
                }
            }
        }
    }
}
