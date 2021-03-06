package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenDebugHandMessage {
    public OpenDebugHandMessage(ItemStack itemStackIn) {
        itemStack = itemStackIn;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    ItemStack itemStack;


    public static OpenDebugHandMessage decode(final PacketBuffer buffer) {
        return new OpenDebugHandMessage(buffer.readItemStack());
    }

    public static void encode(final OpenDebugHandMessage message, final PacketBuffer buffer) {
        buffer.writeItemStack(message.getItemStack());
    }

    public static void handle(final OpenDebugHandMessage message, final Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(
                () -> {
                    SimplyUtilities.NETPROXY.handleDebugHandMessage(ctx.get().getSender(), message);
                });
        ctx.get().setPacketHandled(true);
    }
}
