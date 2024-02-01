package com.chivasss.pocket_dimestions;

import com.chivasss.pocket_dimestions.block.ModBlocks;
import com.chivasss.pocket_dimestions.block.entity.ModBlockEntites;
import com.chivasss.pocket_dimestions.entity.ModEntityTypes;
import com.chivasss.pocket_dimestions.entity.client.*;
import com.chivasss.pocket_dimestions.item.ModCreativeModTabs;
import com.chivasss.pocket_dimestions.item.ModItems;
import com.chivasss.pocket_dimestions.screen.AltarColumnScreen;
import com.chivasss.pocket_dimestions.screen.ModMenuTypes;
import com.chivasss.pocket_dimestions.sound.ModSounds;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
/**
⣷⣺⡑⢲⠀⠀⣿⡇⣎⣉⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⠌⠀⠀⠀⢰⣿⣿⣿⣿⡿⠻⢿⣰⣼⣦⡐⠒⡒⣐⣱⠈⠒⠹⣿⣿⣿⣿⣿⣿
⣿⠱⠅⣸⣵⣿⣿⣿⡏⠛⠛⠙⠛⠿⣿⣿⣿⣿⣿⣿⠟⠁⠀⠀⠀⢠⣿⣿⣿⣿⣿⣿⣷⣸⡏⠛⠟⡛⣍⡿⠧⡉⠃⢤⢀⡤⢉⠙⢻⣿
⣿⣦⣿⣿⡿⢿⣿⡯⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠀⠀⠀⠀⠀⠀⣸⣿⣿⣿⣿⣿⣿⣿⣻⣿⣷⣰⡔⡜⣧⣒⣪⣒⣢⡕⡈⢆⠢⠩⢿⣿⣿⣿
⣿⣿⣿⣿⡇⠈⠁⡀⠀⣀⣠⣤⣶⣶⣷⣿⣿⣿⣶⣶⣶⣤⣀⠀⠀⠸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣷⣝⡾⢧⣿⣿⠽⠽⠀⠀⠀⡈⣿
⣿⣿⣿⠟⢰⣸⠦⠻⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠟⠋⠀⠀⠀⠘⣿⣿⣿⣿⣿⣿⣿⣽⣯⣿⣿⣿⣿⣿⢿⣯⡙⠘⠂⠒⢮⠑⠢⣿
⣿⣿⣿⣯⡻⠶⡟⢋⣤⣿⣿⠿⣿⣿⣿⣿⣿⡿⣛⡅⠀⠀⠀⠀⠀⠀⠀⠉⢽⣿⣿⣿⣿⣿⢿⣿⣿⣿⣿⣿⡿⢷⡍⢻⣿⣽⣑⠝⢦⠀⣿⣿
⣿⣿⣿⣿⣷⡌⠀⣾⣿⣿⣿⣿⣇⠁⠀⠉⠉⠉⣿⣿⣿⣶⣤⣤⠀⠀⠀⠀⠼⣿⣾⢯⡿⢰⡿⠋⠙⣿⣿⣷⣦⠀⠇⡾⠿⡿⡿⢧⠨⢡⣿
⣿⣿⣿⣿⡟⠀⠀⠈⠻⠿⠿⢿⣿⣷⡀⠀⠀⠀⣿⣿⣿⣿⡿⠟⠀⠀⠀⠀⠀⠈⠻⡏⠃⠛⡁⠸⡽⠝⠛⢛⠩⢂⣤⣿⢽⣚⣓⣮⢦⣿⣿⣿
⣿⣿⣿⣿⣠⣷⣶⣶⣶⣶⣶⣶⣌⣿⣿⡄⠀⠀⠙⠋⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⣷⣶⣦⣀⣠⠂⣟⣁⡽⢯⣵⢖⠊⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⡟⣿⣿⠟⠋⠉⠁⠀⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⠿⣻⣿⣿⠙⠛⠡⣪⣊⠻⢠⢋⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣇⣭⣶⣤⣤⣤⣬⣿⣷⣄⠀⠀⠀⠀⠀⠀⠀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣾⣿⠿⠋⠀⠀⠈⣜⢧⣝⣎⢿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⡇⣭⣷⣶⣶⣶⣶⣝⣛⣿⣧⡀⠀⠀⠀⠀⠀⠙⢿⣿⣦⣤⣀⣀⣤⣔⠎⡠⣞⡿⠁⠀⠀⠀⠀⠀⠈⣡⠿⢿⣽⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⡸⠉⠁⠉⠙⠻⠿⠿⠟⠛⠁⠀⠀⠀⠀⢀⣠⣼⣿⣿⣿⡿⢟⣛⣵⠞⠀⠈⠀⠀⠀⠀⠀⠀⠀⡝⢸⡴⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣇⣤⣾⣶⣤⣀⣀⣀⣀⣀⣠⣤⣴⣶⣾⣿⣿⣿⣿⣿⣿⣿⣿⡿⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣧⣾⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿
**/
@Mod(PocketDim.MODID)
public class PocketDim {
    public static final String MODID = "examplemod";
    private static final Logger LOGGER = LogUtils.getLogger();


    public PocketDim() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModCreativeModTabs.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);

        //ModConfiguredFeatures.register(modEventBus);
        //ModPlacedFeatures.register(modEventBus);

        //ModDimensions.register();
        ModEntityTypes.register(modEventBus);
        ModBlockEntites.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModSounds.register(modEventBus);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);


    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntityTypes.PORTAL_A.get(), PortalRenderer::new);
            EntityRenderers.register(ModEntityTypes.CORE.get(), CoreRenderer::new);
            EntityRenderers.register(ModEntityTypes.RUNE.get(), NoopRenderer::new);
            EntityRenderers.register(ModEntityTypes.RUNE_TURRET.get(), RuneTurretRenderer::new);
            EntityRenderers.register(ModEntityTypes.TEST.get(), (context) -> new Test1EntityRenderer<>(context, ModModelLayers.TEST1_LAYER));
            EntityRenderers.register(ModEntityTypes.BOLA.get(), NoopRenderer::new);


            MenuScreens.register(ModMenuTypes.ALTAR_COLUMN_MENU.get(), AltarColumnScreen::new);
        }
    }
}
