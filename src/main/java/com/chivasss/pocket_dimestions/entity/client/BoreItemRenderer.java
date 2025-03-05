package com.chivasss.pocket_dimestions.entity.client;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.item.custom.Bore;
import com.chivasss.pocket_dimestions.util.RendererUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class BoreItemRenderer extends BlockEntityWithoutLevelRenderer {
    private BoreModel model;
    private static final ResourceLocation TEXTURE = new ResourceLocation(PocketDim.MODID, "textures/entity/bore.png");
    public static final ResourceLocation BEAM_LOCATION = new ResourceLocation("textures/entity/beacon_beam.png");
    public static final int MAX_RENDER_Y = 1024;

    public BoreItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.model = new BoreModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.BORE_LAYER));
    }
    @Override
    public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {}

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        if (pDisplayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) pPoseStack.scale(1.5F, 1.5F, 1.5F);
        else pPoseStack.scale(1.0F, 1.0F, 1.0F);
        float[] beamColor = new float[]{0.0F, 0.5F, 1.0F};
        long gameTime = Minecraft.getInstance().level.getGameTime();
        pPoseStack.translate(0.25D, -1.0D, 0.0D);
        pPoseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180.0F));

        double beamDistance = 0.0;
        if (pStack.getItem() instanceof Bore bore) {
            beamDistance = bore.getDistance();
        }

        int beamHeight = (int) beamDistance;


        VertexConsumer vertexConsumer = pBuffer.getBuffer(RenderType.entityCutout(TEXTURE));
        this.model.renderToBuffer(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
        pPoseStack.pushPose();
        if (!((pDisplayContext == ItemDisplayContext.GUI) || (pDisplayContext == ItemDisplayContext.GROUND))) {
            pPoseStack.mulPose(Axis.XP.rotationDegrees(- 90.0F));
            pPoseStack.translate(0.0D, 0.0D, -0.25D);
            if ((pDisplayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)) {
                pPoseStack.translate(0.1D, 0.25D, 0.25D);
            }
            renderBeaconBeam(pPoseStack, pBuffer, BEAM_LOCATION, 1.0f, 1.0f, gameTime, 0, beamHeight, beamColor, 0.2F, 0.25F);
        }
        pPoseStack.popPose();
    }



    private static void renderBeaconBeam(PoseStack pPoseStack, MultiBufferSource pBufferSource, float pPartialTick, long pGameTime, int pYOffset, int pHeight, float[] pColors) {
        renderBeaconBeam(pPoseStack, pBufferSource, BEAM_LOCATION, pPartialTick, 1.0F, pGameTime, pYOffset, pHeight, pColors, 0.2F, 0.25F);
    }

    public static void renderBeaconBeam(PoseStack pPoseStack, MultiBufferSource pBufferSource, ResourceLocation pBeamLocation, float pPartialTick, float pTextureScale, long pGameTime, int pYOffset, int pHeight, float[] pColors, float pBeamRadius, float pGlowRadius) {
        int i = pYOffset + pHeight;
        pPoseStack.pushPose();
        pPoseStack.translate(0.5D, 0.0D, 0.5D);
        float f = (float)Math.floorMod(pGameTime, 40) + pPartialTick;
        float f1 = pHeight < 0 ? f : -f;
        float f2 = Mth.frac(f1 * 0.2F - (float)Mth.floor(f1 * 0.1F));
        float f3 = pColors[0];
        float f4 = pColors[1];
        float f5 = pColors[2];
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(f * 2.25F - 45.0F));
        float f6 = 0.0F;
        float f8 = 0.0F;
        float f9 = -pBeamRadius;
        float f12 = -pBeamRadius;
        float f15 = -1.0F + f2;
        float f16 = (float)pHeight * pTextureScale * (0.5F / pBeamRadius) + f15;
        renderPart(pPoseStack, pBufferSource.getBuffer(RenderType.beaconBeam(pBeamLocation, false)), f3, f4, f5, 1.0F, pYOffset, i, 0.0F, pBeamRadius, pBeamRadius, 0.0F, f9, 0.0F, 0.0F, f12, 0.0F, 1.0F, f16, f15);
        pPoseStack.popPose();
        f6 = -pGlowRadius;
        float f7 = -pGlowRadius;
        f8 = -pGlowRadius;
        f9 = -pGlowRadius;
        f15 = -1.0F + f2;
        f16 = (float)pHeight * pTextureScale + f15;
        renderPart(pPoseStack, pBufferSource.getBuffer(RenderType.beaconBeam(pBeamLocation, true)), f3, f4, f5, 0.125F, pYOffset, i, f6, f7, pGlowRadius, f8, f9, pGlowRadius, pGlowRadius, pGlowRadius, 0.0F, 1.0F, f16, f15);

        pPoseStack.popPose();
    }

    private static void renderPart(PoseStack pPoseStack, VertexConsumer pConsumer, float pRed, float pGreen, float pBlue, float pAlpha, int pMinY, int pMaxY, float pX0, float pZ0, float pX1, float pZ1, float pX2, float pZ2, float pX3, float pZ3, float pMinU, float pMaxU, float pMinV, float pMaxV) {
        PoseStack.Pose posestack$pose = pPoseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        renderQuad(matrix4f, matrix3f, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY, pX0, pZ0, pX1, pZ1, pMinU, pMaxU, pMinV, pMaxV);
        renderQuad(matrix4f, matrix3f, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY, pX3, pZ3, pX2, pZ2, pMinU, pMaxU, pMinV, pMaxV);
        renderQuad(matrix4f, matrix3f, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY, pX1, pZ1, pX3, pZ3, pMinU, pMaxU, pMinV, pMaxV);
        renderQuad(matrix4f, matrix3f, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY, pX2, pZ2, pX0, pZ0, pMinU, pMaxU, pMinV, pMaxV);
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