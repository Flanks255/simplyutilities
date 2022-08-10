package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.configuration.ClientConfiguration;
import com.flanks255.simplyutilities.configuration.ConfigCache;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ZoomSmoothMessage {
    public ZoomSmoothMessage(boolean setSmoothcam) {
        smoothcam = setSmoothcam;
    }

    public boolean isSmoothcam() {
        return smoothcam;
    }

    private final boolean smoothcam;

    public static ZoomSmoothMessage decode(final FriendlyByteBuf buffer) {
        return new ZoomSmoothMessage(buffer.readBoolean());
    }

    public static void encode(final ZoomSmoothMessage message, final FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.isSmoothcam());
    }

    public static void handle(final ZoomSmoothMessage message, final Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(
                () -> {
                    ClientConfiguration.ZOOMSMOOTHCAM.set(message.isSmoothcam());
                    ClientConfiguration.CLIENT_CONFIG.save();
                    ConfigCache.RefreshClientCache();
                }
            );
        }
        ctx.get().setPacketHandled(true);
    }
}
