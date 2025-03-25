package com.chivasss.pocket_dimestions.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class PlayerUtils {
    public static int blocksAbove(Player player, int max) {
        BlockPos blockPos = player.blockPosition().above(2);
        Level level = player.level();
        int blocksAbove = 0;
            for (int y = blockPos.getY(); y < player.level().getMaxBuildHeight(); y++) {
                if (level.getBlockState(new BlockPos(blockPos.getX(), y, blockPos.getZ())).isSolid())
                    blocksAbove++;
                if (blocksAbove >= max) break;
            }
        return blocksAbove;
    }
}
