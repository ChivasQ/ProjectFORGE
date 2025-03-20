package com.chivasss.pocket_dimestions.datagen;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PocketDim.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.ASPHALT);
        blockWithItem(ModBlocks.ZINC_BLOCK);
        blockWithItem(ModBlocks.ZINC_LAMP);
        blockWithItem(ModBlocks.AI_TEST_BLOCK);
        //blockWithItem(ModBlocks.ALTAR);
        blockWithItem(ModBlocks.ZINC_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_ZINC_ORE);
        blockWithItem(ModBlocks.CRACKED_QUARTZ_BRICKS);
        //blockWithItem(ModBlocks.CHISELED_QUARTZ_BLOCK);
        blockWithItem(ModBlocks.SAND_QUARTZ);
        //blockWithItem(ModBlocks.SUSPICIOUS_SAND_QUARTZ);
        blockWithItem(ModBlocks.THERMAL_QUARTZ);
        blockWithItem(ModBlocks.TEST_BLOCK);
        //blockWithItem(ModBlocks.ELECTRO_TURRET_BLOCK);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
