package com.chivasss.pocket_dimestions.item.custom;


import com.chivasss.pocket_dimestions.entity.client.BoreItemRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class Bore extends DiggerItem {
    private double distance = 0.0;
    private boolean isInUse = false;
    private float curBlockDamage = 0;
    public Bore(float pAttackDamageModifier, float pAttackSpeedModifier, Tier pTier, TagKey<Block> pBlocks, Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, pBlocks, pProperties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private BlockEntityWithoutLevelRenderer renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    renderer = new BoreItemRenderer();
                }

                return this.renderer;
            }
            private static final HumanoidModel.ArmPose EXAMPLE_POSE = HumanoidModel.ArmPose.create("EXAMPLE", false, (model, entity, arm) -> {
                if (arm == HumanoidArm.RIGHT) {
                    model.body.xRot = 112.5F;
                    model.body.yRot = 112.5F;
                    model.head.yRot = 112.5F;

                    model.rightArm.xRot = (float) (112.5);
                    model.rightArm.yRot = (float) (112.5);
                    model.leftArm.xRot = (float) (67.5);
                    model.leftArm.yRot = (float) (-112.5);
                }
            });

            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                if (!itemStack.isEmpty()) {
                    if (entityLiving.getUsedItemHand() == hand && entityLiving.getUseItemRemainingTicks() > 0) {
                        return EXAMPLE_POSE;
                    }
                }
                return HumanoidModel.ArmPose.EMPTY;
            }

            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                int i = arm == HumanoidArm.RIGHT ? 1 : -1;
                poseStack.translate(i * 0.56F, -0.52F, -0.72F);
                if (player.getUseItem() == itemInHand && player.isUsingItem()) {
                    poseStack.translate(-0.5, -0.5, -0.5);
                    poseStack.scale(2, 2, 2);
                }
                return true;
            }

        });
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        entity.playSound(SoundEvents.SPYGLASS_USE, 1.0F, 1.0F);
        ItemStack itemstack = entity.getItemInHand(hand);

        entity.startUsingItem(hand);
        return ItemUtils.startUsingInstantly(world, entity, hand);

    }

    @Override
    public void onUseTick(Level world, LivingEntity entity, ItemStack pStack, int pRemainingUseDuration) {
        Vec3 look = entity.getLookAngle();
        Vec3 start = entity.getEyePosition(1F);
        this.setInUse(true);
        double range = 64;
        if (!world.isClientSide) {
            Vec3 end = new Vec3(entity.getX() + look.x * range, entity.getEyeY() + look.y * range, entity.getZ() + look.z * range);
            ClipContext context = new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);

            BlockHitResult rayTraceResult = world.clip(context);

            double traceDistance = rayTraceResult.getLocation().distanceToSqr(start);
            this.setDistance(traceDistance);

            if (traceDistance <= range) {
                //world.destroyBlock(rayTraceResult.getBlockPos(), true);
                BlockState pState = world.getBlockState(rayTraceResult.getBlockPos());
                float relative = pState.getDestroyProgress((Player) entity, world, rayTraceResult.getBlockPos());
                curBlockDamage += relative;

                world.destroyBlockProgress(entity.getId(), rayTraceResult.getBlockPos(), (int) (curBlockDamage * 10.0F) - 1);

                curBlockDamage += 0.01F;
            }


        super.onUseTick(world, entity, pStack, pRemainingUseDuration);
        }
    }

    public int getUseDuration(ItemStack p_42933_) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack p_40935_) {
        return UseAnim.BOW;
    }

    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        this.stopUsing(livingEntity);
        this.setDistance(0);
        this.setInUse(false);
        return itemStack;
    }

    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i) {
        this.setDistance(0);
        this.setInUse(false);
        this.stopUsing(livingEntity);
    }

    private void stopUsing(LivingEntity livingEntity) {
        this.setDistance(0);
        this.setInUse(false);
        livingEntity.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0F, 1.0F);
    }

    public boolean isInUse() {
        return isInUse;
    }
    public void setInUse(boolean inUse) {
        isInUse = inUse;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }


}
