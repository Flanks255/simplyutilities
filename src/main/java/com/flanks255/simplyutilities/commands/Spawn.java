package com.flanks255.simplyutilities.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.storage.IWorldInfo;

public class Spawn {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("spawn")
                .requires(cs -> cs.hasPermissionLevel(0))
                .executes(Spawn::spawn);
    }

    public static int spawn(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().asPlayer();
        IWorldInfo worldInfo = player.getServerWorld().getWorldInfo();

        player.teleport(ctx.getSource().getServer().func_241755_D_(), worldInfo.getSpawnX(), worldInfo.getSpawnY(), worldInfo.getSpawnZ(), player.getYaw(0), player.getPitch(0));
        return 0;
    }
}
