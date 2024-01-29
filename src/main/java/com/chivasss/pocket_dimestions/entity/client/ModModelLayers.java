package com.chivasss.pocket_dimestions.entity.client;

import com.chivasss.pocket_dimestions.PocketDim;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation CORE_LAYER = new ModelLayerLocation(
            new ResourceLocation(PocketDim.MODID, "core_layer"), "main");
    public static final ModelLayerLocation RUNE_TURRET_LAYER = new ModelLayerLocation(
            new ResourceLocation(PocketDim.MODID, "rune_turret_layer"), "main");
    public static final ModelLayerLocation BORE_LAYER = new ModelLayerLocation(
            new ResourceLocation(PocketDim.MODID, "bore_layer"), "main");
    public static final ModelLayerLocation TEST1_LAYER = new ModelLayerLocation(
            new ResourceLocation(PocketDim.MODID, "magi_shield_texture"), "main");
}
