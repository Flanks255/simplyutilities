package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.configuration.ClientConfiguration;
import com.flanks255.simplyutilities.configuration.ConfigCache;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ZoomSmoothMessage(boolean smoothCam) implements CustomPacketPayload {
    public static final Type<ZoomSmoothMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SimplyUtilities.MODID, "smoothzoom"));
    public static final StreamCodec<FriendlyByteBuf, ZoomSmoothMessage> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, ZoomSmoothMessage::isSmoothCam, ZoomSmoothMessage::new
    );

    public boolean isSmoothCam() {
        return smoothCam;
    }

    public static void handle(final ZoomSmoothMessage message, final IPayloadContext ctx) {
            ctx.enqueueWork(
                () -> {
                    ClientConfiguration.ZOOMSMOOTHCAM.set(message.isSmoothCam());
                    ClientConfiguration.CLIENT_CONFIG.save();
                    ConfigCache.RefreshClientCache();
                }
            );
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
