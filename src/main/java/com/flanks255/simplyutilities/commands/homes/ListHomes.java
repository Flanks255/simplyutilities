package com.flanks255.simplyutilities.commands.homes;

import com.flanks255.simplyutilities.save.HomeDataManager;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;


import java.util.Set;

public class ListHomes {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("list-homes")
                .requires(cs -> cs.hasPermissionLevel(0))
                .executes(ListHomes::list);
    }

    private static int list(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().asPlayer();
        HomeDataManager homes = HomeDataManager.get(ctx.getSource().getWorld());

        Set<String> homenames = homes.getPlayerHomes(player.getUniqueID(), player.getDisplayName().toString()).getHomes();

        if (homenames.isEmpty()) {
            ctx.getSource().sendFeedback(new StringTextComponent("[ ]"), false);
        }
        else {
            ctx.getSource().sendFeedback(new StringTextComponent(homenames.toString()), false);
        }

        return 0;
    }
}
