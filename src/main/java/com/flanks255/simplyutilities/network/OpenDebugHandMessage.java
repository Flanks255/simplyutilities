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
        SimplyUtilities.LOGGER.info("Narf1");
        ctx.get().enqueueWork(
                () -> {
                    PlayerEntity player = ctx.get().getSender();
                    if (player != null && ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                        //gotta move this to server
                        //Minecraft.getInstance().displayGuiScreen(new DebugScreen(message.getItemStack()));
                        SimplyUtilities.LOGGER.info("Narf2");
                    }
                });
        ctx.get().setPacketHandled(true);
    }
}
