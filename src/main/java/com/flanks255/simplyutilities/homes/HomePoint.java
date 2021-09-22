package com.flanks255.simplyutilities.homes;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;

public class HomePoint {
    private final String name;
    private String world;
    private BlockPos postion;

    public HomePoint(String name, String world, BlockPos pos) {
        this.name = name;
        this.world = world;
        this.postion = pos;
    }

    public ResourceKey<Level> getWorldKey() {
        return ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(world));
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

    public CompoundTag toNBT() {
        CompoundTag nbt = new CompoundTag();

        nbt.putString("Name", name);
        nbt.putString("WorldKey", world);
        nbt.putLong("Pos", postion.asLong());

        return nbt;
    }

}
