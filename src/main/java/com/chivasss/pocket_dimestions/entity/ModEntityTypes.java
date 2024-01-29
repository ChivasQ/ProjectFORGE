package com.chivasss.pocket_dimestions.entity;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.entity.custom.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PocketDim.MODID);

    public static final RegistryObject<EntityType<PortalEntity>> PORTAL_A =
            ENTITY_TYPES.register("portal_a",
                    () -> EntityType.Builder.of(PortalEntity::new, MobCategory.MISC)
                            .build(new ResourceLocation(PocketDim.MODID, "portal_a").toString()));

    public static final RegistryObject<EntityType<Test1Entity>> TEST =
            ENTITY_TYPES.register("test111",
                    () -> EntityType.Builder.<Test1Entity>of(Test1Entity::new, MobCategory.MISC).sized(0.5f,0.5f)
                            .build(new ResourceLocation(PocketDim.MODID, "test111").toString()));

    public static final RegistryObject<EntityType<CoreEntity>> CORE =
            ENTITY_TYPES.register("core",
                    () -> EntityType.Builder.of(CoreEntity::new, MobCategory.MISC).sized(2.4f,2.6f)
                            .build("core"));

    public static final RegistryObject<EntityType<RuneTurretEntity>> RUNE_TURRET =
            ENTITY_TYPES.register("rune_turret",
                    () -> EntityType.Builder.of(RuneTurretEntity::new, MobCategory.MISC).sized(1.5f,7.4f)
                            .build("rune_turret"));

    public static final RegistryObject<EntityType<RuneProjectileEntity>> RUNE =
            ENTITY_TYPES.register("rune",
                    () -> EntityType.Builder.<RuneProjectileEntity>of(RuneProjectileEntity::new, MobCategory.MISC).sized(0.5f,0.5f)
                            .build("rune"));

    public static final RegistryObject<EntityType<BolaProjectileEntity>> BOLA =
            ENTITY_TYPES.register("bola",
                    () -> EntityType.Builder.<BolaProjectileEntity>of(BolaProjectileEntity::new, MobCategory.MISC).sized(0.2f,0.2f)
                            .build("bola"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}