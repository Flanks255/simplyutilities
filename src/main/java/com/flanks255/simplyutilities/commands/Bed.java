package com.flanks255.simplyutilities.commands;

import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class Bed {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("bed")
                .requires(cs -> ConfigCache.cmd_bed)
                .executes(Bed::toBed);
    }

    public static int toBed(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayerOrException();
        ServerLevel TargetWorld = player.getServer().getLevel(player.getRespawnDimension());
        BlockPos spawnPoint = player.getRespawnPosition();
        if (spawnPoint != null && TargetWorld != null)
            player.teleportTo(TargetWorld, spawnPoint.getX(), spawnPoint.getY(),spawnPoint.getZ(), player.getViewYRot(0), player.getViewXRot(0));
        return 0;
    }
}
