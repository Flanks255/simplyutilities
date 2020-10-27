package com.flanks255.simplyutilities.commands;

import com.flanks255.simplyutilities.blocks.EnderInhibitor;
import com.flanks255.simplyutilities.save.InhibitorManager;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.HashSet;
import java.util.Set;

public class Inhibitor {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("inhibitor")
                .then(Commands.literal("list").executes(Inhibitor::list))
                .then(Commands.literal("check-orphans").executes(Inhibitor::checkOrphans));
    }

    public static int list(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().asPlayer();
        ServerWorld world = player.getServerWorld();

        Set<BlockPos> inhibitors = InhibitorManager.get(world).getInhibitors();

        if (inhibitors.isEmpty()) {
            ctx.getSource().sendFeedback(new StringTextComponent("[ ]"), false);
        }
        else {
            ctx.getSource().sendFeedback(new StringTextComponent(inhibitors.toString()), false);
        }

        return 0;
    }

    public static int checkOrphans(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().asPlayer();
        ServerWorld world = player.getServerWorld();

        InhibitorManager inhibitorManager = InhibitorManager.get(world);
        Set<BlockPos> inhibitors = inhibitorManager.getInhibitors();
        Set<BlockPos> removeset = new HashSet<>();

        for (BlockPos inhibitor : inhibitors) {
            if(!(world.getBlockState(inhibitor).getBlock() instanceof EnderInhibitor))
                removeset.add(inhibitor);
        }
        if (!removeset.isEmpty())
            inhibitorManager.removeInhibitors(removeset);

        ctx.getSource().sendFeedback(new TranslationTextComponent("message.su.inhibitororphans", removeset.size()), false);

        return 0;
    }
}
