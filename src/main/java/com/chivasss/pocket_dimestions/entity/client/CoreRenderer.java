package com.chivasss.pocket_dimestions.entity.client;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.entity.custom.CoreEntity;
import com.chivasss.pocket_dimestions.entity.custom.PortalEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class CoreRenderer extends MobRenderer<CoreEntity, CoreModel<CoreEntity>> {


    public CoreRenderer(EntityRendererProvider.Context context) {
        super(context, new CoreModel<>(context.bakeLayer(ModModelLayers.CORE_LAYER)), 0.25f);


    }

    @Override
    public ResourceLocation getTextureLocation(CoreEntity p_114482_) {
        return new ResourceLocation(PocketDim.MODID, "textures/entity/core.png");
    }

    @Override
    public void render(CoreEntity p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) {
        super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
    }
}
