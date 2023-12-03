package com.chivasss.pocket_dimestions.world.dimension;

import com.chivasss.pocket_dimestions.PocketDim;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;


public class ModDimensions {
    public static final ResourceKey<LevelStem> POCKET_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(PocketDim.MODID, "pocketdim"));
    public static final ResourceKey<Level> POCKET_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(PocketDim.MODID, "pocketdim"));
    public static final ResourceKey<DimensionType> POCKET_KEY_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(PocketDim.MODID, "pocketdim_type"));



}
