package com.chivasss.pocket_dimestions.block.custom;

import com.chivasss.pocket_dimestions.block.entity.testBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Arrays;

public class AltarBlock extends Block {
    public AltarBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide()) return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        int counter = 0;
        if (pLevel.getBlockEntity(pPos.east().below()) instanceof testBlockEntity TBE) {
            boolean[][] arr = TBE.getPixels();
            for (int y = 0; y < arr.length; y++) {
                for (int x = 0; x < arr[0].length; x++) {
                    if (arr[x][y]) counter++;
                }
            }

        }
        if (pLevel.getBlockEntity(pPos.west().below()) instanceof testBlockEntity TBE) {
            boolean[][] arr = TBE.getPixels();
            for (int y = 0; y < arr.length; y++) {
                for (int x = 0; x < arr[0].length; x++) {
                    if (arr[x][y]) counter++;
                }
            }

        }
        if (pLevel.getBlockEntity(pPos.north().below()) instanceof testBlockEntity TBE) {
            boolean[][] arr = TBE.getPixels();
            for (int y = 0; y < arr.length; y++) {
                for (int x = 0; x < arr[0].length; x++) {
                    if (arr[x][y]) counter++;
                }
            }

        }

        if (pLevel.getBlockEntity(pPos.south().below()) instanceof testBlockEntity TBE) {
            boolean[][] arr = TBE.getPixels();
            for (int y = 0; y < arr.length; y++) {
                for (int x = 0; x < arr[0].length; x++) {
                    if (arr[x][y]) counter++;
                }
            }

        }
        System.out.println(counter);
        if (counter >= 20) {
            pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX()+0.5, pPos.getY()+1, pPos.getZ()+0.5, Items.DIAMOND.getDefaultInstance()));
//            if (pLevel.getBlockEntity(pPos.south().below()) instanceof testBlockEntity TBE) TBE.clear();
//            if (pLevel.getBlockEntity(pPos.north().below()) instanceof testBlockEntity TBE) TBE.clear();
//            if (pLevel.getBlockEntity(pPos.west().below()) instanceof testBlockEntity TBE)  TBE.clear();
//            if (pLevel.getBlockEntity(pPos.east().below()) instanceof testBlockEntity TBE)  TBE.clear();
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

}
