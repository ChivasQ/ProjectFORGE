package com.chivasss.pocket_dimestions.entity.custom.sandworm;

import com.chivasss.pocket_dimestions.entity.ai.goals.RandomWanderGoal;
import com.chivasss.pocket_dimestions.entity.ai.goals.SpiralWanderGoal;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;

import java.util.ArrayList;
import java.util.List;

public class Sandworm extends Monster {
    public List<Vec3> path = new ArrayList<>();
    private float bodySpace = 0.65f;
    private SandwormPart[] bodies;

    public Sandworm(EntityType<? extends Sandworm> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        int segmentCount = 40;
        this.bodies = new SandwormPart[segmentCount];
        boolean n = false;
        for (int i = 0; i < segmentCount; i++) {
//            this.bodies[i] = new SandwormPart(this, "joint" + (i + 1), .5F, .5F);
            this.bodies[i] = new SandwormPart(this, "bone" + (i + 1), n ? 1.0F : 0.75F, 1.0F);
            n = !n;
        }

        this.setNoGravity(true);
        this.setGlowingTag(true);
        this.noPhysics = true;
        this.noCulling = true;
    }
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new RandomWanderGoal(this, 40));
        //this.goalSelector.addGoal(0, new SpiralWanderGoal(this, 80, 5000, 0.01F, 1F));
        //this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));

    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.2D)
                .add(Attributes.ATTACK_KNOCKBACK, 20D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 100D)
                .add(Attributes.ATTACK_DAMAGE, 10D);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        Vec3[] avec3 = new Vec3[this.bodies.length];
        for(int j = 0; j < this.bodies.length; ++j) {
            avec3[j] = new Vec3(this.bodies[j].getX(), this.bodies[j].getY(), this.bodies[j].getZ());
        }
        if(this.getDeltaMovement() != Vec3.ZERO) {
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
    public boolean isInWall() {
        return false;
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }
}
