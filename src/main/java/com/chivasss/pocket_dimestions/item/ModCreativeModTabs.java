package com.chivasss.pocket_dimestions.item;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PocketDim.MODID);

    public static final RegistryObject<CreativeModeTab> POCKET_DIM =
            CREATIVE_MODE_TABS.register("pocket_dim",
                    () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CHALK.get()))
                            .title(Component.translatable("creativetab.pocketdim_tab"))
                            .displayItems((pParameters, pOutput) -> {
                                pOutput.accept(ModItems.CHALK.get());
                                pOutput.accept(ModItems.DRAW_AREA.get());
                                pOutput.accept(ModItems.EIGTH_BALL.get());
                                pOutput.accept(ModItems.PESTLE_POT.get());
                                pOutput.accept(ModItems.PESTLE_STICK.get());
                                pOutput.accept(ModItems.RANDOM_EFF.get());
                                pOutput.accept(ModItems.RAW_ZINC.get());
                                pOutput.accept(ModItems.STICK_OF_DYNAMITE.get());
                                pOutput.accept(ModItems.ZINC.get());
                                pOutput.accept(ModItems.ALEBARDA.get());
                                pOutput.accept(ModItems.MOD_TRIM_PART1.get());
                                pOutput.accept(ModItems.MOD_TRIM_PART2.get());
                                pOutput.accept(ModItems.MOD_TRIM.get());
                                pOutput.accept(ModItems.QUARTZ_SWORD.get());
                                pOutput.accept(ModItems.MURMILLON_AMULET.get());

                                pOutput.accept(ModBlocks.CAST_IRON_BLOCK.get());
                                pOutput.accept(ModBlocks.ASPHALT.get());
                                pOutput.accept(ModBlocks.ZINC_BLOCK.get());
                                pOutput.accept(ModBlocks.ZINC_LAMP.get());
                                pOutput.accept(ModBlocks.ALTAR.get());
                                pOutput.accept(ModBlocks.ZINC_ORE.get());
                                pOutput.accept(ModBlocks.DEEPSLATE_ZINC_ORE.get());
                                pOutput.accept(ModBlocks.CRACKED_QUARTZ_BRICKS.get());
                                pOutput.accept(ModBlocks.CHISELED_QUARTZ_BLOCK.get());
                                pOutput.accept(ModBlocks.SAND_QUARTZ.get());
                                pOutput.accept(ModBlocks.SUSPICIOUS_SAND_QUARTZ.get());
                                pOutput.accept(ModBlocks.THERMAL_QUARTZ.get());
                                pOutput.accept(ModBlocks.ELECTRO_TURRET_BLOCK.get());








                                pOutput.accept(ModItems.INFECTION_ITEM.get());
                            })
                            .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
