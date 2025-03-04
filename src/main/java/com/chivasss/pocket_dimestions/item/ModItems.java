package com.chivasss.pocket_dimestions.item;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.item.armortrim.ModTrimPatterns;
import com.chivasss.pocket_dimestions.item.custom.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
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

    public static final RegistryObject<Item> MURMILLON_AMULET = ITEMS.register("murmillon_amulet",
            () -> new MurmillonAmuletItem(new Item.Properties()
                    .stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> BORE = ITEMS.register("bore",
            () -> new Bore(1,1, Tiers.NETHERITE, BlockTags.MINEABLE_WITH_PICKAXE, new Item.Properties()
                    .stacksTo(1)));

    public static final RegistryObject<Item> ALEBARDA = ITEMS.register("alebarda",
            () -> new CastIronAlebarda(Tiers.NETHERITE, 6, -3F,new Item.Properties()));

    public static final RegistryObject<Item> MOD_TRIM_PART1 = ITEMS.register("mod_trim_part1",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MOD_TRIM_PART2 = ITEMS.register("mod_trim_part2",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MOD_TRIM = ITEMS.register("mod_trim",
            () -> SmithingTemplateItem.createArmorTrimTemplate(ModTrimPatterns.MOD_TRIM));

    public static final RegistryObject<Item> QUARTZ_SWORD = ITEMS.register("quartz_sword",
            () -> new SwordItem(Tiers.DIAMOND, 6, -1F,new Item.Properties()));

    public static final RegistryObject<Item> INFECTION_ITEM = ITEMS.register("experimental_infection_item",
            () -> new Infection(new Item.Properties()));

    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test123",
            () -> new TestItem1(new Item.Properties().stacksTo(1)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
