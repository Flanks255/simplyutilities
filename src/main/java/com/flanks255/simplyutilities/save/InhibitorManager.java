package com.flanks255.simplyutilities.save;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.configuration.ConfigCache;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class InhibitorManager extends WorldSavedData {
    public InhibitorManager() {
        super(NAME);
    }

    public static String NAME = SimplyUtilities.MODID + "_inhibitors";
    private final Set<BlockPos> inhibitors = new HashSet<>();

    public static InhibitorManager get(ServerWorld world) {
        return world.getSavedData().getOrCreate(InhibitorManager::new, NAME);
    }

    public void addInhibitor(BlockPos pos) {
        inhibitors.add(pos);
        setDirty(true);
    }

    public void removeInhibitor(BlockPos pos) {
        inhibitors.remove(pos);
        setDirty(true);
    }
    public void removeInhibitors(Set<BlockPos> remSet) {
        inhibitors.removeAll(remSet);
        setDirty(true);
    }

    public Set<BlockPos> getInhibitors() { return inhibitors; }

    public boolean InhibitorCloseEnough(BlockPos teleporterPos) {
        double threshhold = ConfigCache.EnderInhibitorRange;
        for (BlockPos inhibitor : inhibitors) {
            if (inhibitor.distanceSq(teleporterPos) < threshhold * threshhold)
                return true;
        }
        return false;
    }

    @Override
    public void read(CompoundNBT nbt) {
        if (nbt.contains("Inhibitors")) {
            inhibitors.clear();
            ListNBT positions = nbt.getList("Inhibitors", Constants.NBT.TAG_LONG);
            positions.forEach((pos) -> inhibitors.add(BlockPos.fromLong(((LongNBT)pos).getLong())));
        }
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        ListNBT poslist = new ListNBT();
        inhibitors.forEach((pos -> poslist.add(LongNBT.valueOf(pos.toLong()))));

        compound.put("Inhibitors", poslist);
        return compound;
    }
}
