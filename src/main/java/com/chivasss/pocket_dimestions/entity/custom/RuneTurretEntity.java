package com.chivasss.pocket_dimestions.entity.custom;

import com.chivasss.pocket_dimestions.util.ParticlePresets;
import com.chivasss.pocket_dimestions.util.RayTrace;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.opengl.GL11;
import oshi.util.tuples.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

public class RuneTurretEntity extends Monster implements RangedAttackMob {
    private static final int SHORT_LASER_COOLDOWN = 5;
    private static final int SHORT_LASER_RANGE = 32;
    private static final int SHORT_LASER_DAMAGE = 1;
    public AnimationState departureAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeOut = 0;
    private float clientSideTailAnimation;
    private float clientSideTailAnimationO;
    private float clientSideTailAnimationSpeed;
    private float clientSideSpikesAnimation;
    private float clientSideSpikesAnimationO;
    @Nullable
    private LivingEntity clientSideCachedAttackTarget;
    private boolean clientSideTouchedGround;
    private int clientSideAttackTime;
    private static final EntityDataAccessor<Integer> DATA_ID_ATTACK_TARGET = SynchedEntityData.defineId(Guardian.class, EntityDataSerializers.INT);

    public RuneTurretEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        //this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 24.0F, 1F, false));
        //this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1F, SHORT_LASER_COOLDOWN, SHORT_LASER_RANGE));
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(0, new RuneTurretEntity.LaserAttackGoal(this, 0.0D, SHORT_LASER_COOLDOWN, SHORT_LASER_RANGE));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true));
    }

    private void setupAnimationStates() {
        if(this.idleAnimationTimeOut <= 0) {
            this.idleAnimationTimeOut = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeOut;
        }

    }


    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.2D)
                .add(Attributes.ATTACK_KNOCKBACK, 20D)
                .add(Attributes.MOVEMENT_SPEED, 0D)
                .add(Attributes.FLYING_SPEED, 0D)
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




//    void setActiveAttackTarget(int pActiveAttackTargetId) {
//        this.entityData.set(DATA_ID_ATTACK_TARGET, pActiveAttackTargetId);
//    }

    public boolean hasActiveAttackTarget() {
        return this.entityData.get(DATA_ID_ATTACK_TARGET) != 0;
    }

    @Nullable
    public LivingEntity getActiveAttackTarget() {
        if (!this.hasActiveAttackTarget()) {
            return null;
        } else if (this.level().isClientSide) {
            if (this.clientSideCachedAttackTarget != null) {
                return this.clientSideCachedAttackTarget;
            } else {
                Entity entity = this.level().getEntity(this.entityData.get(DATA_ID_ATTACK_TARGET));
                if (entity instanceof LivingEntity) {
                    this.clientSideCachedAttackTarget = (LivingEntity)entity;
                    return this.clientSideCachedAttackTarget;
                } else {
                    return null;
                }
            }
        } else {
            return this.getTarget();
        }
    }





    public int getAttackDuration() {
        return SHORT_LASER_COOLDOWN;
    }

//    public float getAttackAnimationScale(float pPartialTick) {
//        return ((float)this.clientSideAttackTime + pPartialTick) / (float)this.getAttackDuration();
//    }
//
//    public float getClientSideAttackTime() {
//        return (float)this.clientSideAttackTime;
//    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        //setActiveAttackTarget(pTarget.getId());
        pTarget.sendSystemMessage(Component.literal(String.valueOf("SHOOTING")));
        double d0 = pTarget.getX();
        double d1 = pTarget.getY(0.3333333333333333D);
        double d2 = pTarget.getZ();
        Level level = this.level();

        Vec3 look = this.getViewVector(1);
        //pTarget.sendSystemMessage(Component.literal(String.valueOf(look)));
        Vec3 start = this.getEyePosition(1F);

        Vec3 end = new Vec3(this.getX() + look.x * SHORT_LASER_RANGE, this.getEyeY() + look.y * SHORT_LASER_RANGE, this.getZ() + look.z * SHORT_LASER_RANGE);
        ClipContext context = new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this);
        //pTarget.sendSystemMessage(Component.literal(String.valueOf(end)));
        HitResult rayTraceResult = level.clip(context);

        double traceDistance = rayTraceResult.getLocation().distanceToSqr(start);

        AABB playerBox = this.getBoundingBox().expandTowards(look.scale(traceDistance)).expandTowards(1.0D, 1.0D, 1.0D);


        for (Entity possible : level.getEntities(this, playerBox)) {
            AABB entityBox = possible.getBoundingBox().inflate(0.3D); //.deflate(0.3D) or .inflate(0.3D) to scale hitbox
            Optional<Vec3> optional = entityBox.clip(start, end);
            if (optional.isPresent()) {
                Vec3 position = optional.get();
                double distance = start.distanceToSqr(position);

                if (distance < traceDistance) {
                    possible.setTicksFrozen(100);
                    possible.hurt(this.damageSources().indirectMagic(this, pTarget), SHORT_LASER_DAMAGE);
                }
            }
        }



        //ParticlePresets.clientSidedParticles((HitResult) pair.getB(), pTarget.level(), 12, -0.5F, 0.5F);


//        ThrownTrident throwntrident = new ThrownTrident(this.level(), this, new ItemStack(Items.TRIDENT));
//        double d0 = pTarget.getX() - this.getX();
//        double d1 = pTarget.getY(0.3333333333333333D) - throwntrident.getY();
//        double d2 = pTarget.getZ() - this.getZ();
//        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
//        throwntrident.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
//        this.playSound(SoundEvents.DROWNED_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
//        this.level().addFreshEntity(throwntrident);
    }
    protected BodyRotationControl createBodyControl() {
        return new RuneTurretEntity.TurretBodyRotationControl(this);
    }

    static class TurretBodyRotationControl extends BodyRotationControl {
        public TurretBodyRotationControl(Mob pMob) {super(pMob);}

        public void clientTick() {
        }
    }

    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            switch (this.getPose()) {
                case EMERGING:
                    this.clientDiggingParticles(this.departureAnimationState);
                    break;
            }
            setupAnimationStates();
        }


    }

    public void lerpTo(double pX, double pY, double pZ, float pYaw, float pPitch, int pPosRotationIncrements, boolean pTeleport) {
        this.lerpSteps = 0;
        this.setPos(pX, pY, pZ);
        this.setRot(pYaw, pPitch);
    }


    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setPose(Pose.EMERGING);
        //this.setXRot(Mth.randomBetween(this.getRandom(), -180, 179));
        //this.setYRot(Mth.randomBetween(this.getRandom(), -180, 179));
        //this.playSound(SoundEvents.WARDEN_DIG, 5.0F, 1.0F);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    private void clientDiggingParticles(AnimationState pAnimationState) {
        if ((float)pAnimationState.getAccumulatedTime() < 1000.0F) {
            RandomSource randomsource = this.getRandom();
            BlockState blockstate = this.getBlockStateOn();
            if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                for(int i = 0; i < 30; ++i) {
                    double d0 = this.getX() + .25 + (double) Mth.randomBetween(randomsource, -1F, 1F);
                    double d1 = this.getY();
                    double d2 = this.getZ() - .25 + (double)Mth.randomBetween(randomsource, -1F, 1F);
                    this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }

    }



    static class LaserAttackGoal extends RangedAttackGoal {
        private final RuneTurretEntity entity;
        private int attackTime;

        public LaserAttackGoal(RangedAttackMob pRangedAttackMob, double pSpeedModifier, int pAttackInterval, float pAttackRadius) {
            super(pRangedAttackMob, pSpeedModifier, pAttackInterval, pAttackRadius);
            this.entity = (RuneTurretEntity) pRangedAttackMob;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
//        public boolean canUse() {
//            return super.canUse() && this.entity.getMainHandItem().is(Items.TRIDENT);
//        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
//            LivingEntity livingentity = this.entity.getTarget();
//            if (livingentity != null) {
//                this.entity.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
//            }

            //this.entity.setAggressive(true);
            this.entity.startUsingItem(InteractionHand.MAIN_HAND);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            super.stop();
            this.entity.stopUsingItem();
            //this.entity.setTarget((LivingEntity)null);
            //this.entity.setActiveAttackTarget(0);
            //this.entity.setAggressive(false);
        }
//        public void tick() {
//            LivingEntity livingentity = this.entity.getTarget();
//            if (livingentity != null) {
//                this.entity.getNavigation().stop();
//                this.entity.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
//                if (!this.entity.hasLineOfSight(livingentity)) {
//                    this.entity.setTarget((LivingEntity)null);
//                } else {
//                    ++this.attackTime;
//                    if (this.attackTime == 0) {
//                        this.entity.setActiveAttackTarget(livingentity.getId());
//                        if (!this.entity.isSilent()) {
//                            this.entity.level().broadcastEntityEvent(this.entity, (byte)21);
//                        }
//                    } else if (this.attackTime >= this.entity.getAttackDuration()) {
//                        float f = 1.0F;
////                        if (this.entity.level().getDifficulty() == Difficulty.HARD) {
////                            f += 2.0F;
////                        }
//
//
//                        //livingentity.hurt(this.entity.damageSources().indirectMagic(this.entity, this.entity), f);
//                        livingentity.hurt(this.entity.damageSources().mobAttack(this.entity), (float)this.entity.getAttributeValue(Attributes.ATTACK_DAMAGE));
//                        this.entity.setTarget((LivingEntity)null);
//                    }
//
//                    super.tick();
//                }
//            }
//        }
    }
}


