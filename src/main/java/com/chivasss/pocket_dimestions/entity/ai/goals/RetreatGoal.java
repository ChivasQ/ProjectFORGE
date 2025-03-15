package com.chivasss.pocket_dimestions.entity.ai.goals;


import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class RetreatGoal extends Goal {
    private Vec3 randomTarget;
    private final Mob mob;

    public RetreatGoal(Mob mob, int tickToChangeTarget) {
        this.mob = mob;
        retreatToTarget();
    }

    protected Vec3 retreatToTarget() {
        Vec3 pos = mob.position();
        Level level = mob.level();

        BlockPos moveToPos = mob.blockPosition().offset(level.random.nextInt(20) - 10, level.random.nextInt(20) - 10, level.random.nextInt(20) - 10);
        randomTarget = moveToPos.getCenter();

        return moveToPos.getCenter();
    }



    @Override
    public boolean canUse() {
        retreatToTarget();
        if (mob.getTarget() == null) return false;
        return mob.getTarget().distanceToSqr(this.mob.position()) <= 20;
    }

    public boolean canContinueToUse() {
        return this.canUse();
    }

    public void tick() {
//        Vec3 movementDirection = mob.getDeltaMovement();
//        if (movementDirection.lengthSqr() > 0.01) {
//            movementDirection = movementDirection.normalize();
//            mob.getLookControl().setLookAt(mob.getX() + movementDirection.x, mob.getY() + movementDirection.y, mob.getZ() + movementDirection.z);
//        } else {
//            mob.getLookControl().setLookAt(randomTarget.x, randomTarget.y, randomTarget.z);
//        }

        if (randomTarget == Vec3.ZERO) {
            mob.setDeltaMovement(Vec3.ZERO);
            return;
        }


        lookAt(mob, randomTarget, 10, 30);
        mob.addDeltaMovement(mob.getLookAngle().scale(0.1f));
    }


    public void lookAt(Mob mob, Vec3 target, float maxYRotIncrease, float maxXRotIncrease) {
        double dx = target.x - mob.getX();
        double dy = target.y - (mob.getY() + mob.getEyeHeight());
        double dz = target.z - mob.getZ();

        double d3 = Math.sqrt(dx * dx + dz * dz);
        float targetYaw = (float) (Mth.atan2(dz, dx) * (180F / Math.PI)) - 90.0F;
        float targetPitch = (float) -(Mth.atan2(dy, d3) * (180F / Math.PI));

        float newYaw = rotlerp(mob.getYRot(), targetYaw, maxYRotIncrease);
        float newPitch = rotlerp(mob.getXRot(), targetPitch, maxXRotIncrease);
        mob.setXRot(newPitch);
        mob.setYRot(newYaw);
        mob.setYHeadRot(newYaw);

        mob.getLookControl().setLookAt(target.x, target.y, target.z, maxYRotIncrease, 90);
    }

    private float rotlerp(float pAngle, float pTargetAngle, float pMaxIncrease) {
        float f = Mth.wrapDegrees(pTargetAngle - pAngle);
        if (f > pMaxIncrease) {
            f = pMaxIncrease;
        }

        if (f < -pMaxIncrease) {
            f = -pMaxIncrease;
        }

        return pAngle + f;
    }
}