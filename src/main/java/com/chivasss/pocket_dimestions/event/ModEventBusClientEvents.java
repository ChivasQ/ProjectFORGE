package com.chivasss.pocket_dimestions.event;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.entity.client.CoreModel;
import com.chivasss.pocket_dimestions.entity.client.ModModelLayers;
import com.chivasss.pocket_dimestions.entity.client.RuneTurretModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PocketDim.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ModModelLayers.CORE_LAYER, CoreModel::createBodyLayer);
            event.registerLayerDefinition(ModModelLayers.RUNE_TURRET_LAYER, RuneTurretModel::createBodyLayer);
        }
}
