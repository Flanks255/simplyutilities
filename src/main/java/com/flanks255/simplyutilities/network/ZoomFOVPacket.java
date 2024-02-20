package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.configuration.ClientConfiguration;
import com.flanks255.simplyutilities.configuration.ConfigCache;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record ZoomFOVPacket(double fov) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(SimplyUtilities.MODID, "zoomfov");

    public ZoomFOVPacket(final FriendlyByteBuf buffer) {
        this(buffer.readDouble());
    }

    public double getFov() {
        return fov;
    }

    public static void handle(final ZoomFOVPacket message, final PlayPayloadContext ctx) {
            ctx.workHandler().submitAsync(
                () -> {
                    ClientConfiguration.ZOOMFOV.set(message.getFov());
                    ClientConfiguration.CLIENT_CONFIG.save();
                    ConfigCache.RefreshClientCache();
                }
            );
    }

    @Override
    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeDouble(fov);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
