package com.chivasss.pocket_dimestions.event;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.entity.ModEntityTypes;
import com.chivasss.pocket_dimestions.entity.custom.CoreEntity;
import com.chivasss.pocket_dimestions.entity.custom.rune_turret.RuneTurretEntity;
import com.chivasss.pocket_dimestions.entity.custom.sandworm.Sandworm;
import com.chivasss.pocket_dimestions.entity.custom.spidertron.Spidertron;
import com.chivasss.pocket_dimestions.entity.custom.symbiote.SymbioteEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PocketDim.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.CORE.get(), CoreEntity.createAttributes().build());
        event.put(ModEntityTypes.RUNE_TURRET.get(), RuneTurretEntity.createAttributes().build());
        event.put(ModEntityTypes.SANDWORM.get(), Sandworm.createAttributes().build());
        event.put(ModEntityTypes.SPIDERTRON.get(), Spidertron.createAttributes().build());
        event.put(ModEntityTypes.SYMBIOTE.get(), SymbioteEntity.createAttributes().build());

    }
}
