package com.chivasss.pocket_dimestions.entity.custom.symbiote;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.entity.client.ModModelLayers;
import com.chivasss.pocket_dimestions.entity.client.rune_turret.RuneTurretModel;
import com.chivasss.pocket_dimestions.entity.custom.rune_turret.RuneTurretEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SymbioteRenderer extends MobRenderer<SymbioteEntity, SymbioteModel<SymbioteEntity>> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(PocketDim.MODID, "textures/entity/texture_inf.png");
    public SymbioteRenderer(EntityRendererProvider.Context pContext, ModelLayerLocation symbioteLayer) {
        super(pContext, new SymbioteModel<>(pContext.bakeLayer(ModModelLayers.SYMBIOTE_LAYER)), 0.25f);
    }



    @Override
    public ResourceLocation getTextureLocation(SymbioteEntity pEntity) {
        return TEXTURE_LOCATION;
    }
}
