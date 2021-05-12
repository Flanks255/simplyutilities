package com.flanks255.simplyutilities.blocks;

import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.flanks255.simplyutilities.save.InhibitorManager;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import javax.annotation.Nonnull;

public class EnderInhibitor extends Block {
    public EnderInhibitor() {
        super(AbstractBlock.Properties.create(new Material(
                MaterialColor.EMERALD,
                false,
                false,
                false,
                false,
                false,
                false,
                PushReaction.BLOCK
        )));
    }

    @Override
    public void onBlockAdded(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState oldState, boolean isMoving) {
        super.onBlockAdded(state, worldIn, pos, oldState, isMoving);

        if (!worldIn.isRemote)
            InhibitorManager.get((ServerWorld)worldIn).addInhibitor(pos);
    }

    @Override
    public void onBlockHarvested(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);

        if (!worldIn.isRemote)
            InhibitorManager.get((ServerWorld)worldIn).removeInhibitor(pos);
    }

    @Override
    public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, world, pos, explosion);

        if (!world.isRemote)
            InhibitorManager.get((ServerWorld)world).removeInhibitor(pos);
    }

    public static void TeleportEvent(EnderTeleportEvent event) {
        if (!ConfigCache.EnderInhibitorEnabled || (!ConfigCache.EnderInhibitorPlayers && event.getEntityLiving() instanceof PlayerEntity))
            return;

        World world = event.getEntityLiving().getEntityWorld();
        if (!world.isRemote && world instanceof ServerWorld) {
            if(InhibitorManager.get((ServerWorld) world).InhibitorCloseEnough(event.getEntityLiving().getPosition()))
                event.setCanceled(true);
        }
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return VoxelShapes.create(0,0,0.4,1,1,0.6);
    }
}
