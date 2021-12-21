package com.flanks255.simplyutilities.homes;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.*;

public class PlayerHomes {
    private final UUID ID;
    private final String Name;
    private final Map<String, HomePoint> Homes;

    public PlayerHomes(UUID uuid, String playerName) {
        Name = playerName;
        ID = uuid;
        Homes = new HashMap<>();
    }

    public void setHome(String name, ResourceKey<Level> world, BlockPos pos) {
        setHome(name, world.location().toString(), pos);
    }
    public void setHome(String name, String world, BlockPos pos) {
        if (isHome(name)) {
            getHome(name).update(world, pos);
        }
        else {
            Homes.put(name, new HomePoint(name, world, pos));
        }
    }

    public HomePoint getHome(String name) {
        return Homes.get(name);
    }

    public boolean isHome(String name) {
        return Homes.containsKey(name);
    }

    public UUID getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public Set<String> getHomes() {
        return Homes.keySet();
    }

    public int getCount() {
        return Homes.size();
    }

    public boolean removeHome(String name) {
        if (isHome(name)) {
            Homes.remove(name);
            return true;
        }
        else
            return false;
    }

    public CompoundTag toNBT() {
        CompoundTag nbt = new CompoundTag();
        ListTag homeList = new ListTag();

        nbt.putUUID("UUID", ID);
        nbt.putString("Name", Name);
        for (Map.Entry<String, HomePoint> entry : Homes.entrySet()) {
            homeList.add(entry.getValue().toNBT());
        }
        nbt.put("Homes", homeList);

        return nbt;
    }

    public static Optional<PlayerHomes> fromNBT(CompoundTag nbt) {
        if (nbt.contains("UUID") && nbt.contains("Name")) {
            PlayerHomes player = new PlayerHomes(nbt.getUUID("UUID"), nbt.getString("Name"));

            if (nbt.contains("Homes")) {
                ListTag homes = nbt.getList("Homes", Tag.TAG_COMPOUND);
                for (int i = 0; i < homes.size(); i++) {
                    CompoundTag home = homes.getCompound(i);
                    if (home.contains("Name") && home.contains("WorldKey") && home.contains("Pos")) {
                        player.setHome(home.getString("Name"), home.getString("WorldKey"), BlockPos.of(home.getLong("Pos")));
                    }
                }
            }

            return Optional.of(player);
        }
        else
            return Optional.empty();
    }
}
