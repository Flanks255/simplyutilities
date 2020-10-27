package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.tweaks.DoubleDoorFix;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenOtherDoorMessage {
    public OpenOtherDoorMessage(BlockPos pos) {
        blockPos = pos;
    }

    private BlockPos blockPos;

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public static OpenOtherDoorMessage decode(final PacketBuffer buffer) {
        return new OpenOtherDoorMessage(buffer.readBlockPos());
    }

    public static void encode(final OpenOtherDoorMessage message, final PacketBuffer buffer) {
        buffer.writeBlockPos(message.blockPos);
    }

    public static void handle(final OpenOtherDoorMessage message, final Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(
                () -> {
                    ServerPlayerEntity player = ctx.get().getSender();
                    if (player != null) {
                        World world = player.getEntityWorld();
                        DoubleDoorFix.openOtherDoor(world, message.getBlockPos());
                    }
                });
        ctx.get().setPacketHandled(true);
    }
}
