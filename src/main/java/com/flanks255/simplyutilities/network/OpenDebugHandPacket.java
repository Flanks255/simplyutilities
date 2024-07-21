package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenDebugHandPacket(ItemStack itemStack) implements CustomPacketPayload {
    public static final Type<OpenDebugHandPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SimplyUtilities.MODID, "opendebughand"));
    public static final StreamCodec<RegistryFriendlyByteBuf, OpenDebugHandPacket> CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, OpenDebugHandPacket::getItemStack, OpenDebugHandPacket::new
    );

    public ItemStack getItemStack() {
        return itemStack;
    }

    public static void handle(final OpenDebugHandPacket message, final IPayloadContext ctx) {
        ctx.enqueueWork(
            () -> ClientPacketStuff.handleDebugHandMessage(ctx.player(), message));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
