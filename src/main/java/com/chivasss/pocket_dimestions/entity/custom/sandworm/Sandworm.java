package com.chivasss.pocket_dimestions.entity.custom.sandworm;

import com.chivasss.pocket_dimestions.entity.ai.goals.RandomWanderGoal;
import com.chivasss.pocket_dimestions.entity.ai.goals.RetreatGoal;
import com.chivasss.pocket_dimestions.entity.custom.rune_turret.RuneTurretEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class Sandworm extends Monster {
    public List<Vec3> path = new ArrayList<>();
    private float bodySpace = 0.65f;
    private SandwormPart[] bodies;
    public AttackPhase attackPhase = AttackPhase.WANDER;
    private Vec3 moveTargetPoint = Vec3.ZERO;

    public Sandworm(EntityType<? extends Sandworm> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        int segmentCount = 80;
        this.bodies = new SandwormPart[segmentCount];
        boolean n = false;
        for (int i = 0; i < segmentCount; i++) {
            this.bodies[i] = new SandwormPart(this, "bone" + (i + 1), n ? 1.0F : 0.75F, 1.0F);
            n = !n;
        }

        this.setNoGravity(true);
        //this.setGlowingTag(true);
        this.noPhysics = true;
        this.noCulling = true;
    }
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new RandomWanderGoal(this, 40));
        this.goalSelector.addGoal(2, new RetreatGoal(this, 40));
        //this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 1, false, false, new Sandworm.SandwormAttackSelector(this)));

    }
    protected BodyRotationControl createBodyControl() {
        return new SandwormBodyRotationControl(this);
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.2D)
                .add(Attributes.MOVEMENT_SPEED, 2D)

                .add(Attributes.ATTACK_KNOCKBACK, 20D)
                .add(Attributes.FOLLOW_RANGE, 50D)
                .add(Attributes.ATTACK_DAMAGE, 10D);
    }
    @Override
    public void aiStep() {
        super.aiStep();
        Vec3[] avec3 = new Vec3[this.bodies.length];
        for(int j = 0; j < this.bodies.length; ++j) {
            avec3[j] = new Vec3(this.bodies[j].getX(), this.bodies[j].getY(), this.bodies[j].getZ());
        }
        if(this.getDeltaMovement().lengthSqr() >= 0.001) {
            path.add(0, new Vec3(this.getX(), this.getY()+0.25, this.getZ()));
        }
        if (path.size() > 1000) {
            path.remove(path.size() - 1);
        }

        for (int i = 0; i < bodies.length; i++) {
            double offset = (i + 1) * bodySpace;

            Vec3 targetPos = getPositionAlongPath(offset);
            if (targetPos != null) {
                bodies[i].setPos(targetPos.x, targetPos.y, targetPos.z);
            }
            this.hurt(level().getEntities(this, bodies[i].getBoundingBox()));
//            } else {
//                bodies[i].setPos(this.getX()+i * bodySpace, this.getY(), this.getZ());
//            }
        }

        for(int l = 0; l < this.bodies.length; ++l) {
            this.bodies[l].xo = avec3[l].x;
            this.bodies[l].yo = avec3[l].y;
            this.bodies[l].zo = avec3[l].z;
            this.bodies[l].xOld = avec3[l].x;
            this.bodies[l].yOld = avec3[l].y;
            this.bodies[l].zOld = avec3[l].z;
            this.bodies[l].xRotO = 0;
            this.bodies[l].yRotO = 0;
        }

//        this.setDeltaMovement(0.02, 0.02, 0.02);
//        this.move(MoverType.SELF, this.getDeltaMovement());
    }
    private Vec3 getPositionAlongPath(double distance) {
        double traveled = 0.0;

        for (int i = 1; i < path.size(); i++) {
            Vec3 prev = path.get(i - 1);
            Vec3 curr = path.get(i);

            double segmentLength = prev.distanceTo(curr);
            traveled += segmentLength;

            if (traveled >= distance) {
                double excess = traveled - distance;
                double t = (segmentLength - excess) / segmentLength;

                double x = Mth.lerp(t, prev.x, curr.x);
                double y = Mth.lerp(t, prev.y, curr.y);
                double z = Mth.lerp(t, prev.z, curr.z);

                return new Vec3(x, y, z);
            }
        }
        return null;
    }
    @Override
    public boolean isMultipartEntity() {
        return true;
    }
    @Override
    public PartEntity<?>[] getParts() {
        return this.bodies;
    }
    @Override
    public boolean isNoGravity() {
        return true;
    }
    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (level().isClientSide()) return false;
        //System.out.println("Sandworm received damage: " + pAmount + " " + this.getHealth()); // Для отладки
        Entity entity = pSource.getEntity();
        if (entity == null)
            return false;
        return super.hurt(entity.damageSources().generic(), 2); // Применяем урон к основному телу
    }
    @Override
    public boolean isInWall() {
        return false;
    }
    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }
    @Override
    public boolean isPickable() {
        return true;
    }
    public boolean isPushable() {
        return false;
    }
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.5F;
    }

    class SandwormBodyRotationControl extends BodyRotationControl {
        public SandwormBodyRotationControl(Mob pMob) {
            super(pMob);
        }
        public void clientTick() {
            Sandworm.this.yHeadRot = Sandworm.this.yBodyRot;
            Sandworm.this.yBodyRot = Sandworm.this.getYRot();
        }
    }
    public enum AttackPhase {
        RETREAT,
        APPROACH,
        WANDER
    }

    @Override
    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
        this.gameEvent(GameEvent.ENTITY_DIE);
    }

    private void hurt(List<Entity> pEntities) {
        for(Entity entity : pEntities) {
            if (entity instanceof LivingEntity) {
                entity.hurt(this.damageSources().mobAttack(this), 10.0F);
                this.doEnchantDamageEffects(this, entity);
            }
        }

    }

    static class SandwormAttackSelector implements Predicate<LivingEntity> {
        private final Sandworm entity;

        public SandwormAttackSelector(Sandworm pRuneTurret) {
            this.entity = pRuneTurret;
        }

        public boolean test(@Nullable LivingEntity pEntity) {
            return !(pEntity instanceof RuneTurretEntity);
        }
    }
    class SandwormAttackPlayerTargetGoal extends Goal {
        private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0D);
        private int nextScanTick = reducedTickDelay(20);

        public boolean canUse() {
            if (this.nextScanTick > 0) {
                -- this.nextScanTick;
                System.out.println("a");
                return false;
            } else {

                this.nextScanTick = reducedTickDelay(60);
                List<Player> list = Sandworm.this.level().getNearbyPlayers(this.attackTargeting, Sandworm.this, Sandworm.this.getBoundingBox().inflate(64.0D, 64.0D, 64.0D));
                if (! list.isEmpty()) {
                    list.sort(Comparator.<Entity, Double>comparing(Entity::getY).reversed());

                    for (Player player : list) {
                        if (Sandworm.this.canAttack(player, TargetingConditions.DEFAULT)) {
                            Sandworm.this.setTarget(player);
                            //System.out.println("d");
                            return true;
                        }
                    }
                }

                return false;
            }
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = Sandworm.this.getTarget();
            return livingentity != null ? Sandworm.this.canAttack(livingentity, TargetingConditions.DEFAULT) : false;
        }
    }

    abstract class SandwormMoveTargetGoal extends Goal {
        public SandwormMoveTargetGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        protected boolean touchingTarget() {
            return Sandworm.this.moveTargetPoint.distanceToSqr(Sandworm.this.getX(), Sandworm.this.getY(), Sandworm.this.getZ()) < 4.0D;
        }
    }
}
