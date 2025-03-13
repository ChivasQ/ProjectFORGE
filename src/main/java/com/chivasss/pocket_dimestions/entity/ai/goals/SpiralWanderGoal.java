package com.chivasss.pocket_dimestions.entity.ai.goals;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class SpiralWanderGoal extends Goal {
    private Vec3 randomTarget;
    private int tickToChangeTarget;
    private final int _tickToChangeTarget;
    private final Mob mob;
    private float angle;
    private float heightStep;
    private final float radius;
    private final float angularSpeed;
    public SpiralWanderGoal(Mob mob, int tickToChangeTarget, float radius, float angularSpeed, float heightStep) {
        this.mob = mob;
        this._tickToChangeTarget = tickToChangeTarget;
        this.tickToChangeTarget = tickToChangeTarget;
        this.radius = radius;
        this.angularSpeed = angularSpeed;
        this.heightStep = heightStep;
        this.angle = 0;
        findWanderTarget();
    }

    protected Vec3 findWanderTarget() {
        angle += angularSpeed;
        double xOffset = radius * Math.cos(angle);
        double zOffset = radius * Math.sin(angle);
        double yOffset = heightStep * angle;

        randomTarget = mob.position().add(xOffset, yOffset, zOffset);
        return randomTarget;
    }

    @Override
    public boolean canUse() {
        if (--tickToChangeTarget <= 0) {
            tickToChangeTarget = _tickToChangeTarget + mob.getRandom().nextIntBetweenInclusive(0, 20);
            findWanderTarget();
        }
        return mob.getTarget() == null;
    }

    public boolean canContinueToUse() {
        return this.canUse();
    }

    public void tick() {
        float currentYaw = mob.getYRot();
        float newYaw = Mth.rotLerp(0.1F, currentYaw, (float) Math.toDegrees(angle));

        mob.setYRot(newYaw);
        mob.setYHeadRot(newYaw);

        Vec3 direction = new Vec3(randomTarget.x - mob.getX(), randomTarget.y - mob.getY(), randomTarget.z - mob.getZ()).normalize().scale(0.05f);
        mob.addDeltaMovement(direction);
    }
}
