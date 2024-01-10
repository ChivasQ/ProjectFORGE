package com.chivasss.pocket_dimestions.block;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.block.custom.AltarColumn;
import com.chivasss.pocket_dimestions.block.custom.ElectroTurretBlock;
import com.chivasss.pocket_dimestions.block.custom.ZincLamp;
import com.chivasss.pocket_dimestions.block.custom.asphalt;
import com.chivasss.pocket_dimestions.item.ModItems;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, PocketDim.MODID);


    public static final RegistryObject<Block> ZINC_BLOCK = registerBlock("zinc_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
            .strength(6f)
            .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> ZINC_ORE = registerBlock("zinc_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
            .strength(4f)
            .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> DEEPSLATE_ZINC_ORE = registerBlock("deepslate_zinc_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
            .strength(7f)
            .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> CAST_IRON_BLOCK = registerBlock("cast_iron_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
            .strength(8f)
            .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> ZINC_LAMP = registerBlock("zinc_lamp", () -> new ZincLamp(BlockBehaviour.Properties.copy(Blocks.STONE)
            .strength(3f)
            .requiresCorrectToolForDrops()
            .lightLevel(state -> state.getValue(ZincLamp.LIT) ? 0 : 15)));

    public static final RegistryObject<Block> ASPHALT = registerBlock("asphalt", () -> new asphalt(BlockBehaviour.Properties.copy(Blocks.STONE)
            .strength(4f)));

    public static final RegistryObject<Block> ALTAR = registerBlock("altar_column", () -> new AltarColumn(BlockBehaviour.Properties.copy(Blocks.STONE)
            .strength(4f).noOcclusion()));

    public static final RegistryObject<Block> CRACKED_QUARTZ_BRICKS = registerBlock("cracked_quartz_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BRICKS)));

    public static final RegistryObject<Block> CHISELED_QUARTZ_BLOCK = registerBlock("chiseled_quartz_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BRICKS)));

    public static final RegistryObject<Block> SAND_QUARTZ = registerBlock("sand_quartz", () -> new SandBlock(14408667,BlockBehaviour.Properties.copy(Blocks.SAND)));

    public static final RegistryObject<Block> SUSPICIOUS_SAND_QUARTZ = registerBlock("suspicious_sand_quartz", () -> new BrushableBlock(ModBlocks.CHISELED_QUARTZ_BLOCK.get(),BlockBehaviour.Properties.copy(Blocks.SAND), SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED));

    public static final RegistryObject<Block> THERMAL_QUARTZ = registerBlock("thermal_quartz", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GLASS).strength(0.3F)
            .sound(SoundType.GLASS).lightLevel((state) -> {return 11;})));

    public static final RegistryObject<Block> ELECTRO_TURRET_BLOCK = registerBlock("electro_turret",
            () -> new ElectroTurretBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
            .strength(4f)));














    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
