package com.chivasss.pocket_dimestions.item.client;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.item.custom.CastIronAlebarda;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class CastIronAlebardaModel extends GeoModel<CastIronAlebarda> {

    @Override
    public ResourceLocation getModelResource(CastIronAlebarda animatable) {
        return new ResourceLocation(PocketDim.MODID, "geo/alebarda.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CastIronAlebarda animatable) {
        return new ResourceLocation(PocketDim.MODID, "textures/item/alebarda_model.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CastIronAlebarda animatable) {
        return new ResourceLocation(PocketDim.MODID, "animations/alebarda.animation.json");
    }
}
