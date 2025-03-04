package com.chivasss.pocket_dimestions.entity.custom.spidertron;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Spidertron extends Animal {
    public int LEGS_CLIP_FROM_HEIGHT = 3;
    public int LEGS_HEIGHT = 5;
    public int AMOUNT_OF_LEGS = 8;

    public Spidertron(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
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


    public int getAMOUNT_OF_LEGS() {
        return AMOUNT_OF_LEGS;
    }

    public int getLEGS_CLIP_FROM_HEIGHT() {
        return LEGS_CLIP_FROM_HEIGHT;
    }

    public int getLEGS_HEIGHT() {
        return LEGS_HEIGHT;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.isVehicle()) {
            player.startRiding(this);
            return super.mobInteract(player, hand);
        }
        return super.mobInteract(player, hand);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public void travel(Vec3 pos) {
        if (this.isAlive()) {
            if (this.isVehicle()) {
                LivingEntity passenger = getControllingPassenger();
                this.yRotO = getYRot();
                this.xRotO = getXRot();

                setYRot(passenger.getYRot());
                setXRot(passenger.getXRot() * 0.5f);
                setRot(getYRot(), getXRot());

                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float x = passenger.xxa;
                float z = passenger.zza;
                float y = passenger.yya;
                if (z <= 0)
                    z *= 0.25f;

                this.setSpeed(0.3f);
                super.travel(new Vec3(x, pos.y, z));
            }

        }
    }

    @Override
    public void tick() {
        Vec3 translate = this.getRotatedPoint(Vec3.ZERO, 180, Vec3.ZERO.add(1, 0, 0));
        Vec3 vec = this.getRotatedPoint(this.getPosition(1), 180, this.getPosition(1).add(5, 0, 0));
        Vec3 start = this.getPosition(1).add(translate);
        Vec3 end = this.getHeight(vec.add(0, 10, 0), vec.add(0, -10, 0), 0).getLocation();


        //level().addParticle(ParticleTypes.FLAME, );
        this.tickLerp();
        super.tick();
    }

    public void lerpTo(double pX, double pY, double pZ, float pYaw, float pPitch, int pPosRotationIncrements, boolean pTeleport) {
        this.lerpX = pX;
        this.lerpY = pY;
        this.lerpZ = pZ;
        this.lerpYRot = pYaw;
        this.lerpXRot = pPitch;
        this.lerpSteps = 5;
    }
    private void tickLerp() {
        if (this.isControlledByLocalInstance()) {
            this.lerpSteps = 0;
            this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
        }

        if (this.lerpSteps > 0) {
            double d0 = this.getX() + (this.lerpX - this.getX()) / (double)this.lerpSteps;
            double d1 = this.getY() + (this.lerpY - this.getY()) / (double)this.lerpSteps;
            double d2 = this.getZ() + (this.lerpZ - this.getZ()) / (double)this.lerpSteps;
            double d3 = Mth.wrapDegrees(this.lerpYRot - (double)this.getYRot());
            this.setYRot(this.getYRot() + (float)d3 / (float)this.lerpSteps);
            this.setXRot(this.getXRot() + (float)(this.lerpXRot - (double)this.getXRot()) / (float)this.lerpSteps);
            --this.lerpSteps;
            this.setPos(d0, d1, d2);
            this.setRot(this.getYRot(), this.getXRot());
        }
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return getFirstPassenger() instanceof LivingEntity entity ? entity : null;
    }

    @Override
    public boolean isControlledByLocalInstance() {
        return true;
    }

    @Override
    public void positionRider(Entity entity, MoveFunction moveFunction) {
        if (entity instanceof LivingEntity passenger) {
            moveFunction.accept(entity, getX(), getY() + 0.7f, getZ());

            this.xRotO = passenger.xRotO;
        }
    }

    public Vec3 getRotatedPoint(Vec3 center, double angle, Vec3 point){
        angle = Math.toRadians(angle);
        return new Vec3(
                Math.cos(angle) * (point.x - center.x) - Math.sin(angle) * (point.z - center.z) + center.x,
                center.y,
                Math.sin(angle) * (point.x - center.x) + Math.cos(angle) * (point.z - center.z) + center.z);
    }

    public HitResult getHeight(Vec3 start, Vec3 end, int max){
         return level().clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
    }

    public double[] find_intersection_points(double x0, double y0, double r0, double x1, double y1, double r1) {
        double d = Math.sqrt(Mth.square((x0 - x1) + Mth.square(y0 - y1)));
        if (d > (r0 + r1) || d < Math.abs(r0 - r1)){
            return new double[]{};
        }

        double a = (Mth.square(r0) - Mth.square(r1) + Mth.square(d))/(2 * d);
        double h = Math.sqrt(Mth.square(r0) - Mth.square(a));

        double x2 = x0 + a * (x1 - x0) / d;
        double y2 = y0 + a * (y1 - y0) / d;
        double x3 = x2 - h * (y1 - y0) / d;
        double y3 = y2 + h * (x1 - x0) / d;

        return new double[]{x2, y2, x3, y3};
    }

}
