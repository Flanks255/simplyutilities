package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.configuration.ClientConfiguration;
import com.flanks255.simplyutilities.configuration.ConfigCache;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ZoomFOVMessage {
    public ZoomFOVMessage(double zoomfov) {
        fov = zoomfov;
    }

    public double getFov() {
        return fov;
    }

    private final double fov;


    public static ZoomFOVMessage decode(final FriendlyByteBuf buffer) {
        return new ZoomFOVMessage(buffer.readDouble());
    }

    public static void encode(final ZoomFOVMessage message, FriendlyByteBuf buffer) {
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
