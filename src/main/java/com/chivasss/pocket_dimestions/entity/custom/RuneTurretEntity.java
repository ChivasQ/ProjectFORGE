package com.chivasss.pocket_dimestions.entity.custom;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;


public class RuneTurretEntity extends Monster {
    protected static final int ATTACK_TIME = 80;
    private static final int SHORT_LASER_RANGE = 32;
    public AnimationState departureAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeOut = 0;
    private static final EntityDataAccessor<Integer> DATA_ID_ATTACK_TARGET = SynchedEntityData.defineId(RuneTurretEntity.class, EntityDataSerializers.INT);
    @Nullable
    private LivingEntity clientSideCachedAttackTarget;
    public int clientSideAttackCooldown;
    public int clientSideAttackTime;


    private static final int MAX_TICK_HISTORY = 3;

    public static Deque<HitResult> entityPositions = new LinkedList<>();
    private LivingEntity lastTarget;
    public RuneTurretEntity(EntityType<? extends RuneTurretEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 10;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RuneTurretEntity.RuneTurretAttackGoal(this));
        //this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, new RuneTurretEntity.RuneTurretAttackSelector(this)));
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
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.2D)
                .add(Attributes.ATTACK_KNOCKBACK, 20D)
                .add(Attributes.MOVEMENT_SPEED, 0D)
                .add(Attributes.FLYING_SPEED, 0D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 100D)
                .add(Attributes.ATTACK_DAMAGE, 10D);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_ATTACK_TARGET, 0);
    }
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public int getAttackDuration() {
        return 80;
    }
    public int getAttackTime() {
        return 40;
    }
    void setActiveAttackTarget(int pActiveAttackTargetId) {
        this.entityData.set(DATA_ID_ATTACK_TARGET, pActiveAttackTargetId);
    }

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
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        super.onSyncedDataUpdated(pKey);
        if (DATA_ID_ATTACK_TARGET.equals(pKey)) {
            this.clientSideAttackCooldown = 0;
            this.clientSideAttackTime = 0;
            this.clientSideCachedAttackTarget = null;
            //this.clientAttackBlock = null;
        }
        if (DATA_POSE.equals(pKey)) {
            switch (this.getPose()) {
                case EMERGING:
                    this.departureAnimationState.start(this.tickCount);
                    break;
            }
        }
    }

    public int getAmbientSoundInterval() {
        return 160;
    }

//    protected SoundEvent getAmbientSound() {
//        return this.isInWaterOrBubble() ? SoundEvents.RuneTurret_AMBIENT : SoundEvents.RuneTurret_AMBIENT_LAND;
//    }
//
//    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
//        return this.isInWaterOrBubble() ? SoundEvents.RuneTurret_HURT : SoundEvents.RuneTurret_HURT_LAND;
//    }
//
//    protected SoundEvent getDeathSound() {
//        return this.isInWaterOrBubble() ? SoundEvents.GUARDIAN_DEATH : SoundEvents.GUARDIAN_DEATH_LAND;
//    }

    public void lerpTo(double pX, double pY, double pZ, float pYaw, float pPitch, int pPosRotationIncrements, boolean pTeleport) {
        this.lerpSteps = 0;
        this.setPos(pX, pY, pZ);
        this.setRot(pYaw, pPitch);
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

    private void clientDiggingParticles(AnimationState pAnimationState) {
        if ((float)pAnimationState.getAccumulatedTime() < 1000.0F) {
            RandomSource randomsource = this.getRandom();
            BlockState blockstate = this.getBlockStateOn();
            if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                for(int i = 0; i < 30; ++i) {
                    double d0 = this.getX() + .25 + (double) Mth.randomBetween(randomsource, -1F, 1F);
                    double d1 = this.getY();
                    double d2 = this.getZ() - .25 + (double) Mth.randomBetween(randomsource, -1F, 1F);
                    this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
    public void aiStep() {
        if (this.isAlive()) {
            if (this.level().isClientSide) {
                if (this.hasActiveAttackTarget()) {
                    if (this.clientSideAttackCooldown < this.getAttackDuration()) {
                        ++this.clientSideAttackCooldown;
                    }
                    if (this.clientSideAttackTime < this.getAttackTime()) {
                        ++this.clientSideAttackTime;
                    }
                    LivingEntity livingentity = this.getActiveAttackTarget();
                    if (livingentity != null) {
                        this.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
                        this.getLookControl().tick();
                        double d5 = (double)this.getAttackAnimationScale(0.0F);
                        double d0 = livingentity.getX() - this.getX();
                        double d1 = livingentity.getY(0.5D) - this.getEyeY();
                        double d2 = livingentity.getZ() - this.getZ();
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        double d4 = this.random.nextDouble();

//                        while(d4 < d3) {
//                            d4 += 1.8D - d5 + this.random.nextDouble() * (1.7D - d5);
//                            this.level().addParticle(ParticleTypes.BUBBLE, this.getX() + d0 * d4, this.getEyeY() + d1 * d4, this.getZ() + d2 * d4, 0.0D, 0.0D, 0.0D);
//                        }
                    }
                }
            }



            if (this.hasActiveAttackTarget()) {
                this.setYRot(this.yHeadRot);
            }
        }

        super.aiStep();
    }
    protected SoundEvent getFlopSound() {
        return SoundEvents.GUARDIAN_FLOP;
    }
    public float getAttackAnimationScale(float pPartialTick) {
        return ((float)this.clientSideAttackCooldown + pPartialTick) / (float)this.getAttackDuration();
    }
    public float getAttackAnimationScaleTime(float pPartialTick) {

        return ((float)this.clientSideAttackTime + pPartialTick) / (float)this.getAttackTime();
    }

    public float getClientSideAttackCooldown() {
        return (float)this.clientSideAttackCooldown;
    }

//    public int getMaxHeadYRot() {
//        return 180;
//    }
    private static void updateHitResultPositions(HitResult currentPosition) {
        entityPositions.offer(currentPosition);

        // Remove old positions to keep the history within the specified limit
        while (entityPositions.size() > MAX_TICK_HISTORY) {
            entityPositions.poll();
        }
    }
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setPose(Pose.EMERGING);
        this.playSound(SoundEvents.WARDEN_DIG, 5.0F, 1.0F);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    static class RuneTurretAttackGoal extends Goal {
        private final RuneTurretEntity entity;
        private int attackCooldown;
        public int attackTime;
        public RuneTurretAttackGoal(RuneTurretEntity pRuneTurret) {
            this.entity = pRuneTurret;
            //this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }
        public boolean canUse() {
            LivingEntity livingentity = this.entity.getTarget();
            return livingentity != null && livingentity.isAlive();
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse() && (this.entity.getTarget() != null && this.entity.distanceToSqr(this.entity.getTarget()) > 9.0D);
        }

        public void start() {
            this.attackCooldown = -10;
            this.attackTime = 0;
            this.entity.getNavigation().stop();
            LivingEntity livingentity = this.entity.getTarget();
            if (livingentity != null) {
                this.entity.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
            }

            this.entity.hasImpulse = true;
        }

        public void stop() {
            this.entity.setActiveAttackTarget(0);
            this.entity.setTarget((LivingEntity)null);
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }
        public void performAttack(HitResult rayTraceResult, Level level, Vec3 start, Vec3 look, float f) {
            double traceDistance = rayTraceResult.getLocation().distanceToSqr(start);

            AABB playerBox = this.entity.getBoundingBox().expandTowards(look.scale(traceDistance)).expandTowards(1.0D, 1.0D, 1.0D);

            for (Entity possible : level.getEntities(this.entity, playerBox)) {
                AABB entityBox = possible.getBoundingBox().inflate(0.3D); //.deflate(0.3D) or .inflate(0.3D) to scale hitbox
                Optional<Vec3> optional = entityBox.clip(start, rayTraceResult.getLocation());
                if (optional.isPresent()) {
                    Vec3 position = optional.get();
                    double distance = start.distanceToSqr(position);

                    if (distance < traceDistance) {
                        //possible.setTicksFrozen(100);
                        possible.hurt(this.entity.damageSources().indirectMagic(this.entity, this.entity), f);
                        possible.hurt(this.entity.damageSources().mobAttack(this.entity), (float) this.entity.getAttributeValue(Attributes.ATTACK_DAMAGE));

                    }
                }
            }
        }
        public void tick() {
            LivingEntity livingentity = this.entity.getTarget();
            if (livingentity != null) {

                Level level = this.entity.level();
                Vec3 look = this.entity.getViewVector(1F);
                Vec3 start = this.entity.getEyePosition(1F);

                Vec3 end = new Vec3(this.entity.getX() + look.x * SHORT_LASER_RANGE, this.entity.getEyeY()-1 + look.y * SHORT_LASER_RANGE, this.entity.getZ() + look.z * SHORT_LASER_RANGE);
                ClipContext context = new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.entity);
                //livingentity.sendSystemMessage(Component.literal(String.valueOf(end)));
                HitResult rayTraceResult = level.clip(context);

                updateHitResultPositions(rayTraceResult);
                if (!entityPositions.isEmpty()) {
                    rayTraceResult = entityPositions.getFirst();
                }
                this.entity.getLookControl().setLookAt(livingentity);

                if (!this.entity.hasLineOfSight(livingentity)) {
                    this.entity.setTarget((LivingEntity) null);
                } else {
                    ++this.attackCooldown;
                    if (this.attackCooldown == 0) {
                        this.entity.setActiveAttackTarget(livingentity.getId());
                        if (!this.entity.isSilent()) {
                            this.entity.level().broadcastEntityEvent(this.entity, (byte) 21);
                        }
                    } else if (this.attackCooldown >= this.entity.getAttackDuration()) {
                        float f = 1.0F;
                        if (this.entity.level().getDifficulty() == Difficulty.HARD) {
                            f += 2.0F;
                        }

                        if (this.attackTime  == 0) {
                            performAttack(rayTraceResult, level, start, look, f);
                        } else if (this.attackTime % 20 == 0) {
                            performAttack(rayTraceResult, level, start, look, f);
                        }
                        if (this.attackTime >= 40) {

                            this.entity.setTarget((LivingEntity) null);
                        }
                        this.attackTime++;
                        /////////////////////////awdad

                    }

                    super.tick();
                }
            }
        }
    }

    static class RuneTurretAttackSelector implements Predicate<LivingEntity> {
        private final RuneTurretEntity entity;

        public RuneTurretAttackSelector(RuneTurretEntity pRuneTurret) {
            this.entity = pRuneTurret;
        }

        public boolean test(@Nullable LivingEntity pEntity) {
            return !(pEntity instanceof RuneTurretEntity) && pEntity.distanceToSqr(this.entity) > 9.0D;
        }
    }

    static class RuneTurretMoveControl extends MoveControl {
        private final RuneTurretEntity entity;
        public RuneTurretMoveControl(RuneTurretEntity pRuneTurret) {
            super(pRuneTurret);
            this.entity = pRuneTurret;
        }
//
//        public void tick() {
//            if (this.operation == MoveControl.Operation.MOVE_TO && !this.entity.getNavigation().isDone()) {
//                Vec3 vec3 = new Vec3(this.wantedX - this.entity.getX(), this.wantedY - this.entity.getY(), this.wantedZ - this.entity.getZ());
//                double d0 = vec3.length();
//                double d1 = vec3.x / d0;
//                double d2 = vec3.y / d0;
//                double d3 = vec3.z / d0;
//                float f = (float)(Mth.atan2(vec3.z, vec3.x) * (double)(180F / (float)Math.PI)) - 90.0F;
//                this.entity.setYRot(this.rotlerp(this.entity.getYRot(), f, 90.0F));
//                this.entity.yBodyRot = this.entity.getYRot();
//                float f1 = (float)(this.speedModifier * this.entity.getAttributeValue(Attributes.MOVEMENT_SPEED));
//                float f2 = Mth.lerp(0.125F, this.entity.getSpeed(), f1);
//                this.entity.setSpeed(f2);
//                double d4 = Math.sin((double)(this.entity.tickCount + this.entity.getId()) * 0.5D) * 0.05D;
//                double d5 = Math.cos((double)(this.entity.getYRot() * ((float)Math.PI / 180F)));
//                double d6 = Math.sin((double)(this.entity.getYRot() * ((float)Math.PI / 180F)));
//                double d7 = Math.sin((double)(this.entity.tickCount + this.entity.getId()) * 0.75D) * 0.05D;
//                this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(d4 * d5, d7 * (d6 + d5) * 0.25D + (double)f2 * d2 * 0.1D, d4 * d6));
//                LookControl lookcontrol = this.entity.getLookControl();
//                double d8 = this.entity.getX() + d1 * 2.0D;
//                double d9 = this.entity.getEyeY() + d2 / d0;
//                double d10 = this.entity.getZ() + d3 * 2.0D;
//                double d11 = lookcontrol.getWantedX();
//                double d12 = lookcontrol.getWantedY();
//                double d13 = lookcontrol.getWantedZ();
//                if (!lookcontrol.isLookingAtTarget()) {
//                    d11 = d8;
//                    d12 = d9;
//                    d13 = d10;
//                }
//
//                this.entity.getLookControl().setLookAt(Mth.lerp(0.125D, d11, d8), Mth.lerp(0.125D, d12, d9), Mth.lerp(0.125D, d13, d10), 10.0F, 40.0F);
////                this.entity.setMoving(true);
//            } else {
//                this.entity.setSpeed(0.0F);
////                this.entity.setMoving(false);
//            }
//        }
    }
}


//
//public class RuneTurretEntity extends Monster implements RangedAttackMob {
//    private static final int SHORT_LASER_COOLDOWN = 5;
//    private static final int SHORT_LASER_RANGE = 32;
//    private static final int SHORT_LASER_DAMAGE = 1;
//    public AnimationState departureAnimationState = new AnimationState();
//    public final AnimationState idleAnimationState = new AnimationState();
//    private int idleAnimationTimeOut = 0;
//    @Nullable
//    private LivingEntity clientSideCachedAttackTarget;
//    private boolean clientSideTouchedGround;
//    private int clientSideAttackTime;
//    private final HitResult laserHitResult = null;
//
//    public RuneTurretEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
//        super(pEntityType, pLevel);
//    }
//
//    @Override
//    protected void registerGoals() {
//        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
//        this.goalSelector.addGoal(0, new RuneTurretEntity.LaserAttackGoal(this, 0.0D, SHORT_LASER_COOLDOWN, SHORT_LASER_RANGE));
//        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true));
//    }
//
//    private void setupAnimationStates() {
//        if(this.idleAnimationTimeOut <= 0) {
//            this.idleAnimationTimeOut = this.random.nextInt(40) + 80;
//            this.idleAnimationState.start(this.tickCount);
//        } else {
//            --this.idleAnimationTimeOut;
//        }
//
//    }
//
//
//    public static AttributeSupplier.Builder createAttributes() {
//        return Animal.createLivingAttributes()
//                .add(Attributes.MAX_HEALTH, 20D)
//                .add(Attributes.ARMOR_TOUGHNESS, 0.2D)
//                .add(Attributes.ATTACK_KNOCKBACK, 20D)
//                .add(Attributes.MOVEMENT_SPEED, 0D)
//                .add(Attributes.FLYING_SPEED, 0D)
//                .add(Attributes.FOLLOW_RANGE, 24D)
//                .add(Attributes.KNOCKBACK_RESISTANCE, 100D);
//    }
//
//
//    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
//        if (DATA_POSE.equals(pKey)) {
//            switch (this.getPose()) {
//                case EMERGING:
//                    this.departureAnimationState.start(this.tickCount);
//                    break;
//            }
//        }
//        super.onSyncedDataUpdated(pKey);
//    }
//
//    @Override
//    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
//        double d0 = pTarget.getX();
//        double d1 = pTarget.getY(0.3333333333333333D);
//        double d2 = pTarget.getZ();
//
//        Level level = this.level();
//        Vec3 look = this.getViewVector(1);
//        Vec3 start = this.getEyePosition(1F);
//
//        Vec3 end = new Vec3(this.getX() + look.x * SHORT_LASER_RANGE, this.getEyeY() + look.y * SHORT_LASER_RANGE, this.getZ() + look.z * SHORT_LASER_RANGE);
//        ClipContext context = new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this);
//        pTarget.sendSystemMessage(Component.literal(String.valueOf(end)));
//        HitResult rayTraceResult = level.clip(context);
//
//        double traceDistance = rayTraceResult.getLocation().distanceToSqr(start);
//
//        AABB playerBox = this.getBoundingBox().expandTowards(look.scale(traceDistance)).expandTowards(1.0D, 1.0D, 1.0D);
//
//        for (Entity possible : level.getEntities(this, playerBox)) {
//            AABB entityBox = possible.getBoundingBox().inflate(0.3D); //.deflate(0.3D) or .inflate(0.3D) to scale hitbox
//            Optional<Vec3> optional = entityBox.clip(start, end);
//            if (optional.isPresent()) {
//                Vec3 position = optional.get();
//                double distance = start.distanceToSqr(position);
//
//                if (distance < traceDistance) {
//                    possible.setTicksFrozen(100);
//                    possible.hurt(this.damageSources().indirectMagic(this, pTarget), SHORT_LASER_DAMAGE);
//
//
//                }
//            }
//        }
//    }
//    protected BodyRotationControl createBodyControl() {
//        return new RuneTurretEntity.TurretBodyRotationControl(this);
//    }
//
//    static class TurretBodyRotationControl extends BodyRotationControl {
//        public TurretBodyRotationControl(Mob pMob) {super(pMob);}
//
//        public void clientTick() {
//        }
//    }
//
//    public boolean canBeCollidedWith() {
//        return this.isAlive();
//    }
//
//    @Override
//    public void tick() {
//        super.tick();
//        if (this.level().isClientSide()) {
//            switch (this.getPose()) {
//                case EMERGING:
//                    this.clientDiggingParticles(this.departureAnimationState);
//                    break;
//            }
//            setupAnimationStates();
//        }
//
//
//    }
//

//
//    @Nullable
//    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
//        this.setPose(Pose.EMERGING);
//        this.playSound(SoundEvents.WARDEN_DIG, 5.0F, 1.0F);
//        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
//    }
//
//    private void clientDiggingParticles(AnimationState pAnimationState) {
//        if ((float)pAnimationState.getAccumulatedTime() < 1000.0F) {
//            RandomSource randomsource = this.getRandom();
//            BlockState blockstate = this.getBlockStateOn();
//            if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
//                for(int i = 0; i < 30; ++i) {
//                    double d0 = this.getX() + .25 + (double) Mth.randomBetween(randomsource, -1F, 1F);
//                    double d1 = this.getY();
//                    double d2 = this.getZ() - .25 + (double)Mth.randomBetween(randomsource, -1F, 1F);
//                    this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0.0D, 0.0D, 0.0D);
//                }
//            }
//        }
//
//    }
//
//    public int getMaxHeadYRot() {
//        return 180;
//    }
//
//    static class LaserAttackGoal extends RangedAttackGoal {
//        private final RuneTurretEntity entity;
//        private int attackTime;
//
//        public LaserAttackGoal(RangedAttackMob pRangedAttackMob, double pSpeedModifier, int pAttackInterval, float pAttackRadius) {
//            super(pRangedAttackMob, pSpeedModifier, pAttackInterval, pAttackRadius);
//            this.entity = (RuneTurretEntity) pRangedAttackMob;
//        }
//
//        public void start() {
//            super.start();
//        }
//
//        public void stop() {
//            super.stop();
//        }
//
//    }
//}
//
//
