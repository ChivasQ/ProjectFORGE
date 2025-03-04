package com.chivasss.pocket_dimestions.datagen.loot;

import com.chivasss.pocket_dimestions.block.ModBlocks;
import com.chivasss.pocket_dimestions.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.CAST_IRON_BLOCK.get());
        this.dropSelf(ModBlocks.ASPHALT.get());
        this.dropSelf(ModBlocks.ZINC_BLOCK.get());
        this.dropSelf(ModBlocks.ZINC_LAMP.get());
        this.dropSelf(ModBlocks.ALTAR.get());
        this.dropSelf(ModBlocks.DEEPSLATE_ZINC_ORE.get());
        this.dropSelf(ModBlocks.CRACKED_QUARTZ_BRICKS.get());
        this.dropSelf(ModBlocks.CHISELED_QUARTZ_BLOCK.get());
        this.dropSelf(ModBlocks.SAND_QUARTZ.get());
        this.dropSelf(ModBlocks.SUSPICIOUS_SAND_QUARTZ.get());
        this.dropSelf(ModBlocks.THERMAL_QUARTZ.get());
        this.dropSelf(ModBlocks.ELECTRO_TURRET_BLOCK.get());

        this.add(ModBlocks.ZINC_ORE.get(),
                block -> createCopperOreLikeDrops(ModBlocks.ZINC_ORE.get(), ModItems.RAW_ZINC.get()));
    }


    //createCopperOreDrops()
    protected LootTable.Builder createCopperOreLikeDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
