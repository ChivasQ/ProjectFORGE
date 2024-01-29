package com.chivasss.pocket_dimestions.event;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.block.entity.ModBlockEntites;
import com.chivasss.pocket_dimestions.block.entity.client.ElectroTurretBlockEntityRenderer;
import com.chivasss.pocket_dimestions.entity.client.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PocketDim.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.CORE_LAYER, CoreModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BORE_LAYER, BoreModel::createLayer);
        event.registerLayerDefinition(ModModelLayers.RUNE_TURRET_LAYER, RuneTurretModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.TEST1_LAYER, Test1EntityModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(ModBlockEntites.ELECTRO_TURRET_BLOCK_ENTITY.get(), ElectroTurretBlockEntityRenderer::new);
    }
}
