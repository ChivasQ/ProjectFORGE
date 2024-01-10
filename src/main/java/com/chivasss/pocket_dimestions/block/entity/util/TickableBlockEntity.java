package com.chivasss.pocket_dimestions.block.entity.util;

import com.chivasss.pocket_dimestions.block.entity.ElectroTurretBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;

public interface TickableBlockEntity {
    void tick();

    static <T extends BlockEntity>BlockEntityTicker<T> getTickerHelper(Level level){
        return level.isClientSide() ? null : (pLevel0, pPos0, pState0, pBlockEntity) -> ((ElectroTurretBlockEntity)pBlockEntity).tick();
    }
}
