package com.chivasss.pocket_dimestions.block.entity.client;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.block.entity.testBlockEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class testBlockEntityRenderer implements BlockEntityRenderer<testBlockEntity> {
    public testBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(testBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0, 1.01, 0);

        var vertexConsumer = buffer.getBuffer(RenderType.debugQuads());

        boolean[][] pixels = blockEntity.getPixels();

        float pixelSize = 1.0f / 16.0f;

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                if (pixels[x][y]) {
                    float minX = x * pixelSize;
                    float minZ = y * pixelSize;
                    float maxX = minX + pixelSize;
                    float maxZ = minZ + pixelSize;

                    Matrix4f matrix = poseStack.last().pose();
                    vertexConsumer.vertex(matrix, minX, 0, minZ).color(1f, 1f, 0f, 1f).uv(0, 0).uv2(combinedLight).overlayCoords(combinedOverlay).normal(poseStack.last().normal(), 0, 1, 0).endVertex();
                    vertexConsumer.vertex(matrix, maxX, 0, minZ).color(1f, 0f, 1f, 1f).uv(1, 0).uv2(combinedLight).overlayCoords(combinedOverlay).normal(poseStack.last().normal(), 0, 1, 0).endVertex();
                    vertexConsumer.vertex(matrix, maxX, 0, maxZ).color(1f, 0f, 0f, 1f).uv(1, 1).uv2(combinedLight).overlayCoords(combinedOverlay).normal(poseStack.last().normal(), 0, 1, 0).endVertex();
                    vertexConsumer.vertex(matrix, minX, 0, maxZ).color(1f, 1f, 1f, 1f).uv(0, 1).uv2(combinedLight).overlayCoords(combinedOverlay).normal(poseStack.last().normal(), 0, 1, 0).endVertex();
                }
            }
        }

        poseStack.popPose();
    }



    private static void renderQuad(Matrix4f pPose, Matrix3f pNormal, VertexConsumer pConsumer, float pRed, float pGreen, float pBlue, float pAlpha, int pMinY, int pMaxY, float pMinX, float pMinZ, float pMaxX, float pMaxZ, float pMinU, float pMaxU, float pMinV, float pMaxV) {
        addVertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMaxY, pMinX, pMinZ, pMaxU, pMinV);
        addVertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMinX, pMinZ, pMaxU, pMaxV);
        addVertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxX, pMaxZ, pMinU, pMaxV);
        addVertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMaxY, pMaxX, pMaxZ, pMinU, pMinV);
    }

    private static void addVertex(Matrix4f pPose, Matrix3f pNormal, VertexConsumer pConsumer, float pRed, float pGreen, float pBlue, float pAlpha, int pY, float pX, float pZ, float pU, float pV) {
        pConsumer.vertex(pPose, pX, (float)pY, pZ).color(pRed, pGreen, pBlue, pAlpha).uv(pU, pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(pNormal, 0.0F, 1.0F, 0.0F).endVertex();
    }
}