package com.chivasss.pocket_dimestions.world.chunk;


import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public class ChunkManaCapability implements INBTSerializable<CompoundTag> {
    private int mana = 6;

    public int getMana() {
        return mana;
    }

    public void setMana(int value) {
        this.mana = value;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Mana", mana);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.mana = tag.getInt("Mana");
    }
}