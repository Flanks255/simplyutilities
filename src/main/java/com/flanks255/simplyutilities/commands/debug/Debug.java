package com.flanks255.simplyutilities.commands.debug;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;

public class Debug {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("debug")
            .executes(Debug::debug)
            .then(Commands.literal("hand").executes(DebugHand::debug));
    }

    public static int debug(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayerOrException();
        return 0;
    }
}
