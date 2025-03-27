package com.chivasss.pocket_dimestions.item.custom;

import com.chivasss.pocket_dimestions.PocketDim;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class StructCreatorItem extends Item {
    public StructCreatorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level pLevel = pContext.getLevel();
        BlockPos pPos = pContext.getClickedPos();
        if (pLevel.isClientSide()) return super.useOn(pContext);

        ResourceLocation structureID = new ResourceLocation(PocketDim.MODID, "modfurnace");
        StructureTemplateManager manager = pLevel.getServer().getStructureManager();

//        Optional<StructureTemplate> optionalTemplate = manager.get(structureID);
//        if (optionalTemplate.isPresent()) {
//            StructureTemplate structure = optionalTemplate.get();
//
//
//            StructurePlaceSettings settings = new StructurePlaceSettings()
//                    .setMirror(Mirror.NONE)
//                    .setRotation(Rotation.NONE)
//                    .setIgnoreEntities(false);
//
//            structure.placeInWorld((ServerLevelAccessor) pLevel, pPos.north(2).below(1).west(1), pPos.north(2).below(1).west(1), settings, pLevel.random, 3);
//            for (int x = -1; x < structure.getSize().getX(); x++) {
//                for (int y = -1; y < structure.getSize().getY(); y++) {
//                    for (int z = -2; z < structure.getSize().getZ(); z++) {
//                        BlockPos blockPos = pPos.offset(x, y, z);
//                        pLevel.sendBlockUpdated(blockPos, pLevel.getBlockState(blockPos), pLevel.getBlockState(blockPos), 3);
//                    }
//                }
//            }
//        }

        return super.useOn(pContext);
    }
}
