package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.configuration.ClientConfiguration;
import com.flanks255.simplyutilities.configuration.ConfigCache;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ZoomFOVMessage {
    public ZoomFOVMessage(double zoomfov) {
        fov = zoomfov;
    }

    public double getFov() {
        return fov;
    }

    private double fov;


    public static ZoomFOVMessage decode(final PacketBuffer buffer) {
        return new ZoomFOVMessage(buffer.readDouble());
    }

    public static void encode(final ZoomFOVMessage message, PacketBuffer buffer) {
        buffer.writeDouble(message.getFov());
    }

    public static void handle(final ZoomFOVMessage message, final Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(
                    () -> {
                        ClientConfiguration.ZOOMFOV.set(message.getFov());
                        ClientConfiguration.CLIENT_CONFIG.save();
                        ConfigCache.RefreshCache();
                    }
            );
        }
        ctx.get().setPacketHandled(true);
    }
}
