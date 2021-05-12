package com.flanks255.simplyutilities.homes;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

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

    public void setHome(String name, RegistryKey<World> world, BlockPos pos) {
        setHome(name, world.getLocation().toString(), pos);
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

    public CompoundNBT toNBT() {
        CompoundNBT nbt = new CompoundNBT();
        ListNBT homeList = new ListNBT();

        nbt.putUniqueId("UUID", ID);
        nbt.putString("Name", Name);
        for (Map.Entry<String, HomePoint> entry : Homes.entrySet()) {
            homeList.add(entry.getValue().toNBT());
        }
        nbt.put("Homes", homeList);

        return nbt;
    }

    public static Optional<PlayerHomes> fromNBT(CompoundNBT nbt) {
        if (nbt.contains("UUID") && nbt.contains("Name")) {
            PlayerHomes player = new PlayerHomes(nbt.getUniqueId("UUID"), nbt.getString("Name"));

            if (nbt.contains("Homes")) {
                ListNBT homes = nbt.getList("Homes", Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < homes.size(); i++) {
                    CompoundNBT home = homes.getCompound(i);
                    if (home.contains("Name") && home.contains("WorldKey") && home.contains("Pos")) {
                        player.setHome(home.getString("Name"), home.getString("WorldKey"), BlockPos.fromLong(home.getLong("Pos")));
                    }
                }
            }

            return Optional.of(player);
        }
        else
            return Optional.empty();
    }
}
