package com.chivasss.pocket_dimestions.entity.client;

import ca.weblite.objc.Client;
import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = PocketDim.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BoreBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {
    public static BoreBlockEntityWithoutLevelRenderer instance;
    private final BlockEntityRenderDispatcher modBlockEntityRenderDispatcher;
    private final EntityModelSet modEntityModelSet;
    BoreModel boreModel;
    private static final ResourceLocation BORE_BEAM_LOCATION = new ResourceLocation(PocketDim.MODID,"textures/entity/turret_beam.png");
    private static final RenderType BEAM_RENDER_TYPE = RenderType.entityCutoutNoCull(BORE_BEAM_LOCATION);

    public BoreBlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
        this.modBlockEntityRenderDispatcher = pBlockEntityRenderDispatcher;
        this.modEntityModelSet = pEntityModelSet;
    }
    public void onResourceManagerReload(ResourceManager pResourceManager) {
        this.boreModel = new BoreModel(this.modEntityModelSet.bakeLayer(ModModelLayers.BORE_LAYER));
    }
    @SubscribeEvent
    public static void onRegisterReloadListener(RegisterClientReloadListenersEvent event) {
        instance = new BoreBlockEntityWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        event.registerReloadListener(instance);
    }
    @Override
    public void renderByItem(ItemStack stackIn, ItemDisplayContext type, PoseStack ps, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        ps.pushPose();
        ps.scale(1, -1, -1);
//        Material rendermaterial = isBanner ? ModelBakery.SHIELD_BASE : ModelBakery.NO_PATTERN_SHIELD;


        Vec3 vec3 = new Vec3(0,0,0);
//
        //float f1 = pEntity.getClientSideAttackCooldown() + pPartialTicks;
        //float f2 = f1 * 0.5F % 1.0F;
        //float f3 = pEntity.getEyeHeight();
        ps.pushPose();
        ps.translate(0.0F, 0.0F, 0.0F);
        //Vec3 vec3 = this.getPosition(livingentity, (double)livingentity.getBbHeight() * 0.5D, 1);
        Vec3 vec31 = new Vec3(176, 90, -105);

        Vec3 vec32 = vec3.subtract(vec31);
        float f4 = (float)(vec32.length()+100F);
        vec32 = vec32.normalize();
        float f5 = (float)Math.acos(vec32.y);
        float f6 = (float)Math.atan2(vec32.z, vec32.x);
        ps.mulPose(Axis.YP.rotationDegrees((((float)Math.PI / 2F) - f6) * (180F / (float)Math.PI)));
        ps.mulPose(Axis.XP.rotationDegrees(f5 * (180F / (float)Math.PI)));
        int i = 1;
        float f7 = 0;
        int j = 255;
        int k = 255;
        int l = 255;
        float f9 = 0.2F;
        float f10 = 0.282F;
        float f11 = Mth.cos(f7 + 2.3561945F) * 0.282F;
        float f12 = Mth.sin(f7 + 2.3561945F) * 0.282F;
        float f13 = Mth.cos(f7 + ((float)Math.PI / 4F)) * 0.282F;
        float f14 = Mth.sin(f7 + ((float)Math.PI / 4F)) * 0.282F;
        float f15 = Mth.cos(f7 + 3.926991F) * 0.282F;
        float f16 = Mth.sin(f7 + 3.926991F) * 0.282F;
        float f17 = Mth.cos(f7 + 5.4977875F) * 0.282F;
        float f18 = Mth.sin(f7 + 5.4977875F) * 0.282F;
        float f19 = Mth.cos(f7 + (float)Math.PI) * 0.2F;
        float f20 = Mth.sin(f7 + (float)Math.PI) * 0.2F;
        float f21 = Mth.cos(f7 + 0.0F) * 0.2F;
        float f22 = Mth.sin(f7 + 0.0F) * 0.2F;
        float f23 = Mth.cos(f7 + ((float)Math.PI / 2F)) * 0.2F;
        float f24 = Mth.sin(f7 + ((float)Math.PI / 2F)) * 0.2F;
        float f25 = Mth.cos(f7 + ((float)Math.PI * 1.5F)) * 0.2F;
        float f26 = Mth.sin(f7 + ((float)Math.PI * 1.5F)) * 0.2F;
        float f27 = 0.0F;
        float f28 = 0.4999F;
        float f29 = -1.0F;
        float f30 = f4 * 2.5F + f29;
        VertexConsumer vertexconsumer1 = buffer.getBuffer(BEAM_RENDER_TYPE);
        PoseStack.Pose posestack$pose = ps.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        //pMatrixStack.scale(pEntity.getAttackAnimationScale(pPartialTicks) * f32, 1F, pEntity.getAttackAnimationScale(pPartialTicks)* f32);
        vertex(vertexconsumer1, matrix4f, matrix3f, f19, f4, f20, j, k, l, 0.4999F, f30);
        vertex(vertexconsumer1, matrix4f, matrix3f, f19, 0.0F, f20, j, k, l, 0.4999F, f29);
        vertex(vertexconsumer1, matrix4f, matrix3f, f21, 0.0F, f22, j, k, l, 0.0F, f29);
        vertex(vertexconsumer1, matrix4f, matrix3f, f21, f4, f22, j, k, l, 0.0F, f30);
        vertex(vertexconsumer1, matrix4f, matrix3f, f23, f4, f24, j, k, l, 0.4999F, f30);
        vertex(vertexconsumer1, matrix4f, matrix3f, f23, 0.0F, f24, j, k, l, 0.4999F, f29);
        vertex(vertexconsumer1, matrix4f, matrix3f, f25, 0.0F, f26, j, k, l, 0.0F, f29);
        vertex(vertexconsumer1, matrix4f, matrix3f, f25, f4, f26, j, k, l, 0.0F, f30);


        ps.popPose();
        this.boreModel.renderToBuffer(ps, vertexconsumer1, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        super.renderByItem(stackIn, type, ps, buffer, combinedLight, combinedOverlay);

    }
    private static void vertex(VertexConsumer pConsumer, Matrix4f p_253920_, Matrix3f p_253881_, float pX, float pY, float pZ, int pRed, int pGreen, int pBlue, float pU, float pV) {
        pConsumer.vertex(p_253920_, pX, pY, pZ).color(pRed, pGreen, pBlue, 255).uv(pU, pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(p_253881_, 0.0F, 1.0F, 0.0F).endVertex();
    }
}
