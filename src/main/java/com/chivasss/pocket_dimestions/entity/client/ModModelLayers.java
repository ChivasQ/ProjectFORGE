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
    public static final ModelLayerLocation SPIDERTRON_LAYER = new ModelLayerLocation(
            new ResourceLocation(PocketDim.MODID, "spidertron_base"), "main");
    public static final ModelLayerLocation SYMBIOTE_LAYER = new ModelLayerLocation(
            new ResourceLocation(PocketDim.MODID, "texture_inf"), "main");


    public static final ModelLayerLocation WORM_HEAD_LAYER = new ModelLayerLocation(
            new ResourceLocation(PocketDim.MODID, "worm_head"), "main");
    public static final ModelLayerLocation WORM_BODY_LAYER = new ModelLayerLocation(
            new ResourceLocation(PocketDim.MODID, "worm_body"), "main");
    public static final ModelLayerLocation WORM_JOINT_LAYER = new ModelLayerLocation(
            new ResourceLocation(PocketDim.MODID, "worm_joint"), "main");

}
