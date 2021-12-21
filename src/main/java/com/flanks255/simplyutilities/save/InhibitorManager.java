package com.flanks255.simplyutilities.save;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.configuration.ConfigCache;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class InhibitorManager extends SavedData {
    public InhibitorManager() {
    }

    public InhibitorManager(Set<BlockPos> set) {
        inhibitors.addAll(set);
    }

    public static String NAME = SimplyUtilities.MODID + "_inhibitors";
    private final Set<BlockPos> inhibitors = new HashSet<>();

    public static InhibitorManager get(ServerLevel world) {
        return world.getDataStorage().computeIfAbsent(InhibitorManager::load, InhibitorManager::new, NAME);
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
            if (inhibitor.distSqr(teleporterPos) < threshhold * threshhold)
                return true;
        }
        return false;
    }

    public static InhibitorManager load(CompoundTag nbt) {
        Set<BlockPos> inhibitors = new HashSet<>();
        if (nbt.contains("Inhibitors")) {
            ListTag positions = nbt.getList("Inhibitors", Tag.TAG_LONG);
            positions.forEach((pos) -> inhibitors.add(BlockPos.of(((LongTag)pos).getAsLong())));
        }
        return new InhibitorManager(inhibitors);
    }

    @Nonnull
    @Override
    public CompoundTag save(CompoundTag compound) {
        ListTag poslist = new ListTag();
        inhibitors.forEach((pos -> poslist.add(LongTag.valueOf(pos.asLong()))));

        compound.put("Inhibitors", poslist);
        return compound;
    }
}
