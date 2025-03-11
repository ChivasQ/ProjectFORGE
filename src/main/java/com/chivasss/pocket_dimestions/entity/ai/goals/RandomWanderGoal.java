package com.chivasss.pocket_dimestions.entity.ai.goals;


import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class RandomWanderGoal extends Goal {
    private static Vec3 randomTarget;
    private int tickToChangeTarget;
    private final int _tickToChangeTarget;
    private boolean randomDirection;
    private final Mob warm;

    public RandomWanderGoal(Mob warm, int tickToChangeTarget) {
        this.warm = warm;
        findWanderTarget();
        randomDirection = warm.getRandom().nextBoolean();
        this._tickToChangeTarget = tickToChangeTarget;
    }

    protected Vec3 findWanderTarget() {
        randomTarget = warm.position().add(Math.random() * 20 - 10, Math.random() * 20 - 8, Math.random() * 20 - 10);
        BlockPos pos = new BlockPos((int) randomTarget.x, (int) randomTarget.y, (int) randomTarget.z);
        int delta = 0;
        while (warm.level().getBlockState(pos).isAir() && pos.getY() > -65) {
            pos = pos.below();
            delta++;
        }
        float f0 = delta + warm.getRandom().nextIntBetweenInclusive(-3, 5);
        float f1 = f0 < -65 ? -130 - f0 : f0;
        randomTarget = randomTarget.subtract(0, f1, 0);

        randomDirection = !randomDirection;
        return randomTarget;
    }

    public static Vec3 getWanderTarget() {
        return randomTarget;
    }

    @Override
    public boolean canUse() {
        if (--tickToChangeTarget <= 0) {
            tickToChangeTarget = _tickToChangeTarget + warm.getRandom().nextIntBetweenInclusive(0, 20);
            findWanderTarget();
        }
        return warm.getTarget() == null;
    }

    public boolean canContinueToUse() {
        return this.canUse();
    }

    public void tick() {
        double distance = warm.distanceToSqr(randomTarget);

        if (distance > 16.0 * 16) {
            warm.lookAt(EntityAnchorArgument.Anchor.EYES, randomTarget);
//                warm.lookAt(EntityAnchorArgument.Anchor.EYES, randomTarget);
        }
        warm.addDeltaMovement(warm.getLookAngle().scale(0.04f));
    }
}