package com.chivasss.pocket_dimestions.effect;

import com.chivasss.pocket_dimestions.PocketDim;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, PocketDim.MODID);

    public static final RegistryObject<MobEffect> ADRENALINE = MOB_EFFECT.register("adrenaline",
            () ->  new AdrenalineEffect(MobEffectCategory.BENEFICIAL, 0));


    public static void register(IEventBus eventBus) {
        MOB_EFFECT.register(eventBus);
    }
}
