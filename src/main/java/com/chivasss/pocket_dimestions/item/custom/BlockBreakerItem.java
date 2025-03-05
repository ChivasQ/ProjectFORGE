package com.chivasss.pocket_dimestions.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class BlockBreakerItem extends Item {
    public BlockBreakerItem(Properties properties) {
        super(properties);
    }
    int breakProgress = 1;
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            BlockHitResult hitResult = getPlayerPOVHitResult(world, player, ClipContext.Fluid.NONE);
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = hitResult.getBlockPos();
                player.displayClientMessage(Component.literal("Breaking: " + 1), true);
                world.destroyBlockProgress(player.getId(), pos, 1);

            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

}
