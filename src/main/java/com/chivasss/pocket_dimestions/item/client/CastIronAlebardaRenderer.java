package com.chivasss.pocket_dimestions.item.client;

import com.chivasss.pocket_dimestions.item.custom.CastIronAlebarda;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;

public class CastIronAlebardaRenderer extends GeoItemRenderer<CastIronAlebarda> {

    public CastIronAlebardaRenderer() {
        super(new CastIronAlebardaModel());
    }
}
