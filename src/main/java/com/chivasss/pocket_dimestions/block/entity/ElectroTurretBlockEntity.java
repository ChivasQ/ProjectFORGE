package com.chivasss.pocket_dimestions.block.entity;

import com.chivasss.pocket_dimestions.block.ModBlocks;
import com.chivasss.pocket_dimestions.block.entity.util.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.chivasss.pocket_dimestions.block.custom.ElectroTurretBlock.POWERED;

public class ElectroTurretBlockEntity extends BlockEntity implements TickableBlockEntity {
    public int ticks;
    public int radius = 5;
    BlockPos boxStart = new BlockPos(
            this.getBlockPos().getX()+radius,
            this.getBlockPos().getY()+radius,
            this.getBlockPos().getZ()+radius
    );
    BlockPos boxEnd = new BlockPos(
            this.getBlockPos().getX()-radius,
            this.getBlockPos().getY()-radius,
            this.getBlockPos().getZ()-radius
    );
    AABB Box = new AABB(boxStart, boxEnd);
    Predicate<Entity> filter = entity -> !entity.isSpectator() && entity.isPickable() && entity instanceof Monster;


    public ElectroTurretBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntites.ELECTRO_TURRET_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public List<Entity> getEntityList(){
        if (this.level != null)
            return level.getEntities((Entity) null, Box, filter);
        else return null;
    }

    @Override
    public void tick() {
        if (this.level == null)
            return;
        if(NearBlock()) {
            if (!getEntityList().isEmpty()) {
                if (this.ticks++ % 40 == 0 && this.ticks > 0) {


                    for (Entity possible : getEntityList()) {
                        AABB entityBox = possible.getBoundingBox();
                        Optional<Vec3> optional = entityBox.clip(this.getBlockPos().getCenter().add(0,1.2,0), possible.position());

                        if (optional.isPresent()) {
                            possible.hurt(possible.damageSources().freeze(), 5);
                            possible.setTicksFrozen(100);
                        }
                    }
                }
                if (this.ticks % 40 > 1 && this.ticks % 40 < 10) {
                    level.setBlock(this.getBlockPos(), this.getBlockState().setValue(POWERED, Boolean.TRUE), 3);
                } else {
                    level.setBlock(this.getBlockPos(), this.getBlockState().setValue(POWERED, Boolean.FALSE), 3);
                }
            }




        }
    }



    private boolean NearBlock() {
        if (this.level.getBlockState(new BlockPos(this.getBlockPos().getX(), this.getBlockPos().getY()-1, this.getBlockPos().getZ())).getBlock()
                == ModBlocks.THERMAL_QUARTZ.get()) {
            return true;
        }
        else {
            return false;
        }
    }
}
