package com.chivasss.pocket_dimestions.world.chunk;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class ChunkManaData extends SavedData {
    private int manaLevel;

    public ChunkManaData(int manaLevel) {
        this.manaLevel = manaLevel;
    }

    public static ChunkManaData load(CompoundTag tag) {
        return new ChunkManaData(tag.getInt("ManaLevel"));
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putInt("ManaLevel", manaLevel);
        return tag;
    }

    public int getManaLevel() {
        return manaLevel;
    }

    public void setManaLevel(int manaLevel) {
        this.manaLevel = manaLevel;
        setDirty();
    }
}