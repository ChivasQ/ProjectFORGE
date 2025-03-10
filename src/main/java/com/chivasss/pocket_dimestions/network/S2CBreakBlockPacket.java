package com.chivasss.pocket_dimestions.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CBreakBlockPacket {
    private final BlockPos blockPos;
    private final int breakProgress;

    public S2CBreakBlockPacket(BlockPos blockPos, int breakProgress) {
        this.blockPos = blockPos;
        this.breakProgress = breakProgress;
    }
    public S2CBreakBlockPacket(FriendlyByteBuf buffer) {
        this.blockPos = buffer.readBlockPos();
        this.breakProgress = buffer.readInt();
    }
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(blockPos);
        buffer.writeInt(breakProgress);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        LocalPlayer player = Minecraft.getInstance().player;
        Level level = player.level();
        if (level == null) return;
        level.destroyBlockProgress(player.getId(), blockPos, breakProgress);
        //player.displayClientMessage(Component.literal(String.valueOf(breakProgress)), true);
    }

}
