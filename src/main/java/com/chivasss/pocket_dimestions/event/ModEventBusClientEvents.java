package com.chivasss.pocket_dimestions.event;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.block.entity.ModBlockEntites;
import com.chivasss.pocket_dimestions.block.entity.client.ElectroTurretBlockEntityRenderer;
import com.chivasss.pocket_dimestions.block.entity.client.testBlockEntityRenderer;
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
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.function.Function;

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
        event.registerBlockEntityRenderer(ModBlockEntites.TEST_BLOCK_ENTITY.get(), testBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerGuiOverlay(RegisterGuiOverlaysEvent event){
        event.registerAboveAll( "adrenaline", AdrenalineOverlay.OVERLAY_ADRENALINE);
        //event.registerAbove(VanillaGuiOverlay.CHAT_PANEL.id(), "adrenaline", AdrenalineOverlay.OVERLAY_ADRENALINE);
        //event.registerBelow(VanillaGuiOverlay.HOTBAR.id(), "adrenaline", AdrenalineOverlay.OVERLAY_ADRENALINE);

        //        event.registerBelow(VanillaGuiOverlay.HOTBAR.id(), "adrenaline", AdrenalineOverlay.OVERLAY_ADRENALINE);
    }

    @SubscribeEvent
    public static void registerShader(RegisterShadersEvent event) throws IOException {
//        event.registerShader(new ShaderInstance(
//                event.getResourceProvider(),
//                new ResourceLocation(PocketDim.MODID, "rendertype_bright_solid"),
//                DefaultVertexFormat.NEW_ENTITY)
//        , shaderInstance -> CustomRenderTypes.brightSolidShader = shaderInstance);
//        TODO: FIX.
    }


    private static class CustomRenderTypes extends RenderType
    {
        // Holds the object loaded via RegisterShadersEvent
        private static ShaderInstance brightSolidShader;

        // Shader state for use in the render type, the supplier ensures it updates automatically with resource reloads
        private static final ShaderStateShard RENDERTYPE_BRIGHT_SOLID_SHADER = new ShaderStateShard(() -> brightSolidShader);

        // Dummy constructor needed to make java happy
        private CustomRenderTypes(String s, VertexFormat v, VertexFormat.Mode m, int i, boolean b, boolean b2, Runnable r, Runnable r2)
        {
            super(s, v, m, i, b, b2, r, r2);
            throw new IllegalStateException("This class is not meant to be constructed!");
        }

        // The memoize caches the output value for each input, meaning the expensive registration process doesn't have to rerun
        public static Function<ResourceLocation, RenderType> BRIGHT_SOLID = Util.memoize(CustomRenderTypes::brightSolid);

        // Defines the RenderType. Make sure the name is unique by including your MODID in the name.
        private static RenderType brightSolid(ResourceLocation locationIn)
        {
            RenderType.CompositeState rendertype$state = RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_BRIGHT_SOLID_SHADER)
                    .setTextureState(new RenderStateShard.TextureStateShard(locationIn, false, false))
                    .setTransparencyState(NO_TRANSPARENCY)
                    .setLightmapState(NO_LIGHTMAP)
                    .setOverlayState(NO_OVERLAY)
                    .createCompositeState(true);
            return create("gbook_bright_solid", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, false, rendertype$state);
        }
    }
}
