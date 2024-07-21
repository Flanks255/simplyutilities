package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.tweaks.DoubleDoorFix;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenOtherDoorPacket(BlockPos pos) implements CustomPacketPayload {
    public static final Type<OpenOtherDoorPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SimplyUtilities.MODID, "openotherdoor"));
    public static final StreamCodec<FriendlyByteBuf, OpenOtherDoorPacket> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, OpenOtherDoorPacket::getBlockPos, OpenOtherDoorPacket::new
    );

    public BlockPos getBlockPos() {
        return pos;
    }

    public static void handle(final OpenOtherDoorPacket message, final IPayloadContext ctx) {
        ctx.enqueueWork(
            () -> {
                Player player = ctx.player();
                if (player != null) {
                    Level world = player.getCommandSenderWorld();
                    DoubleDoorFix.openOtherDoor(world, message.getBlockPos());
                }
            });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
