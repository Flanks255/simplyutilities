package com.flanks255.simplyutilities.commands;

import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class Bed {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("bed")
                .requires(cs -> ConfigCache.cmd_bed)
                .executes(Bed::toBed);
    }

    public static int toBed(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().asPlayer();
        ServerWorld TargetWorld = player.getServer().getWorld(player.func_241141_L_());
        BlockPos spawnPoint = player.func_241140_K_();
        if (spawnPoint != null && TargetWorld != null)
            player.teleport(TargetWorld, spawnPoint.getX(), spawnPoint.getY(),spawnPoint.getZ(), player.getYaw(0), player.getPitch(0));
        return 0;
    }
}
