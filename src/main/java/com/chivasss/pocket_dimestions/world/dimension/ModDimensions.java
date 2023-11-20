package com.chivasss.pocket_dimestions.world.dimension;

import com.chivasss.pocket_dimestions.PocketDim;
import com.ibm.icu.impl.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.List;
import java.util.OptionalLong;


public class ModDimensions {
    public static final ResourceKey<LevelStem> POCKET_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(PocketDim.MODID, "pocketdim"));
    public static final ResourceKey<Level> POCKET_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(PocketDim.MODID, "pocketdim"));
    public static final ResourceKey<DimensionType> POCKET_KEY_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(PocketDim.MODID, "pocketdim_type"));



}
