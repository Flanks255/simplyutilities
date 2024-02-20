package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.tweaks.DoubleDoorFix;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record OpenOtherDoorPacket(BlockPos pos) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(SimplyUtilities.MODID, "openotherdoor");
    public OpenOtherDoorPacket(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos());
    }

    public BlockPos getBlockPos() {
        return pos;
    }

    public static void handle(final OpenOtherDoorPacket message, final PlayPayloadContext ctx) {
        ctx.workHandler().submitAsync(
            () -> {
                Player player = ctx.player().orElse(null);
                if (player != null) {
                    Level world = player.getCommandSenderWorld();
                    DoubleDoorFix.openOtherDoor(world, message.getBlockPos());
                }
            });
    }

    @Override
    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeBlockPos(pos);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
