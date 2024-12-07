package com.flanks255.simplyutilities.commands.debug;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;


public class DebugHand {

    public static int debug(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayerOrException();

        // Check to make sure we are holding an item.
        if (player.getMainHandItem().isEmpty()) {
            ctx.getSource().sendFailure(Component.translatable("message.su.debug.hand.noitem"));
            return 0;
        }
        //PacketDistributor.sendToPlayer(player, new OpenDebugHandPacket(player.getMainHandItem()));
        return 0;
    }
}
