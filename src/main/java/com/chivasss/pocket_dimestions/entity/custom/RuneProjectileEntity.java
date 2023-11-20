package com.chivasss.pocket_dimestions.entity.custom;

import com.chivasss.pocket_dimestions.block.ModBlocks;
import com.chivasss.pocket_dimestions.entity.ModEntityTypes;
import com.chivasss.pocket_dimestions.item.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class RuneProjectileEntity extends ThrowableItemProjectile {
    public RuneProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public RuneProjectileEntity(Level pLevel) {
        super(ModEntityTypes.RUNE.get(), pLevel);
    }
    public RuneProjectileEntity(Level pLevel, LivingEntity livingEntity) {
        super(ModEntityTypes.RUNE.get(), livingEntity, pLevel);
    }


    @Override
    protected Item getDefaultItem() {
        return ModItems.RUNE_ITEM.get();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if (!this.level().isClientSide()){
            this.level().broadcastEntityEvent(this, ((byte) 3));
            this.level().setBlock(blockPosition(), ModBlocks.ASPHALT.get().defaultBlockState(),3);
            this.discard();
        }
        super.onHitBlock(pResult);
    }
}
