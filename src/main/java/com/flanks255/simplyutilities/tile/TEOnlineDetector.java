package com.flanks255.simplyutilities.tile;

import com.flanks255.simplyutilities.SUBlocks;
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

        Random rand = new Random();
        tickOffset = rand.nextInt(interval);
    }
    private UUID uuid = Util.DUMMY_UUID;
    private String playerName = "";
    private final int tickOffset;
    private final int interval = 20;

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
        if (world != null && !world.isRemote && (world.getGameTime() + tickOffset) % interval == 0) {
            //SimplyUtilities.LOGGER.info("Tick, Offset: " + tickOffset + " Placed by: " + playerName);
            BlockState state = getBlockState();
            if (state.getBlock() instanceof OnlineDetector) {
                boolean oldStatus = state.get(OnlineDetector.ON);
                boolean newStatus = isOnline(uuid);

                if (oldStatus != newStatus) {
                    world.setBlockState(pos, state.with(OnlineDetector.ON, newStatus), 7);
                }
            }
        }
    }

    public boolean isOnline(UUID uuidIn) {
        if (world != null) {
            return world.getPlayerByUuid(uuidIn) != null;
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
