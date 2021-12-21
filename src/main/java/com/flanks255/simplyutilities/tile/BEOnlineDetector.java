package com.flanks255.simplyutilities.tile;

import com.flanks255.simplyutilities.SUBlocks;
import com.flanks255.simplyutilities.blocks.OnlineDetector;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.Util;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class BEOnlineDetector extends BlockEntity {
    public BEOnlineDetector(BlockPos pos, BlockState state) {
        super(SUBlocks.ONLINE_DETECTOR.getBlockEntityType(), pos, state);

        rand = new Random();
        tickOffset = rand.nextInt(interval);
        rand.setSeed(tickOffset);
    }
    private UUID uuid = Util.NIL_UUID;
    private String playerName = "";
    private final int tickOffset;
    private final int interval = 20;

    public boolean onlineState;
    public int baseEyeAngle = 0;
    public float currentEyeOffset, prevEyeOffset, eyeOffsetTarget = 0;
    public long targetTicks;
    public float ringAngle, prevRingAngle = 0;
    public final Random rand;

    //Bulk chunk data packet initial send to client
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();

        nbt.putUUID("PlacerUUID", uuid);
        nbt.putString("PlacerName", playerName);
        nbt.putBoolean("OnlineState", onlineState);
        return nbt;
    }
    @Override
    public void handleUpdateTag(CompoundTag nbt) {
        super.handleUpdateTag(nbt);

        uuid = nbt.getUUID("PlacerUUID");
        playerName = nbt.getString("PlacerName");
        onlineState = nbt.getBoolean("OnlineState");
    }

    // Update packet
    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("OnlineState", onlineState);
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag nbt = pkt.getTag();
        onlineState = nbt.getBoolean("OnlineState");
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);

        uuid = nbt.getUUID("PlacerUUID");
        playerName = nbt.getString("PlacerName");
        onlineState = nbt.getBoolean("OnlineState");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putUUID("PlacerUUID", uuid);
        nbt.putString("PlacerName", playerName);
        nbt.putBoolean("OnlineState", onlineState);
    }

    public static int wrapDegrees(int in) {
        if (in > 360)
            in -= 360;
        else if (in < 0)
            in += 360;

        return in;
    }

    public static <T extends BlockEntity> void clientTick(Level world, BlockPos blockPos, BlockState blockState, T t) {
        if (t instanceof BEOnlineDetector te) {
            if (world != null && world.isClientSide) {
                te.prevRingAngle = te.ringAngle;
                if (te.ringAngle < 360)
                    te.ringAngle += (360.0 / 80.0);
                if (te.ringAngle >= 360) {
                    te.ringAngle -= 360;
                    te.prevRingAngle -= 360;
                }

                if (te.targetTicks - world.getGameTime() >= 0) {
                    te.prevEyeOffset = te.currentEyeOffset;
                    te.currentEyeOffset = te.eyeOffsetTarget * (1 - ((te.targetTicks - world.getGameTime()) / 20.0f));
                } else
                    te.prevEyeOffset = te.currentEyeOffset;

            }
            if (world != null && world.isClientSide && (world.getGameTime() + te.tickOffset) % 80 == 0) {
                te.baseEyeAngle += te.eyeOffsetTarget;
                te.baseEyeAngle = wrapDegrees(te.baseEyeAngle);
                te.currentEyeOffset = te.prevEyeOffset = 0;
                te.eyeOffsetTarget = te.rand.nextInt(270) - 135;
                te.targetTicks = world.getGameTime() + 20;
            }
        }
    }

    public static <T extends BlockEntity> void serverTick(Level world, BlockPos blockPos, BlockState blockState, T t) {
        if (t instanceof BEOnlineDetector te) {
            if (world != null && !world.isClientSide && (world.getGameTime() + te.tickOffset) % te.interval == 0) {
                //SimplyUtilities.LOGGER.info("Tick, Offset: " + tickOffset + " Placed by: " + playerName);
                if (blockState.getBlock() instanceof OnlineDetector) {
                    boolean oldStatus = blockState.getValue(OnlineDetector.ON);
                    boolean newStatus = isOnline(world, te.uuid);
                    te.onlineState = newStatus;
                    if (oldStatus != newStatus) {
                        world.setBlock(te.worldPosition, blockState.setValue(OnlineDetector.ON, newStatus), 7);
                    }
                }
            }
        }
    }

    public static boolean isOnline(Level world, UUID uuidIn) {
        if (world != null && world.getServer() != null) {
            return world.getServer().getPlayerList().getPlayer(uuidIn) != null;
        } else
            return false;
    }

    public void setPlayer(Player player) {
        this.uuid = player.getUUID();
        this.playerName = player.getName().getString();
    }

    public UUID getPlayerUUID() {
        return uuid;
    }
    public String getPlayerName() {
        return playerName;
    }
}
