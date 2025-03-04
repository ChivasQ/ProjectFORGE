package com.chivasss.pocket_dimestions.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class RendererUtils {
    public static final RenderType LINE_RENDER_TYPE = RenderType.debugQuads();



    public static void vertex(VertexConsumer pConsumer, Matrix4f matrix4f, Matrix3f matrix3f, float pX, float pY, float pZ, int pRed, int pGreen, int pBlue, float pU, float pV) {
        pConsumer.vertex(matrix4f, pX, pY, pZ).color(pRed, pGreen, pBlue, 255).uv(pU, pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }

    public static void DrawLine(Vec3 vec3, Vec3 vec31, Vec3 translate, PoseStack pMatrixStack, MultiBufferSource pBuffer){
        pMatrixStack.pushPose();

        Vec3 vec32 = vec3.subtract(vec31);
        float f4 = (float) (vec32.length());
        vec32 = vec32.normalize();
        float f5 = (float) Math.acos(vec32.y);
        float f6 = (float) Math.atan2(vec32.z, vec32.x);
        pMatrixStack.translate(translate.x, translate.y, translate.z);
        pMatrixStack.mulPose(Axis.YP.rotationDegrees((((float) Math.PI / 2F) - f6) * (180F / (float) Math.PI)));
        pMatrixStack.mulPose(Axis.XP.rotationDegrees(f5 * (180F / (float) Math.PI)));
        float f7 = 0;
        int red = 255;
        int green = 0;
        int blue = 0;
        float f17 = -0.1F;
        float f18 = -0.025F;
        float f19 = Mth.cos(f7 + (float) Math.PI) * f18;
        float f20 = Mth.sin(f7 + (float) Math.PI) * f18;
        float f21 = Mth.cos(f7 + 0.0F) * f18;
        float f22 = Mth.sin(f7 + 0.0F) * f18;
        float f23 = Mth.cos(f7 + ((float) Math.PI / 2F)) * f18;
        float f24 = Mth.sin(f7 + ((float) Math.PI / 2F)) * f18;
        float f25 = Mth.cos(f7 + ((float) Math.PI * 1.5F)) * f18;
        float f26 = Mth.sin(f7 + ((float) Math.PI * 1.5F)) * f18;

        VertexConsumer vertexconsumer = pBuffer.getBuffer(LINE_RENDER_TYPE);
        PoseStack.Pose posestack$pose = pMatrixStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        vertex(vertexconsumer, matrix4f, matrix3f, f19, f4, f20, red, green, blue, f17, f4);
        vertex(vertexconsumer, matrix4f, matrix3f, f19, 0.0F, f20, red, green, blue, f17, 0);
        vertex(vertexconsumer, matrix4f, matrix3f, f21, 0.0F, f22, red, green, blue, 0.0F, 0);
        vertex(vertexconsumer, matrix4f, matrix3f, f21, f4, f22, red, green, blue, 0.0F, f4);

        vertex(vertexconsumer, matrix4f, matrix3f, f23, f4, f24, red, green, blue, -f17, f4);
        vertex(vertexconsumer, matrix4f, matrix3f, f23, 0.0F, f24, red, green, blue, -f17, 0);
        vertex(vertexconsumer, matrix4f, matrix3f, f25, 0.0F, f26, red, green, blue, 0.0F, 0);
        vertex(vertexconsumer, matrix4f, matrix3f, f25, f4, f26, red, green, blue, 0.0F, f4);
        pMatrixStack.popPose();

    }



}
