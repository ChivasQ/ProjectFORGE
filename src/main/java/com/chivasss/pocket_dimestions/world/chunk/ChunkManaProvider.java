package com.chivasss.pocket_dimestions.world.chunk;


import com.chivasss.pocket_dimestions.capabilities.ModCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ChunkManaProvider extends CapabilityProvider<ChunkManaProvider> implements ICapabilitySerializable<CompoundTag> {
    private final ChunkManaCapability chunkMana = new ChunkManaCapability();

    public ChunkManaProvider() {
        super(ChunkManaProvider.class);
    }

    @Override
    public CompoundTag serializeNBT() {
        return chunkMana.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        chunkMana.deserializeNBT(nbt);
    }


    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ModCapabilities.CHUNK_MANA) {
            return LazyOptional.of(() -> chunkMana).cast();
        }
        return super.getCapability(cap, side);
    }
}