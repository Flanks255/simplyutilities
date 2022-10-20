package com.flanks255.simplyutilities.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class MiscUtils {
    //Teleport helpers
    public static void Teleport(ServerPlayer player, ServerLevel targetLevel, BlockPos pos, float yRot, float xRot) {
        Teleport(player, targetLevel, Vec3.atBottomCenterOf(pos), yRot, xRot);
    }
    public static void Teleport(ServerPlayer player, ServerLevel targetLevel, int x, int y, int z, float yRot, float xRot) {
        Teleport(player, targetLevel, new Vec3(x + 0.5d, y, z + 0.5d), yRot, xRot);
    }
    public static void Teleport(ServerPlayer player, ServerLevel targetLevel, Vec3 pos, float yRot, float xRot) {
        player.teleportTo(targetLevel, pos.x, pos.y, pos.z, yRot, xRot);
        player.connection.send(new ClientboundSetExperiencePacket(player.experienceProgress, player.totalExperience, player.experienceLevel));
    }


}
