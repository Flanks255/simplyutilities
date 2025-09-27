package com.flanks255.simplyutilities.commands;

import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.flanks255.simplyutilities.utils.MiscUtils;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class Bed {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("bed")
                .requires(cs -> ConfigCache.cmd_bed)
                .executes(Bed::toBed);
    }

    public static int toBed(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayerOrException();
        ServerLevel bedLevel = player.getServer().getLevel(player.getRespawnDimension());
        BlockPos bedPosition = player.getRespawnPosition();
        float angle = player.getRespawnAngle();

        if (bedPosition != null && bedLevel != null) {
            BlockState blockState = bedLevel.getBlockState(bedPosition);
            Block block = blockState.getBlock();

            if (block instanceof BedBlock && BedBlock.canSetSpawn(bedLevel)) {
                Optional<Vec3> pos = BedBlock.findStandUpPosition(EntityType.PLAYER, bedLevel, bedPosition, blockState.getValue(BedBlock.FACING), angle);
                if (pos.isPresent()) {
                    MiscUtils.Teleport(player, bedLevel, pos.get(), angle, 0.0f);
                    return 0;
                }
                // Fallback
                Optional<ServerPlayer.RespawnPosAngle> respawnPosition = blockState.getRespawnPosition(EntityType.PLAYER, bedLevel, bedPosition, player.getViewYRot(0));
            respawnPosition.ifPresentOrElse(
                    (p) -> MiscUtils.Teleport(player, bedLevel, p.position(), player.getViewYRot(0), player.getViewXRot(0)),
                    () -> MiscUtils.Teleport(player, bedLevel, bedPosition, player.getViewYRot(0), player.getViewXRot(0)));
            }
        }
        return 0;
    }
}
