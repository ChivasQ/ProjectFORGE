package com.chivasss.pocket_dimestions.world.dimension;

import com.chivasss.pocket_dimestions.PocketDim;
import com.ibm.icu.impl.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
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



    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(POCKET_KEY_TYPE, new DimensionType(
                OptionalLong.of(12000), // fixedTime
                false, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                false, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                0, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                BuiltinDimensionTypes.END_EFFECTS, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator wrappedChunkGenerator = new NoiseBasedChunkGenerator(
                new FixedBiomeSource(biomeRegistry.getOrThrow(Biomes.THE_VOID)),
                noiseGenSettings.getOrThrow(NoiseGeneratorSettings.AMPLIFIED));

        LevelStem stem = new LevelStem(dimTypes.getOrThrow(ModDimensions.POCKET_KEY_TYPE), wrappedChunkGenerator);

        context.register(POCKET_KEY, stem);
    }
}


