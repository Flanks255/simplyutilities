package com.flanks255.simplyutilities.commands.debug;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.network.OpenDebugHandMessage;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

public class DebugHand {

    public static int debug(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().asPlayer();

        // Check to make sure we are holding an item.
        if (player.getHeldItemMainhand().isEmpty()) {
            ctx.getSource().sendErrorMessage(new TranslationTextComponent("message.su.debug.hand.noitem"));
            return 0;
        }
        //SimplyUtilities.NETWORK.send(PacketDistributor.PLAYER.with(()-> player), new OpenDebugHandMessage(player.getHeldItemMainhand()));
        return 0;
    }
}
