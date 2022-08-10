package com.flanks255.simplyutilities.commands.homes;

import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.flanks255.simplyutilities.save.HomeDataManager;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;


import java.util.Set;

public class ListHomes {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("list-homes")
                .requires(cs -> ConfigCache.cmd_home)
                .executes(ListHomes::list);
    }

    private static int list(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayerOrException();
        HomeDataManager homes = HomeDataManager.get(ctx.getSource().getLevel());

        Set<String> homenames = homes.getPlayerHomes(player.getUUID(), player.getDisplayName().toString()).getHomes();

        if (homenames.isEmpty()) {
            ctx.getSource().sendSuccess(Component.literal("[ ]"), false);
        }
        else {
            ctx.getSource().sendSuccess(Component.literal(homenames.toString()), false);
        }

        return 0;
    }
}
