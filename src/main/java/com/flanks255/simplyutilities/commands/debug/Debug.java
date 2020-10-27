package com.flanks255.simplyutilities.commands.debug;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

public class Debug {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("debug")
                .executes(Debug::debug)
                    .then(Commands.literal("hand").executes(DebugHand::debug));
    }

    public static int debug(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().asPlayer();

        return 0;
    }
}
