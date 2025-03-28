package com.chivasss.pocket_dimestions.block.custom;

import com.chivasss.pocket_dimestions.block.entity.MultiBlockFillerEntity;
import com.chivasss.pocket_dimestions.util.StructureUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MultiBlockFiller extends BaseEntityBlock {
    public MultiBlockFiller(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        //System.out.println("a");
        return new MultiBlockFillerEntity(pPos, pState);
    }
    // TODO: add a file that corresponds from what structure to what structure, add a tag so that the filler knows what structure it represents.
    // TODO: add multiblock class
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            System.out.println("onRemove called");
            BlockEntity entity = pLevel.getBlockEntity(pPos);

            if (entity instanceof MultiBlockFillerEntity fillerEntity) {
                System.out.println("d");
                BlockPos relPos = fillerEntity.getRelativePosition();
                BlockPos startPos = pPos.subtract(relPos);

                MinecraftServer server = pLevel.getServer();
                if (server != null) {
                    StructureUtils structureUtils = new StructureUtils();
                    CompoundTag nbtData = structureUtils.readNBTFromResource(server, "mod_furnace_structure");
                    if (nbtData != null) {
                        structureUtils.loadBlocksFromNBT(nbtData, startPos, pLevel);
                        structureUtils.build(startPos, pLevel);
                        System.out.println("Structure rebuilt at: " + startPos);
                    } else {
                        System.out.println("Failed to load structure NBT.");
                    }
                } else {
                    System.out.println("Server is null, cannot rebuild structure.");
                }
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }
}