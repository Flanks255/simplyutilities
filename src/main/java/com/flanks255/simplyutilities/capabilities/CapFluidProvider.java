package com.flanks255.simplyutilities.capabilities;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class CapFluidProvider implements ICapabilityProvider {
    ItemStack itemStack;
    LazyOptional<SUFluidHandler> fluidHandler = LazyOptional.empty();
    public CapFluidProvider(ItemStack item) {
        itemStack = item;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        return null;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return null;
    }
}
