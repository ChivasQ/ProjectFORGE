package com.chivasss.pocket_dimestions.network;

import com.chivasss.pocket_dimestions.PocketDim;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(PocketDim.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;
    private static int nextId() {
        return packetId++;
    }

    public static void register() {
        INSTANCE.messageBuilder(S2CBreakBlockPacket.class, nextId(), NetworkDirection.PLAY_TO_CLIENT)
                .encoder(S2CBreakBlockPacket::encode)
                .decoder(S2CBreakBlockPacket::new)
                .consumerMainThread(S2CBreakBlockPacket::handle)
                .add();
        INSTANCE.messageBuilder(S2CUpdateLaserUsePacket.class, nextId(), NetworkDirection.PLAY_TO_CLIENT)
                .encoder(S2CUpdateLaserUsePacket::toBytes)
                .decoder(S2CUpdateLaserUsePacket::new)
                .consumerMainThread(S2CUpdateLaserUsePacket::handle)
                .add();
        INSTANCE.messageBuilder(S2CSetCustomWeather.class, nextId(), NetworkDirection.PLAY_TO_CLIENT)
                .encoder(S2CSetCustomWeather::encode)
                .decoder(S2CSetCustomWeather::new)
                .consumerMainThread(S2CSetCustomWeather::handle)
                .add();
    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), msg);
    }

    public static void sendToAllClients(Object msg) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }

    public static void sendToPlayer(ServerPlayer player, Object msg) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

    public static void sendToTrackingPlayers(ServerPlayer source, Object msg) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> source), msg);
    }
}
