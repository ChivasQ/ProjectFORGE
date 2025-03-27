package com.chivasss.pocket_dimestions.block.custom;

import com.chivasss.pocket_dimestions.block.entity.MultiBlockFillerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
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
        System.out.println("a");
        return new MultiBlockFillerEntity(pPos, pState);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            System.out.println("onRemove called");
            BlockEntity entity = pLevel.getBlockEntity(pPos);

            if (entity instanceof MultiBlockFillerEntity fillerEntity) {
                System.out.println("d");
                BlockState storedBlock = fillerEntity.getStoredBlock();
                System.out.println(storedBlock.toString());
                if (storedBlock != null && !storedBlock.isAir()) {
                    pLevel.setBlock(pPos, storedBlock, 3);
                }
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }
}

