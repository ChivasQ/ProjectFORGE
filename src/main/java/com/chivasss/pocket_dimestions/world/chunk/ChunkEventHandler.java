package com.chivasss.pocket_dimestions.world.chunk;

import net.minecraftforge.event.level.ChunkDataEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ChunkEventHandler {
    @SubscribeEvent
    public void chunkLoad(ChunkDataEvent.Load event) {
        if (!event.getLevel().isClientSide()) {

        }
    }

    @SubscribeEvent
    public void chunkSave(ChunkDataEvent.Save event) {
        if (!event.getLevel().isClientSide()) {

        }
    }

    @SubscribeEvent
    public void chunkUnload(ChunkEvent.Unload event) {
        if (!event.getLevel().isClientSide()) {

        }
    }
}
