package com.flanks255.simplyutilities.commands;

import com.flanks255.simplyutilities.blocks.EnderInhibitor;
import com.flanks255.simplyutilities.save.InhibitorManager;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;

import java.util.HashSet;
import java.util.Set;

public class Inhibitor {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("inhibitor")
                .then(Commands.literal("list").executes(Inhibitor::list))
                .then(Commands.literal("check-orphans").executes(Inhibitor::checkOrphans));
    }

    public static int list(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayerOrException();
        ServerLevel world = (ServerLevel) player.level;

        Set<BlockPos> inhibitors = InhibitorManager.get(world).getInhibitors();

        if (inhibitors.isEmpty()) {
            ctx.getSource().sendSuccess(new TextComponent("[ ]"), false);
        }
        else {
            ctx.getSource().sendSuccess(new TextComponent(inhibitors.toString()), false);
        }

        return 0;
    }

    public static int checkOrphans(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayerOrException();
        ServerLevel world = (ServerLevel) player.level;

        InhibitorManager inhibitorManager = InhibitorManager.get(world);
        Set<BlockPos> inhibitors = inhibitorManager.getInhibitors();
        Set<BlockPos> removeset = new HashSet<>();

        for (BlockPos inhibitor : inhibitors) {
            if(!(world.getBlockState(inhibitor).getBlock() instanceof EnderInhibitor))
                removeset.add(inhibitor);
        }
        if (!removeset.isEmpty())
            inhibitorManager.removeInhibitors(removeset);

        ctx.getSource().sendSuccess(new TranslatableComponent("message.su.inhibitororphans", removeset.size()), false);

        return 0;
    }
}
