package com.flanks255.simplyutilities.commands;

import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.flanks255.simplyutilities.utils.MiscUtils;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.LevelData;

public class Spawn {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("spawn")
            .requires(cs -> ConfigCache.cmd_spawn)
            .executes(Spawn::spawn);
    }

    public static int spawn(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayerOrException();
        LevelData worldInfo = player.level.getLevelData();

        MiscUtils.Teleport(player, ctx.getSource().getServer().overworld(), worldInfo.getXSpawn(), worldInfo.getYSpawn(), worldInfo.getZSpawn(), player.getViewYRot(0), player.getViewXRot(0));
        return 0;
    }
}
