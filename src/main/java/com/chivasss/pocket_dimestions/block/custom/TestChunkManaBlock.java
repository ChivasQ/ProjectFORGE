package com.chivasss.pocket_dimestions.block.custom;

import com.chivasss.pocket_dimestions.mana.data.ManaManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.BlockHitResult;

public class TestChunkManaBlock extends Block {
    public TestChunkManaBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide()) return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);

        int i = ManaManager.get(pLevel).getMana(pPos);
        pPlayer.displayClientMessage(Component.literal(String.valueOf(i)), true);
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
