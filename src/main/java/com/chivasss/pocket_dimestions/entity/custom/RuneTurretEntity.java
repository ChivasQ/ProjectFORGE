package com.chivasss.pocket_dimestions.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class RuneTurretEntity extends AbstractGolem {
    public AnimationState departureAnimationState = new AnimationState();

    public RuneTurretEntity(EntityType<? extends AbstractGolem> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 16.0F, 1F, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.2D)
                .add(Attributes.ATTACK_KNOCKBACK, 20D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 100D);
    }


    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_POSE.equals(pKey)) {
            switch (this.getPose()) {
                case EMERGING:
                    this.departureAnimationState.start(this.tickCount);
                    break;

            }
        }

        super.onSyncedDataUpdated(pKey);
    }

    protected BodyRotationControl createBodyControl() {
        return new RuneTurretEntity.TurretBodyRotationControl(this);
    }

    static class TurretBodyRotationControl extends BodyRotationControl {
        public TurretBodyRotationControl(Mob pMob) {
            super(pMob);
        }

        public void clientTick() {
        }
    }

    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    public void lerpTo(double pX, double pY, double pZ, float pYaw, float pPitch, int pPosRotationIncrements, boolean pTeleport) {
        this.lerpSteps = 0;
        this.setPos(pX, pY, pZ);
        this.setRot(pYaw, pPitch);
    }

//    public EntityDimensions getDimensions(Pose pPose) {
//        EntityDimensions entitydimensions = super.getDimensions(pPose);
//        return this.isDiggingOrEmerging() ? EntityDimensions.fixed(entitydimensions.width, 1.0F) : entitydimensions;
//    }


    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setPose(Pose.EMERGING);
        this.playSound(SoundEvents.WARDEN_AGITATED, 5.0F, 1.0F);


        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }


}
