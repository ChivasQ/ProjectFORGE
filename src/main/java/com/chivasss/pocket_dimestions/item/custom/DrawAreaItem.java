package com.chivasss.pocket_dimestions.item.custom;

import net.minecraft.world.item.Item;

public class DrawAreaItem extends Item {
    public DrawAreaItem(Properties properties) {

        super(properties);
    }
}
//        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
//            if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND){
//                entityInside(level, player);
//                //Cooldown
//                player.getCooldowns().addCooldown(this, 10);
//
//            }


//            return super.use(level, player, hand);

//}
//    public void entityInside(Level level, Entity entity) {
//            ResourceKey<Level> resourcekey = level.dimension() == ModDimensions.POCKET_KEY ? Level.OVERWORLD : ModDimensions.POCKET_KEY;
//            ServerLevel serverlevel = ((ServerLevel)level).getServer().getLevel(resourcekey);
//            if (serverlevel == null) {
//                return;
//
//            }
//
//            entity.changeDimension(serverlevel);
//        }
//
//    }


