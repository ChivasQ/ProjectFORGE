package com.chivasss.pocket_dimestions.item.custom;

import com.chivasss.pocket_dimestions.entity.client.BoreBlockEntityWithoutLevelRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Bore extends Item {
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return BoreBlockEntityWithoutLevelRenderer.instance;
            }
        });
    }

    public Bore(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        entity.playSound(SoundEvents.SPYGLASS_USE, 1.0F, 1.0F);
        ItemStack itemstack = entity.getItemInHand(hand);

        Vec3 look = entity.getLookAngle();
        Vec3 start = entity.getEyePosition(1F);
        double range = 32;
        if (!world.isClientSide) {
            Vec3 end = new Vec3(entity.getX() + look.x * range, entity.getEyeY() + look.y * range, entity.getZ() + look.z * range);
            ClipContext context = new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);

            HitResult rayTraceResult = world.clip(context);

            double traceDistance = rayTraceResult.getLocation().distanceToSqr(start);
            //entity.sendSystemMessage(Component.literal(String.valueOf(traceDistance)));
            if (traceDistance <= range) {

                itemstack.hurtAndBreak(1, entity, (p_40992_) -> {
                    p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                });

                entity.startUsingItem(hand);
                return InteractionResultHolder.consume(itemstack);
            } else {

            }

        }
        return InteractionResultHolder.fail(itemstack);
    }



    public int getUseDuration(ItemStack p_42933_) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack p_40935_) {
        return UseAnim.BOW;
    }

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
