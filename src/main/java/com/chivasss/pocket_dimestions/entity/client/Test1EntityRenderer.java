package com.chivasss.pocket_dimestions.entity.client;

import com.chivasss.pocket_dimestions.PocketDim;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.chivasss.pocket_dimestions.entity.custom.Test1Entity.owner;

@OnlyIn(Dist.CLIENT)
public class Test1EntityRenderer<T extends Entity> extends EntityRenderer<T> {
    private static final ResourceLocation TEST_LOCATION = new ResourceLocation(PocketDim.MODID, "textures/entity/magi_shield_texture.png");
    private static final RenderType RENDER_TYPE = RenderType.entityTranslucentEmissive(TEST_LOCATION);
    protected final EntityModel<T> model;

    public Test1EntityRenderer(EntityRendererProvider.Context pContext, ModelLayerLocation pLayer) {
        super(pContext);
        this.model = new Test1EntityModel<>(pContext.bakeLayer(pLayer));
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTick, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.scale(-1.0F, -1.0F, 1.0F);

        if (owner != null) {
            pMatrixStack.mulPose(Axis.YP.rotationDegrees(GetYPAngle(owner.getEyePosition(), pEntity.position())-90));
            pMatrixStack.mulPose(Axis.XP.rotationDegrees(GetZPAngle(owner.getEyePosition(), pEntity.position())));
            //pMatrixStack.mulPose(Axis.XP.rotationDegrees(45));
            //System.out.println(GetZPAngle(owner.getEyePosition(), pEntity.position())-90);
            //System.out.println(owner.position() + " " + pEntity.position());
        }
        //pMatrixStack.translate(0,-0.2,0);
        //this.model.setupAnim(pEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(RENDER_TYPE);
        this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pMatrixStack, pBuffer, pPackedLight);
    }

    private static float GetZPAngle(Vec3 player, Vec3 shield) {
        double d0 = Math.atan((shield.y-player.y)/ player.distanceTo(new Vec3(shield.x, player.y, shield.z)));
        return Math.round(Math.toDegrees(d0)-90);
    }
    private static float GetYPAngle(Vec3 player, Vec3 shield) {
        double tg = Math.atan((shield.z - player.z)/(shield.x - player.x));
        long deg = Math.round(Math.toDegrees(tg));
        if (player.x > shield.x && player.z > shield.z){
            deg = deg+180;
        } else if(player.x > shield.x && player.z < shield.z){
            deg = deg + 180;
        }
        return deg;
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return TEST_LOCATION;
    }


}
