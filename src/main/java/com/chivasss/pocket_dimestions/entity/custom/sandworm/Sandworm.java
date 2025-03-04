package com.chivasss.pocket_dimestions.entity.custom.sandworm;

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
import software.bernie.shadowed.eliotlash.mclib.math.functions.utility.Lerp;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Sandworm extends Monster {
    private int tickCounter = 0;
    private static final Logger LOGGER = LogUtils.getLogger();
    public final double[][] positions = new double[64][3];
    public int posMarker = -1;
    public List<SnakePoint> snakePath = new ArrayList<SnakePoint>();
    private float bodySpace = 0.1f;
    private boolean moved;
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
    private SandwormPart bone10;
    public float yRotA;

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
        this.bone10 = new SandwormPart(this, "bone10", 2.0F, 2F);
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
                this.bone10};
        //this.noPhysics = true;
        //this.noCulling = true;
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

    @Nullable private Player unlimitedLastHurtByPlayer = null;
    public void aiStep() {
        // lastHurtByPlayer is cleared after 100 ticks, capture it indefinitely in unlimitedLastHurtByPlayer for LivingExperienceDropEvent
        if (this.lastHurtByPlayer != null) this.unlimitedLastHurtByPlayer = lastHurtByPlayer;
        if (this.unlimitedLastHurtByPlayer != null && this.unlimitedLastHurtByPlayer.isRemoved()) this.unlimitedLastHurtByPlayer = null;
        this.processFlappingMovement();
        if (this.level().isClientSide) {
            this.setHealth(this.getHealth());
        }

        if (this.isDeadOrDying()) {
            float f8 = (this.random.nextFloat() - 0.5F) * 8.0F;
            float f10 = (this.random.nextFloat() - 0.5F) * 4.0F;
            float f11 = (this.random.nextFloat() - 0.5F) * 8.0F;
            this.level().addParticle(ParticleTypes.EXPLOSION, this.getX() + (double)f8, this.getY() + 2.0D + (double)f10, this.getZ() + (double)f11, 0.0D, 0.0D, 0.0D);
        } else {
            Vec3 vec34 = this.getDeltaMovement();

            this.setYRot(Mth.wrapDegrees(this.getYRot()));
            if (!this.isNoAi()) {

                if (this.level().isClientSide) {
                    if (this.lerpSteps > 0) {
                        double d6 = this.getX() + (this.lerpX - this.getX()) / (double)this.lerpSteps;
                        double d0 = this.getY() + (this.lerpY - this.getY()) / (double)this.lerpSteps;
                        double d1 = this.getZ() + (this.lerpZ - this.getZ()) / (double)this.lerpSteps;
                        double d2 = Mth.wrapDegrees(this.lerpYRot - (double)this.getYRot());
                        this.setYRot(this.getYRot() + (float)d2 / (float)this.lerpSteps);
                        this.setXRot(this.getXRot() + (float)(this.lerpXRot - (double)this.getXRot()) / (float)this.lerpSteps);
                        --this.lerpSteps;
                        this.setPos(d6, d0, d1);
                        this.setRot(this.getYRot(), this.getXRot());
                    }

                } else {
                    Vec3 vec3 = new Vec3(100, 300,100);
                    if (vec3 != null) {
                        double d7 = vec3.x - this.getX();
                        double d8 = vec3.y - this.getY();
                        double d9 = vec3.z - this.getZ();
                        double d3 = d7 * d7 + d8 * d8 + d9 * d9;
                        float f4 = 1;
                        double d4 = Math.sqrt(d7 * d7 + d9 * d9);
                        if (d4 > 0.0D) {
                            d8 = Mth.clamp(d8 / d4, -f4, f4);
                        }

                        this.setDeltaMovement(this.getDeltaMovement().add(0.0D, d8/5, 0.0D));
                        this.setYRot(Mth.wrapDegrees(this.getYRot()));
                        Vec3 vec31 = vec3.subtract(this.getX(), this.getY(), this.getZ()).normalize();
                        Vec3 vec32 = (new Vec3(Mth.sin(this.getYRot() * ((float)Math.PI / 180F)), this.getDeltaMovement().y, -Mth.cos(this.getYRot() * ((float)Math.PI / 180F)))).normalize();
                        float f5 = Math.max(((float)vec32.dot(vec31) + 0.5F) / 1.5F, 0.0F);

                        if (Math.abs(d7) > (double)1.0E-5F || Math.abs(d9) > (double)1.0E-5F) {
                            float f6 = Mth.clamp(Mth.wrapDegrees(180.0F - (float)Mth.atan2(d7, d9) * (180F / (float)Math.PI) - this.getYRot()), -50.0F, 50.0F);
                            this.yRotA *= 0.8F;
                            this.yRotA += f6 * 1;
                            this.setYRot(this.getYRot() + this.yRotA * 0.1F);
                        }

                        float f19 = (float)(2.0D / (d3 + 1.0D));

                        this.moveRelative(0.06F * (f5 * f19 + (1.0F - f19)), new Vec3(0.0D, 0.0D, -1.0D));

                        this.move(MoverType.SELF, this.getDeltaMovement());

                        Vec3 vec33 = this.getDeltaMovement().normalize();
                        double d5 = 0.8D + 0.15D * (vec33.dot(vec32) + 1.0D) / 2.0D;
                        this.setDeltaMovement(this.getDeltaMovement().multiply(d5, 0.91F, d5));
                    }
                }
                Vec3[] avec3 = new Vec3[this.bodies.length];
                for(int j = 0; j < this.bodies.length; ++j) {
                    avec3[j] = new Vec3(this.bodies[j].getX(), this.bodies[j].getY(), this.bodies[j].getZ());
                }

                for(int k = 0; k < 6; ++k) {
                    SandwormPart enderdragonpart = null;
                    switch (k){
                        case 0: enderdragonpart = this.bone6; break;
                        case 1: enderdragonpart = this.bone5; break;
                        case 2: enderdragonpart = this.bone4; break;
                        case 3: enderdragonpart = this.bone3; break;
                        case 4: enderdragonpart = this.bone2; break;
                        case 5: enderdragonpart = this.bone1; break;
                    }

                    this.tickPart(enderdragonpart, 0, k, 0);
                }

                for(int l = 0; l < this.bodies.length; ++l) {
                    this.bodies[l].xo = avec3[l].x;
                    this.bodies[l].yo = avec3[l].y;
                    this.bodies[l].zo = avec3[l].z;
                    this.bodies[l].xOld = avec3[l].x;
                    this.bodies[l].yOld = avec3[l].y;
                    this.bodies[l].zOld = avec3[l].z;
                }

            }
        }
    }
    private void tickPart(SandwormPart pPart, double pOffsetX, double pOffsetY, double pOffsetZ) {
        pPart.setPos(this.getX() + pOffsetX, this.getY() + pOffsetY, this.getZ() + pOffsetZ);
    }




    
    public double[] getLatencyPos(int pBufferIndexOffset, float pPartialTicks) {
        pPartialTicks = 1.0F - pPartialTicks;
        int i = this.posMarker - pBufferIndexOffset & 63;
        int j = this.posMarker - pBufferIndexOffset - 1 & 63;
        double[] adouble = new double[3];
        double d0 = this.positions[i][0];
        double d1 = Mth.wrapDegrees(this.positions[j][0] - d0);
        adouble[0] = d0 + d1 * (double)pPartialTicks;
        d0 = this.positions[i][1];
        d1 = this.positions[j][1] - d0;
        adouble[1] = d0 + d1 * (double)pPartialTicks;
        adouble[2] = Mth.lerp((double)pPartialTicks, this.positions[i][2], this.positions[j][2]);
        return adouble;
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public PartEntity<?>[] getParts() {
        return this.bodies;
    }


    public boolean hurt(SandwormPart pPart, DamageSource pSource, float pDamage) {
        return true;
    }
}
