package com.flanks255.simplyutilities.tile;

import com.flanks255.simplyutilities.SUBlocks;
import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.blocks.OnlineDetector;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Util;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class TEOnlineDetector extends TileEntity implements ITickableTileEntity {
    public TEOnlineDetector() {
        super(SUBlocks.ONLINE_DETECTOR.getTileEntityType());

        rand = new Random();
        tickOffset = rand.nextInt(interval);
        rand.setSeed(tickOffset);
    }
    private UUID uuid = Util.DUMMY_UUID;
    private String playerName = "";
    private final int tickOffset;
    private final int interval = 20;

    public boolean onlineState;
    public float oldEyeAngle, eyeAngle = 0;
    public float eyeOffset, eyeOffsetTarget = 0;
    public long targetTicks;
    public float ringAngle, prevRingAngle = 0;
    private final Random rand;

    //Bulk chunk data packet initial send to client
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();

        nbt.putUniqueId("PlacerUUID", uuid);
        nbt.putString("PlacerName", playerName);
        return nbt;
    }
    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        super.handleUpdateTag(state, nbt);

        uuid = nbt.getUniqueId("PlacerUUID");
        playerName = nbt.getString("PlacerName");
    }

    // Update packet
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return super.getUpdatePacket();
    }
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net, pkt);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);

        uuid = nbt.getUniqueId("PlacerUUID");
        playerName = nbt.getString("PlacerName");
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        nbt.putUniqueId("PlacerUUID", uuid);
        nbt.putString("PlacerName", playerName);

        return super.write(nbt);
    }

    @Override
    public void tick() {
        if (world != null && world.isRemote) {
            prevRingAngle = ringAngle;
            if (ringAngle < 360)
                ringAngle += (360.0 / 80.0);
            if (ringAngle >= 360) {
                ringAngle -= 360;
                prevRingAngle -= 360;
            }
            if (oldEyeAngle >= 360) {
                oldEyeAngle -= 360;
            } else if (oldEyeAngle < 0)
                oldEyeAngle += 360;

            if (targetTicks - world.getGameTime() >= 0) {
                eyeOffset = eyeOffsetTarget * (1 - ((targetTicks - world.getGameTime()) / 20.0f));
                eyeAngle = oldEyeAngle + eyeOffset;
            }

        }
        if (world != null && world.isRemote && (world.getGameTime() + tickOffset) % 80 == 0) {
            oldEyeAngle = eyeAngle;
            eyeOffset = 0;
            eyeOffsetTarget = rand.nextInt(360) - 180;
            targetTicks = world.getGameTime() + 20;
        }
        if (world != null && !world.isRemote && (world.getGameTime() + tickOffset) % interval == 0) {
            //SimplyUtilities.LOGGER.info("Tick, Offset: " + tickOffset + " Placed by: " + playerName);
            BlockState state = getBlockState();
            if (state.getBlock() instanceof OnlineDetector) {
                boolean oldStatus = state.get(OnlineDetector.ON);
                boolean newStatus = isOnline(uuid);
                onlineState = newStatus;
                if (oldStatus != newStatus) {
                    world.setBlockState(pos, state.with(OnlineDetector.ON, newStatus), 7);
                }
            }
        }
    }

    public boolean isOnline(UUID uuidIn) {
        if (world != null && world.getServer() != null) {
            return world.getServer().getPlayerList().getPlayerByUUID(uuidIn) != null;
        } else
            return false;
    }

    public void setPlayer(PlayerEntity player) {
        this.uuid = player.getUniqueID();
        this.playerName = player.getName().getString();
    }

    public UUID getPlayerUUID() {
        return uuid;
    }
    public String getPlayerName() {
        return playerName;
    }
}
