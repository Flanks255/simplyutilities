package com.flanks255.simplyutilities.homes;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class HomePoint {
    private final String name;
    private String world;
    private BlockPos postion;

    public HomePoint(String name, String world, BlockPos pos) {
        this.name = name;
        this.world = world;
        this.postion = pos;
    }

    public RegistryKey<World> getWorldKey() {
        return RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(world));
    }

    public BlockPos getPostion() {
        return postion;
    }

    public String getName() {
        return name;
    }

    public void update(String newWorld, BlockPos newPos) {
        this.world = newWorld;
        this.postion = newPos;
    }

    public CompoundNBT toNBT() {
        CompoundNBT nbt = new CompoundNBT();

        nbt.putString("Name", name);
        nbt.putString("WorldKey", world);
        nbt.putLong("Pos", postion.toLong());

        return nbt;
    }

}
