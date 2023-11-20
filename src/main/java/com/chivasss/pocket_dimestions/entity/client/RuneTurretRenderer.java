package com.chivasss.pocket_dimestions.entity.client;

import com.chivasss.pocket_dimestions.entity.custom.CoreEntity;
import com.chivasss.pocket_dimestions.entity.custom.RuneTurretEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RuneTurretRenderer extends MobRenderer<RuneTurretEntity, RuneTurretModel<RuneTurretEntity>> {
    public RuneTurretRenderer(EntityRendererProvider.Context pContext, RuneTurretModel<RuneTurretEntity> pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
    }

    @Override
    public ResourceLocation getTextureLocation(RuneTurretEntity pEntity) {
        return null;
    }
}
