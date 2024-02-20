package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record OpenDebugHandPacket(ItemStack itemStack) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(SimplyUtilities.MODID, "opendebughand");
    public OpenDebugHandPacket(FriendlyByteBuf buffer) {
        this(buffer.readItem());
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public static void handle(final OpenDebugHandPacket message, final PlayPayloadContext ctx) {
        ctx.workHandler().submitAsync(
            () -> ctx.player().ifPresent($ -> ClientPacketStuff.handleDebugHandMessage($, message)));
    }

    @Override
    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeItem(itemStack);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
