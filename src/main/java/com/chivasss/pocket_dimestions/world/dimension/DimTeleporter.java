package com.chivasss.pocket_dimestions.world.dimension;

import com.chivasss.pocket_dimestions.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class DimTeleporter implements ITeleporter {
    public static BlockPos thisPos = BlockPos.ZERO;
    public static boolean insideDim = true;

    public DimTeleporter(BlockPos pos, boolean insideDim) {
        thisPos = pos;
        insideDim = insideDim;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld, float yaw, Function<Boolean, Entity> repositionEntity) {

        entity = repositionEntity.apply(false);
        int y = 62;

        if (!insideDim) {
            y = thisPos.getY();
        }

        BlockPos destinationPos = new BlockPos(thisPos.getX(), y, thisPos.getY());

        int tries = 0;
        while ((destinationWorld.getBlockState(destinationPos).getBlock() != Blocks.AIR) &&
                !destinationWorld.getBlockState(destinationPos).canBeReplaced(Fluids.WATER) &&
                destinationWorld.getBlockState(destinationPos.above()).getBlock() != Blocks.AIR &&
                !destinationWorld.getBlockState(destinationPos.above()).canBeReplaced(Fluids.WATER) && tries < 25) {
            destinationPos = destinationPos.above(2);
            tries++;
        }

        entity.teleportTo(destinationPos.getX(), destinationPos.getY(), destinationPos.getZ());

        if (insideDim) {
            boolean doSetBlock = true;
//            for (BlockPos checkPos : BlockPos.betweenClosed(destinationPos.below(10).west(10).south(10), destinationPos.above(10).east(10).north(10))) {
//                if (destinationWorld.getBlockState(checkPos).getBlock() instanceof ) {
//                    doSetBlock = false;
//                    break;
//                }
//            }
            if (doSetBlock) {
                destinationWorld.setBlock(destinationPos.below(2), ModBlocks.THERMAL_QUARTZ.get().defaultBlockState(), 3);
            }
        }

        return entity;
    }
}
