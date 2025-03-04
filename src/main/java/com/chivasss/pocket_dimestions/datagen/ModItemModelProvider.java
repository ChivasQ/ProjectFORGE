package com.chivasss.pocket_dimestions.datagen;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PocketDim.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.CHALK);
        simpleItem(ModItems.DRAW_AREA);
        simpleItem(ModItems.EIGTH_BALL);
        simpleItem(ModItems.PESTLE_POT);
        simpleItem(ModItems.PESTLE_STICK);
        //simpleItem(ModItems.RANDOM_EFF);
        simpleItem(ModItems.RAW_ZINC);
        //simpleItem(ModItems.STICK_OF_DYNAMITE);
        simpleItem(ModItems.ZINC);
        //simpleItem(ModItems.ALEBARDA);
        simpleItem(ModItems.MOD_TRIM_PART1);
        simpleItem(ModItems.MOD_TRIM_PART2);
        simpleItem(ModItems.MOD_TRIM);
        simpleItem(ModItems.QUARTZ_SWORD);
        simpleItem(ModItems.MURMILLON_AMULET);




    }


    private void simpleItem(RegistryObject<Item> item) {
        withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PocketDim.MODID, "item/" + item.getId().getPath()));
    }
}
