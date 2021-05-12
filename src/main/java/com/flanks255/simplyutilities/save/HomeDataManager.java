package com.flanks255.simplyutilities.save;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.homes.PlayerHomes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomeDataManager extends WorldSavedData {
    public HomeDataManager() {
        super(NAME);
    }

    private static final String NAME = SimplyUtilities.MODID+"_homedata";
    private static final Map<UUID, PlayerHomes> data = new HashMap<>();

    public static HomeDataManager get(World world) {
        ServerWorld serverWorld = world.getServer().func_241755_D_();
        return serverWorld.getSavedData().getOrCreate(HomeDataManager::new, NAME);
    }

    @Override
    public void read(CompoundNBT nbt) {
        if (nbt.contains("Players")) {
            ListNBT players = nbt.getList("Players", Constants.NBT.TAG_COMPOUND);

            players.forEach(playerNBT -> PlayerHomes.fromNBT((CompoundNBT) playerNBT).ifPresent(player -> data.put(player.getID(), player)));
        }
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        ListNBT players = new ListNBT();

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
