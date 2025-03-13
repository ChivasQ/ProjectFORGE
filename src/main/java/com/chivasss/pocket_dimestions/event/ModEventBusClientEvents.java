package com.chivasss.pocket_dimestions.event;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.block.entity.ModBlockEntites;
import com.chivasss.pocket_dimestions.block.entity.client.ElectroTurretBlockEntityRenderer;
import com.chivasss.pocket_dimestions.client.AdrenalineOverlay;
import com.chivasss.pocket_dimestions.entity.client.BoreModel;
import com.chivasss.pocket_dimestions.entity.client.CoreModel;
import com.chivasss.pocket_dimestions.entity.client.ModModelLayers;
import com.chivasss.pocket_dimestions.entity.client.Test1EntityModel;
import com.chivasss.pocket_dimestions.entity.client.rune_turret.RuneTurretModel;
import com.chivasss.pocket_dimestions.entity.client.sandworm.SandwormHeadModel;
import com.chivasss.pocket_dimestions.entity.client.sandworm.SandwormJointPartModel;
import com.chivasss.pocket_dimestions.entity.client.sandworm.SandwormPartModel;
import com.chivasss.pocket_dimestions.entity.client.spidertron.SpidertronModel;
import com.chivasss.pocket_dimestions.entity.custom.symbiote.SymbioteModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
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
        event.registerLayerDefinition(ModModelLayers.SPIDERTRON_LAYER, SpidertronModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.SYMBIOTE_LAYER, SymbioteModel::createBodyLayer);

        event.registerLayerDefinition(ModModelLayers.WORM_BODY_LAYER, SandwormPartModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.WORM_HEAD_LAYER, SandwormHeadModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.WORM_JOINT_LAYER, SandwormJointPartModel::createBodyLayer);

    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(ModBlockEntites.ELECTRO_TURRET_BLOCK_ENTITY.get(), ElectroTurretBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerGuiOverlay(RegisterGuiOverlaysEvent event){
        event.registerBelow(VanillaGuiOverlay.HOTBAR.id(), "adrenaline", AdrenalineOverlay.OVERLAY_ADRENALINE);
    }
}
