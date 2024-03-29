package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.configuration.ClientConfiguration;
import com.flanks255.simplyutilities.configuration.ConfigCache;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record ZoomSmoothMessage(boolean smoothCam) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(SimplyUtilities.MODID, "smoothzoom");
    public ZoomSmoothMessage(final FriendlyByteBuf buffer) {
        this(buffer.readBoolean());
    }

    public boolean isSmoothCam() {
        return smoothCam;
    }

    public static void handle(final ZoomSmoothMessage message, final PlayPayloadContext ctx) {
            ctx.workHandler().submitAsync(
                () -> {
                    ClientConfiguration.ZOOMSMOOTHCAM.set(message.isSmoothCam());
                    ClientConfiguration.CLIENT_CONFIG.save();
                    ConfigCache.RefreshClientCache();
                }
            );
    }

    @Override
    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeBoolean(smoothCam);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
