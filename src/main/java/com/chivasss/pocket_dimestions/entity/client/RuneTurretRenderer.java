package com.chivasss.pocket_dimestions.entity.client;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.entity.custom.CoreEntity;
import com.chivasss.pocket_dimestions.entity.custom.RuneTurretEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RuneTurretRenderer extends MobRenderer<RuneTurretEntity, RuneTurretModel<RuneTurretEntity>> {
    public RuneTurretRenderer(EntityRendererProvider.Context context) {
        super(context, new RuneTurretModel<>(context.bakeLayer(ModModelLayers.RUNE_TURRET_LAYER)), 0f);


    }

    @Override
    public ResourceLocation getTextureLocation(RuneTurretEntity pEntity) {
        return new ResourceLocation(PocketDim.MODID, "textures/entity/quartz_collumn.png");
    }

    @Override
    public void render(RuneTurretEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

}
