package com.chivasss.pocket_dimestions.item.custom;

import com.chivasss.pocket_dimestions.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.stream.Stream;

public class AiTestItem extends Item {
    public AiTestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        pContext.getLevel().setBlock(pContext.getClickedPos(), ModBlocks.AI_TEST_BLOCK.get().defaultBlockState(), 1);
        return InteractionResult.CONSUME;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide()) return super.use(pLevel, pPlayer, pUsedHand);

        Vec3 pos = pPlayer.getPosition(1);
        BlockPos playerPos = new BlockPos((int) pos.x, (int) pos.y - 2, (int) pos.z);

        BlockPos.betweenClosedStream(
                        new BlockPos((int) pos.x - 50, (int) pos.y - 50, (int) pos.z - 50),
                        new BlockPos((int) pos.x + 50, (int) pos.y + 50, (int) pos.z + 50))
                .filter(blockPos -> pLevel.getBlockState(blockPos).getBlock() == ModBlocks.AI_TEST_BLOCK.get())
                .forEach(blockPos -> pLevel.destroyBlock(blockPos, false, pPlayer, 0));

        pLevel.setBlock(playerPos, ModBlocks.AI_TEST_BLOCK.get().defaultBlockState(), 1);

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public boolean isFoil(ItemStack pStack) {
        return true;
    }

}
