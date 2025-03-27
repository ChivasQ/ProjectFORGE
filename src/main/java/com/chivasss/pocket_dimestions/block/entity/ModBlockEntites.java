package com.chivasss.pocket_dimestions.block.entity;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.block.ModBlocks;
import com.chivasss.pocket_dimestions.block.custom.MultiBlockFiller;
import com.chivasss.pocket_dimestions.mana.data.ManaManager;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntites {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PocketDim.MODID);

    public static final RegistryObject<BlockEntityType<AltarColumnBlockEntity>> ALTAR_COLUMN_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("altar_column_block_entity", () ->
                    BlockEntityType.Builder.of(AltarColumnBlockEntity::new,
                            ModBlocks.ALTAR.get()).build(null));

    public static final RegistryObject<BlockEntityType<ElectroTurretBlockEntity>> ELECTRO_TURRET_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("electro_turret_block_entity",
                    () -> BlockEntityType.Builder.of(ElectroTurretBlockEntity::new,
                            ModBlocks.ELECTRO_TURRET_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<testBlockEntity>> TEST_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("test_block_entity",
                    () -> BlockEntityType.Builder.of(testBlockEntity::new,
                            ModBlocks.TEST_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<MultiBlockFillerEntity>> MULTI_BLOCK_FILLER =
            BLOCK_ENTITIES.register("multi_block_filler",
                    () -> BlockEntityType.Builder.of(MultiBlockFillerEntity::new,
                            ModBlocks.MULTIBLOCK_FILLER.get()).build(null));;


    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
