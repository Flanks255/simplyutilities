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

public record ZoomFOVPacket(double fov) implements CustomPacketPayload {
    public static final Type<ZoomFOVPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SimplyUtilities.MODID, "zoomfov"));
    public static final StreamCodec<FriendlyByteBuf, ZoomFOVPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE, ZoomFOVPacket::fov, ZoomFOVPacket::new);

    public double getFov() {
        return fov;
    }

    public static void handle(final ZoomFOVPacket message, final IPayloadContext ctx) {
            ctx.enqueueWork(
                () -> {
                    ClientConfiguration.ZOOMFOV.set(message.getFov());
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
