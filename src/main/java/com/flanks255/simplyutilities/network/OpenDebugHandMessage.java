package com.flanks255.simplyutilities.network;

import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenDebugHandMessage {
    public OpenDebugHandMessage(ItemStack itemStackIn) {
        itemStack = itemStackIn;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    ItemStack itemStack;


    public static OpenDebugHandMessage decode(final FriendlyByteBuf buffer) {
        return new OpenDebugHandMessage(buffer.readItem());
    }

    public static void encode(final OpenDebugHandMessage message, final FriendlyByteBuf buffer) {
        buffer.writeItem(message.getItemStack());
    }

    public static void handle(final OpenDebugHandMessage message, final Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(
            () -> {
                ClientPacketStuff.handleDebugHandMessage(ctx.get().getSender(), message);
            });
        ctx.get().setPacketHandled(true);
    }
}
