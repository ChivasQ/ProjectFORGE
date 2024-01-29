package com.chivasss.pocket_dimestions.entity.custom;

import com.chivasss.pocket_dimestions.entity.ModEntityTypes;
import com.chivasss.pocket_dimestions.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

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
    protected float getGravity() {
        return 0F;
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.RUNE_ITEM.get();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, ((byte) 3));
            //SpawnUtil.trySpawnMob(EntityType.WARDEN, MobSpawnType.TRIGGERED, pLevel, pResult.getBlockPos(), 20, 5, 6, SpawnUtil.Strategy.ON_TOP_OF_COLLIDER).isPresent();
            this.bounce(this, pResult);
//            this.discard();
        }
        super.onHitBlock(pResult);
    }
    private void bounce(Entity pEntity, BlockHitResult blockHitResult) {
        double d0 = 1D;
        Vec3 vec3 = pEntity.getDeltaMovement();


        pEntity.setDeltaMovement(vec3.x, -vec3.y * d0, vec3.z);
        switch (blockHitResult.getDirection()){
            case EAST, WEST -> pEntity.setDeltaMovement(-vec3.x * d0, vec3.y, vec3.z);
            case NORTH, SOUTH -> pEntity.setDeltaMovement(vec3.x, vec3.y, -vec3.z * d0);
        }
        //if (vec3.x < 0.0001 && vec3.y  < 0.0001 && vec3.z < 0.0001) pEntity.setDeltaMovement(0, 0, 0);


    }

    @Override
    public void tick() {
        Vec3 vec3 = this.getDeltaMovement();

        this.level().addParticle(ParticleTypes.TOTEM_OF_UNDYING, this.getX(), this.getY()+0.25, this.getZ(), 0,0,0);
        super.tick();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        pResult.getEntity().hurt(pResult.getEntity().damageSources().magic(), 1);



        super.onHitEntity(pResult);
    }
    public EntityDimensions getDimensions(Pose pPose) {
        return EntityDimensions.scalable(1f,1f);
    }
}
