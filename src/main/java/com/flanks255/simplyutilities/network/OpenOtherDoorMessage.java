package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.tweaks.DoubleDoorFix;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenOtherDoorMessage {
    public OpenOtherDoorMessage(BlockPos pos) {
        blockPos = pos;
    }

    private final BlockPos blockPos;

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public static OpenOtherDoorMessage decode(final FriendlyByteBuf buffer) {
        return new OpenOtherDoorMessage(buffer.readBlockPos());
    }

    public static void encode(final OpenOtherDoorMessage message, final FriendlyByteBuf buffer) {
        buffer.writeBlockPos(message.blockPos);
    }

    public static void handle(final OpenOtherDoorMessage message, final Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(
            () -> {
                ServerPlayer player = ctx.get().getSender();
                if (player != null) {
                    Level world = player.getCommandSenderWorld();
                    DoubleDoorFix.openOtherDoor(world, message.getBlockPos());
                }
            });
        ctx.get().setPacketHandled(true);
    }
}
