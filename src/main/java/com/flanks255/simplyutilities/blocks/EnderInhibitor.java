package com.flanks255.simplyutilities.blocks;

import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.flanks255.simplyutilities.save.InhibitorManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;

import javax.annotation.Nonnull;

public class EnderInhibitor extends Block {
    public EnderInhibitor() {
        super(BlockBehaviour.Properties.of()
                .strength(2.0f)
                .noOcclusion()
                .pushReaction(PushReaction.BLOCK));
    }

    @Override
    public void onPlace(@Nonnull BlockState state, @Nonnull Level worldIn, @Nonnull BlockPos pos, @Nonnull BlockState oldState, boolean isMoving) {
        super.onPlace(state, worldIn, pos, oldState, isMoving);

        if (!worldIn.isClientSide)
            InhibitorManager.get((ServerLevel)worldIn).addInhibitor(pos);
    }

    @Nonnull
    @Override
    public BlockState playerWillDestroy(@Nonnull Level worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Player player) {
        BlockState ret = super.playerWillDestroy(worldIn, pos, state, player);

        if (!worldIn.isClientSide)
            InhibitorManager.get((ServerLevel)worldIn).removeInhibitor(pos);
        return ret;
    }

    @Override
    public void onBlockExploded(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull Explosion explosion) {
        super.onBlockExploded(state, world, pos, explosion);

        if (!world.isClientSide)
            InhibitorManager.get((ServerLevel)world).removeInhibitor(pos);
    }

    public static void TeleportEvent(EntityTeleportEvent.EnderEntity event) {
        if (!ConfigCache.EnderInhibitorEnabled || (!ConfigCache.EnderInhibitorPlayers && event.getEntityLiving() instanceof Player))
            return;

        Level world = event.getEntityLiving().getCommandSenderWorld();
        if (!world.isClientSide && world instanceof ServerLevel) {
            if(InhibitorManager.get((ServerLevel) world).InhibitorCloseEnough(event.getEntityLiving().blockPosition()))
                event.setCanceled(true);
        }
    }
    public static void PearlTeleportEvent(EntityTeleportEvent.EnderPearl event) {
        if (!ConfigCache.EnderInhibitorEnabled || !ConfigCache.EnderInhibitorPlayers)
            return;

        Level world = event.getPlayer().getCommandSenderWorld();
        if (!world.isClientSide && world instanceof ServerLevel) {
            if(InhibitorManager.get((ServerLevel) world).InhibitorCloseEnough(event.getPlayer().blockPosition()))
                event.setCanceled(true);
        }
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return Shapes.box(0,0,0.4,1,1,0.6);
    }
}
