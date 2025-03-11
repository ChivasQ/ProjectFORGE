package com.chivasss.pocket_dimestions.entity.custom.sandworm;

import com.chivasss.pocket_dimestions.entity.ai.goals.RandomWanderGoal;
import com.chivasss.pocket_dimestions.util.SnakePoint;
import com.mojang.logging.LogUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Sandworm extends Monster {
    public final double[][] positions = new double[64][3];
    public int posMarker = -1;
    public List<SnakePoint> snakePath = new ArrayList<SnakePoint>();
    private float bodySpace = 0.5f;
    private SandwormPart[] bodies;
    private SandwormPart bone1;
    private SandwormPart bone2;
    private SandwormPart bone3;
    private SandwormPart bone4;
    private SandwormPart bone5;
    private SandwormPart bone6;
    private SandwormPart bone7;
    private SandwormPart bone8;
    private SandwormPart bone9;

    public Sandworm(EntityType<? extends Sandworm> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.bone1 = new SandwormPart(this, "bone1", 1.0F, 1.0F);
        this.bone2 = new SandwormPart(this, "bone2", 1.0F, 1.0F);
        this.bone3 = new SandwormPart(this, "bone3", 1.0F, 1.0F);
        this.bone4 = new SandwormPart(this, "bone4", 1.0F, 1.0F);
        this.bone5 = new SandwormPart(this, "bone5", 1.0F, 1.0F);
        this.bone6 = new SandwormPart(this, "bone6", 1.0F, 1.0F);
        this.bone7 = new SandwormPart(this, "bone7", 1.0F, 1.0F);
        this.bone8 = new SandwormPart(this, "bone8", 1.0F, 1.0F);
        this.bone9 = new SandwormPart(this, "bone9", 1.0F, 1.0F);
        this.bodies = new SandwormPart[]{
                this.bone1,
                this.bone2,
                this.bone3,
                this.bone4,
                this.bone5,
                this.bone6,
                this.bone7,
                this.bone8,
                this.bone9,
        };
        //this.noPhysics = true;
        this.setNoGravity(true);

        this.noCulling = true;
    }
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new RandomWanderGoal(this, 20));

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

    @Override
    public void aiStep() {
        super.aiStep();
        Vec3[] avec3 = new Vec3[this.bodies.length];
        for(int j = 0; j < this.bodies.length; ++j) {
            avec3[j] = new Vec3(this.bodies[j].getX(), this.bodies[j].getY(), this.bodies[j].getZ());
        }
        snakePath.add(0, new SnakePoint(this.getX(), this.getY(), this.getZ()));

        if (snakePath.size() > 500) {
            snakePath.remove(snakePath.size() - 1);
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
        }

        //this.setDeltaMovement(0.00, 0.00, 0.00);
        //this.move(MoverType.SELF, RandomWanderGoal.getWanderTarget().normalize().multiply(0.30,0.30,0.30));
    }

    private Vec3 getPositionAlongPath(double distance) {
        double traveled = 0.0;

        for (int i = 1; i < snakePath.size(); i++) {
            SnakePoint prev = snakePath.get(i - 1);
            SnakePoint curr = snakePath.get(i);

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
}
