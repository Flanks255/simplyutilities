package com.flanks255.simplyutilities.tweaks;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.flanks255.simplyutilities.network.OpenOtherDoorMessage;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;

public class DoubleDoorFix {

    public static void playerInteraction(PlayerInteractEvent.RightClickBlock interactEvent) {
        if (interactEvent.getPlayer().isSneaking() || interactEvent.isCanceled() || interactEvent.getResult() == Event.Result.DENY)
            return;

        World world = interactEvent.getWorld();
        BlockPos blockPos = interactEvent.getPos();

        if (world.getBlockState(blockPos).getBlock() instanceof DoorBlock) {
            openOtherDoor(world, blockPos);
            if (world.isRemote)
                SimplyUtilities.NETWORK.sendToServer(new OpenOtherDoorMessage(blockPos));
        }
    }

    public static void openOtherDoor(World world, BlockPos blockPos ) {
        if (!ConfigCache.doorFix || (SimplyUtilities.isQuarkLoaded && !ConfigCache.forceDoorFix))
            return;

        BlockState blockState = world.getBlockState(blockPos);
        Direction facing = blockState.get(DoorBlock.FACING);
        DoorHingeSide mirrored = blockState.get(DoorBlock.HINGE);
        boolean open = blockState.get(DoorBlock.OPEN);

        BlockPos mirroredPos = blockPos.offset(mirrored == DoorHingeSide.RIGHT ? facing.rotateYCCW() : facing.rotateY());
        BlockPos doorPos = blockState.get(DoorBlock.HALF) == DoubleBlockHalf.LOWER ? mirroredPos : mirroredPos.down();
        BlockState otherDoor = world.getBlockState(doorPos);

        if (blockState.getMaterial() != Material.IRON && otherDoor.getBlock() == blockState.getBlock() && otherDoor.get(DoorBlock.FACING) == facing && otherDoor.get(DoorBlock.OPEN) == open && otherDoor.get(DoorBlock.HINGE) != mirrored) {
            world.setBlockState(doorPos, otherDoor.func_235896_a_(DoorBlock.OPEN));
        }
    }
}
