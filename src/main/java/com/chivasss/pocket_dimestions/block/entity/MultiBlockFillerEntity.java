package com.chivasss.pocket_dimestions.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MultiBlockFillerEntity extends BlockEntity {
    private BlockState storedBlock = Blocks.OBSIDIAN.defaultBlockState();

    public MultiBlockFillerEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntites.MULTI_BLOCK_FILLER.get(),  pPos, pBlockState);
    }

    public void setStoredBlock(BlockState block) {
        this.storedBlock = block;
        setChanged();
    }

    public BlockState getStoredBlock() {
        return storedBlock;
    }
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("StoredBlock", NbtUtils.writeBlockState(storedBlock));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("StoredBlock", Tag.TAG_COMPOUND)) {
            HolderGetter<Block> blockGetter = level != null ? level.holderLookup(Registries.BLOCK) : null;
            if (blockGetter != null) {
                storedBlock = NbtUtils.readBlockState(blockGetter, tag.getCompound("StoredBlock"));
            }
        }
    }
}
