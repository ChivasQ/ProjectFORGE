package com.chivasss.pocket_dimestions.item;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.item.custom.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PocketDim.MODID);

    public static final RegistryObject<Item> ZINC = ITEMS.register("zinc",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> EIGTH_BALL = ITEMS.register("eight_ball",
            () -> new EigthBallItem(new Item.Properties()

                    .stacksTo(1)));

    public static final RegistryObject<Item> RANDOM_EFF = ITEMS.register("random_eff",
            () -> new RandomEffects(new Item.Properties()
                    .stacksTo(1)));

    public static final RegistryObject<Item> RAW_ZINC = ITEMS.register("raw_zinc",
            () -> new RandomEffects(new Item.Properties()
                    .stacksTo(128)));

    public static final RegistryObject<Item> STICK_OF_DYNAMITE = ITEMS.register("stick_of_dynamite",
            () -> new StickofDynamite(new Item.Properties()));

    public static final RegistryObject<Item> CHALK = ITEMS.register("chalk",
            () -> new Item(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .durability(100)));

    public static final RegistryObject<Item> DRAW_AREA = ITEMS.register("draw_area",
            () -> new TestItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> PESTLE_POT = ITEMS.register("pestle_pot",
            () -> new PestlePot(new Item.Properties()
                    .stacksTo(1)));

    public static final RegistryObject<Item> PESTLE_STICK = ITEMS.register("pestle_stick",
            () -> new PestleStick(new Item.Properties()
                    .stacksTo(1)));

    public static final RegistryObject<Item> RUNE_ITEM = ITEMS.register("rune_item",
            () -> new RuneItem(new Item.Properties()
                    .stacksTo(1)));

    public static final RegistryObject<Item> BORE = ITEMS.register("bore",
            () -> new Bore(new Item.Properties()
                    .stacksTo(1)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
