package com.chivasss.pocket_dimestions.entity.ai.goals;


import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
public class RandomWanderGoal extends Goal {
    private Vec3 randomTarget;
    private int tickToChangeTarget;
    private final int _tickToChangeTarget;
    private final Mob mob;
    private float targetYaw;
    private float targetPitch;

    public RandomWanderGoal(Mob warm, int tickToChangeTarget) {
        this.mob = warm;
        findWanderTarget();
        this._tickToChangeTarget = tickToChangeTarget;
        this.targetYaw = warm.getYRot();
        this.targetPitch = warm.getXRot();
    }

    protected Vec3 findWanderTarget() {
        randomTarget = mob.position().add(Math.random() * 20 - 10, Math.random() * 20 - 10, Math.random() * 20 - 10);
        targetYaw = (float) (Math.atan2(randomTarget.z - mob.getZ(), randomTarget.x - mob.getX()) * (180F / Math.PI));
        targetPitch = (float) (-Math.atan2(randomTarget.y - mob.getY(), mob.position().distanceTo(randomTarget)) * (180F / Math.PI));
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
        float currentPitch = mob.getXRot();

        float newYaw = Mth.rotLerp(0.1F, currentYaw, targetYaw);
        float newPitch = Mth.rotLerp(0.1F, currentPitch, targetPitch);

        mob.setYRot(newYaw);
        mob.setYHeadRot(newYaw);
        mob.setXRot(newPitch);

        Vec3 lookDirection = Vec3.directionFromRotation(newPitch, newYaw).scale(0.04f);
        System.out.println(lookDirection.toString());
        //mob.getLookControl().setLookAt(lookDirection.normalize());
        mob.addDeltaMovement(lookDirection);
    }
}