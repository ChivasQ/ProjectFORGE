package com.chivasss.pocket_dimestions.entity;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.entity.custom.CoreEntity;
import com.chivasss.pocket_dimestions.entity.custom.PortalEntity;
import com.chivasss.pocket_dimestions.entity.custom.RuneProjectileEntity;
import com.chivasss.pocket_dimestions.entity.custom.RuneTurretEntity;
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
    public static final RegistryObject<EntityType<CoreEntity>> CORE =
            ENTITY_TYPES.register("core",
                    () -> EntityType.Builder.of(CoreEntity::new, MobCategory.MISC).sized(2.4f,2.6f)
                            .build("core"));
    public static final RegistryObject<EntityType<RuneTurretEntity>> RUNE_TURRET =
            ENTITY_TYPES.register("rune_turret",
                    () -> EntityType.Builder.of(RuneTurretEntity::new, MobCategory.MISC).sized(1.5f,4f)
                            .build("rune_turret"));

    public static final RegistryObject<EntityType<RuneProjectileEntity>> RUNE =
            ENTITY_TYPES.register("rune",
                    () -> EntityType.Builder.<RuneProjectileEntity>of(RuneProjectileEntity::new, MobCategory.MISC).sized(0.1f,0.1f)
                            .build("rune"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}