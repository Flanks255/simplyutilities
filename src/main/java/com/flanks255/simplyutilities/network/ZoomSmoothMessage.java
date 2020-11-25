package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.configuration.ClientConfiguration;
import com.flanks255.simplyutilities.configuration.ConfigCache;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ZoomSmoothMessage {
    public ZoomSmoothMessage(boolean setSmoothcam) {
        smoothcam = setSmoothcam;
    }

    public boolean isSmoothcam() {
        return smoothcam;
    }

    private boolean smoothcam;

    public static ZoomSmoothMessage decode(final PacketBuffer buffer) {
        return new ZoomSmoothMessage(buffer.readBoolean());
    }

    public static void encode(final ZoomSmoothMessage message, final PacketBuffer buffer) {
        buffer.writeBoolean(message.isSmoothcam());
    }

    public static void handle(final ZoomSmoothMessage message, final Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(
                    () -> {
                        ClientConfiguration.ZOOMSMOOTHCAM.set(message.isSmoothcam());
                        ClientConfiguration.CLIENT_CONFIG.save();
                        ConfigCache.RefreshCache();
                    }
            );
        }
        ctx.get().setPacketHandled(true);
    }
}
