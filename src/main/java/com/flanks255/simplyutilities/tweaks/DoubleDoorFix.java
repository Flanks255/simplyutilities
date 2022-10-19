package com.flanks255.simplyutilities.tweaks;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.flanks255.simplyutilities.network.OpenOtherDoorMessage;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;

public class DoubleDoorFix {

    public static void playerInteraction(PlayerInteractEvent.RightClickBlock interactEvent) {
        if (interactEvent.getEntity() instanceof FakePlayer || interactEvent.getEntity().isShiftKeyDown() || interactEvent.isCanceled() || interactEvent.getResult() == Event.Result.DENY)
            return;

        Level world = interactEvent.getLevel();
        BlockPos blockPos = interactEvent.getPos();

        if (world.getBlockState(blockPos).getBlock() instanceof DoorBlock) {
            openOtherDoor(world, blockPos);
            if (world.isClientSide)
                SimplyUtilities.NETWORK.sendToServer(new OpenOtherDoorMessage(blockPos));
        }
    }

    public static void openOtherDoor(Level world, BlockPos blockPos ) {
        if (!ConfigCache.doorFix || (SimplyUtilities.isQuarkLoaded && !ConfigCache.forceDoorFix))
            return;

        BlockState blockState = world.getBlockState(blockPos);
        Direction facing = blockState.getValue(DoorBlock.FACING);
        DoorHingeSide mirrored = blockState.getValue(DoorBlock.HINGE);
        boolean open = blockState.getValue(DoorBlock.OPEN);

        BlockPos mirroredPos = blockPos.relative(mirrored == DoorHingeSide.RIGHT ? facing.getCounterClockWise() : facing.getClockWise());
        BlockPos doorPos = blockState.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER ? mirroredPos : mirroredPos.below();
        BlockState otherDoor = world.getBlockState(doorPos);

        if (blockState.getMaterial() != Material.METAL && otherDoor.getBlock() == blockState.getBlock() && otherDoor.getValue(DoorBlock.FACING) == facing && otherDoor.getValue(DoorBlock.OPEN) == open && otherDoor.getValue(DoorBlock.HINGE) != mirrored) {
            world.setBlockAndUpdate(doorPos, otherDoor.cycle(DoorBlock.OPEN));
        }
    }
}
