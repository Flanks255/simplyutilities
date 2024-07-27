package com.flanks255.simplyutilities.homes;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class HomePoint {
    private final String name;
    private String world;
    private BlockPos position;

    public HomePoint(String name, String world, BlockPos pos) {
        this.name = name;
        this.world = world;
        this.position = pos;
    }

    public ResourceKey<Level> getWorldKey() {
        return ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(world));
    }

    public BlockPos getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public void update(String newWorld, BlockPos newPos) {
        this.world = newWorld;
        this.position = newPos;
    }

    public CompoundTag toNBT() {
        CompoundTag nbt = new CompoundTag();

        nbt.putString("Name", name);
        nbt.putString("WorldKey", world);
        nbt.putLong("Pos", position.asLong());

        return nbt;
    }

}
