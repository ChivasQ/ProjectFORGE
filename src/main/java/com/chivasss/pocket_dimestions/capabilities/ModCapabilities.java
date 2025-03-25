package com.chivasss.pocket_dimestions.capabilities;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.world.chunk.ChunkManaCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PocketDim.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCapabilities {
    public static final Capability<ChunkManaCapability> CHUNK_MANA = CapabilityManager.get(new CapabilityToken<>() {});

}