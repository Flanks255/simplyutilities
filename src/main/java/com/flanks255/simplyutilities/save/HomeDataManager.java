package com.flanks255.simplyutilities.save;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.homes.PlayerHomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomeDataManager extends SavedData {
    private static final String NAME = SimplyUtilities.MODID+"_homedata";
    private static final Map<UUID, PlayerHomes> data = new HashMap<>();

    public static HomeDataManager get(Level world) {
        ServerLevel serverWorld = world.getServer().overworld();
        return serverWorld.getDataStorage().computeIfAbsent(new Factory<>(HomeDataManager::new, HomeDataManager::load), NAME);
    }

    public static HomeDataManager load(CompoundTag nbt, HolderLookup.Provider registries) {
        if (nbt.contains("Players")) {
            ListTag players = nbt.getList("Players", Tag.TAG_COMPOUND);

            players.forEach(playerNBT -> PlayerHomes.fromNBT((CompoundTag) playerNBT).ifPresent(player -> data.put(player.getID(), player)));
        }

        return new HomeDataManager();
    }

    @Nonnull
    @Override
    public CompoundTag save(CompoundTag compound, HolderLookup.Provider registries) {
        ListTag players = new ListTag();

        data.forEach(((uuid, playerHomes) -> players.add(playerHomes.toNBT())));
        compound.put("Players", players);

        return compound;
    }


    public PlayerHomes getPlayerHomes(UUID uuid, String playername) {
        if (data.containsKey(uuid)) {
            return data.get(uuid);
        }
        else {
            PlayerHomes newPlayer = new PlayerHomes(uuid, playername);
            data.put(uuid, newPlayer);
            return newPlayer;
        }
    }
}
