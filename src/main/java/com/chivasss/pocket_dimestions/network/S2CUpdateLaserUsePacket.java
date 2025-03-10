package com.chivasss.pocket_dimestions.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CUpdateLaserUsePacket {
    private final boolean isInUse;
    private final int entityId;

    public S2CUpdateLaserUsePacket(int entityId, boolean isInUse) {
        this.entityId = entityId;
        this.isInUse = isInUse;
    }

    public S2CUpdateLaserUsePacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.isInUse = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeBoolean(isInUse);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Player player = (Player) Minecraft.getInstance().level.getEntity(entityId);
            if (player != null) {
                player.getPersistentData().putBoolean("isInUse", isInUse);
            }
        });
        return true;
    }
}
