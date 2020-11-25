package com.flanks255.simplyutilities.commands;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.network.ZoomFOVMessage;
import com.flanks255.simplyutilities.network.ZoomSmoothMessage;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

public class Zoom {
    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("zoom")
                .then(Commands.literal("setfov").then(Commands.argument("FOV", DoubleArgumentType.doubleArg(1.0d, 180.0d)).executes(cs -> setFOV(cs, DoubleArgumentType.getDouble(cs, "FOV")))))
                .then(Commands.literal("setsmooth").then(Commands.argument("enabled", BoolArgumentType.bool()).executes(cs -> setSmooth(cs, BoolArgumentType.getBool(cs, "enabled")))));
    }

    public static int setFOV(CommandContext<CommandSource> ctx, double fov) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().asPlayer();
        SimplyUtilities.NETWORK.send(PacketDistributor.PLAYER.with(() -> player), new ZoomFOVMessage(fov));
        ctx.getSource().sendFeedback(new TranslationTextComponent("message.su.zoom.setfov", fov), false);
        return 0;
    }

    public static int setSmooth(CommandContext<CommandSource> ctx, boolean smooth) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().asPlayer();
        SimplyUtilities.NETWORK.send(PacketDistributor.PLAYER.with(() -> player), new ZoomSmoothMessage(smooth));
        ctx.getSource().sendFeedback(new TranslationTextComponent("message.su.zoom.setsmooth", smooth), false);
        return 0;
    }
}
