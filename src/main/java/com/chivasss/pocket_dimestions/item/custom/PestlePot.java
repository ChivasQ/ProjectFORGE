package com.chivasss.pocket_dimestions.item.custom;

import com.chivasss.pocket_dimestions.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class PestlePot extends Item {
    public PestlePot(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
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
        ItemStack itemstack = entity.getItemInHand(hand);
        if (entity.getOffhandItem().getItem() == ModItems.PESTLE_STICK.get()) {
            entity.playSound(SoundEvents.SPYGLASS_USE, 1.0F, 1.0F);

            //Sounds
            return ItemUtils.startUsingInstantly(world, entity, hand);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }


    public int getUseDuration(ItemStack p_42933_) {
        return 100;
    }

    public UseAnim getUseAnimation(ItemStack p_40935_) {
        return UseAnim.BOW;
    }

//    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
//
//        player.awardStat(Stats.ITEM_USED.get(this));
////        return super.use(level, player, hand);
//        return ItemUtils.startUsingInstantly(level, player, hand);
//    }

    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        this.stopUsing(livingEntity);
        return itemStack;
    }

    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i) {
        this.stopUsing(livingEntity);
    }

    private void stopUsing(LivingEntity livingEntity) {
        livingEntity.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0F, 1.0F);
    }

}
