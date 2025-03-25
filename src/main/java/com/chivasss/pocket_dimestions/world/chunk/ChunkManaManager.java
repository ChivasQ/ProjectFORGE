package com.chivasss.pocket_dimestions.world.chunk;

import com.chivasss.pocket_dimestions.capabilities.ModCapabilities;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.util.LazyOptional;

public class ChunkManaManager {
    public static int getMana(LevelChunk chunk) {
        LazyOptional<ChunkManaCapability> cap = chunk.getCapability(ModCapabilities.CHUNK_MANA);
        return cap.map(ChunkManaCapability::getMana).orElse(0);
    }

    public static void setMana(LevelChunk chunk, int value) {
        LazyOptional<ChunkManaCapability> cap = chunk.getCapability(ModCapabilities.CHUNK_MANA);
        cap.ifPresent(mana -> mana.setMana(value));
    }
}