package com.chivasss.pocket_dimestions.entity.client;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.util.RendererUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
@OnlyIn(Dist.CLIENT)
public class BoreItemRenderer extends BlockEntityWithoutLevelRenderer {
    private BoreModel model;
    private static final ResourceLocation TEXTURE = new ResourceLocation(PocketDim.MODID, "textures/entity/bore.png");

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

        pPoseStack.translate(0.25D, -1.0D, 0.0D);
        pPoseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180.0F));

        VertexConsumer vertexConsumer = pBuffer.getBuffer(RenderType.entityCutout(TEXTURE)); // Используем RenderType.entityCutout
        this.model.renderToBuffer(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
    }
}