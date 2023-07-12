package com.flanks255.simplyutilities.commands;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.network.ZoomFOVMessage;
import com.flanks255.simplyutilities.network.ZoomSmoothMessage;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

public class Zoom {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("zoom")
            .then(Commands.literal("setfov").then(Commands.argument("FOV", DoubleArgumentType.doubleArg(1.0d, 90.0d)).executes(cs -> setFOV(cs, DoubleArgumentType.getDouble(cs, "FOV")))))
            .then(Commands.literal("setsmooth").then(Commands.argument("enabled", BoolArgumentType.bool()).executes(cs -> setSmooth(cs, BoolArgumentType.getBool(cs, "enabled")))));
    }

    public static int setFOV(CommandContext<CommandSourceStack> ctx, double fov) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayerOrException();
        SimplyUtilities.NETWORK.send(PacketDistributor.PLAYER.with(() -> player), new ZoomFOVMessage(fov));
        ctx.getSource().sendSuccess(() -> Component.translatable("message.su.zoom.setfov", fov), false);
        return 0;
    }

    public static int setSmooth(CommandContext<CommandSourceStack> ctx, boolean smooth) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayerOrException();
        SimplyUtilities.NETWORK.send(PacketDistributor.PLAYER.with(() -> player), new ZoomSmoothMessage(smooth));
        ctx.getSource().sendSuccess(() -> Component.translatable("message.su.zoom.setsmooth", smooth), false);
        return 0;
    }
}
