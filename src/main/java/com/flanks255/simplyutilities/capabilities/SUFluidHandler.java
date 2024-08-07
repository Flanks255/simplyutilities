package com.flanks255.simplyutilities.capabilities;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public class SUFluidHandler implements IFluidHandler {

    UUID uuid;
    int capacity;
    private FluidStack storedFluid = FluidStack.EMPTY;

    public SUFluidHandler(UUID uuid, int capacity) {
        this.uuid = uuid;
        this.capacity = capacity;
    }
    public SUFluidHandler(UUID uuid) {
        this.uuid = uuid;
        this.capacity = 8000;
    }

    public UUID getUuid() {
        return uuid;
    }

    public CompoundTag toNBT(HolderLookup.Provider lookupProvider) {
        CompoundTag nbt = new CompoundTag();

        nbt.putUUID("UUID", uuid);
        nbt.putInt("Capacity", capacity);
        if (!storedFluid.isEmpty()) {
            nbt.put("Fluid", storedFluid.save(lookupProvider));
        }

        return nbt;
    }

    public static Optional<SUFluidHandler> fromNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
        if(nbt.contains("UUID") && nbt.contains("Capacity")) {
            SUFluidHandler canHandler = new SUFluidHandler(nbt.getUUID("UUID"), nbt.getInt("Capacity"));
            canHandler.setStoredFluid(FluidStack.parseOptional(lookupProvider, nbt.getCompound("Fluid")));
            return Optional.of(canHandler);
        }
        return Optional.empty();
    }

    public void setStoredFluid(FluidStack stack) {
        if (stack == null) {
            storedFluid = FluidStack.EMPTY;
        }
        else
        {
            storedFluid = stack.copy();
        }

    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        return storedFluid;
    }

    @Override
    public int getTankCapacity(int tank) {
        return capacity;
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
      return true;
    }

    @Override
    public int fill(@Nonnull FluidStack resource, @Nonnull FluidAction action) {
        int filled = 0;
        if (storedFluid.isEmpty()) {
            filled = Math.min(capacity, resource.getAmount());
            if (action.execute()) {
                storedFluid = resource.copy();
                storedFluid.setAmount(filled);
            }
        }
        else if (FluidStack.isSameFluidSameComponents(resource, storedFluid)) {
            filled = Math.min(resource.getAmount() + storedFluid.getAmount(), capacity) - storedFluid.getAmount();
            if (action.execute())
                storedFluid.grow(filled);
        }
        return filled;
    }

    @Nonnull
    @Override
    public FluidStack drain(@Nonnull FluidStack resource, @Nonnull FluidAction action) {
        if (!storedFluid.isEmpty() &&  FluidStack.isSameFluidSameComponents(resource, storedFluid)) {
            int drained = Math.min(storedFluid.getAmount(),resource.getAmount());
            FluidStack drainedFluid = storedFluid.copy();
            drainedFluid.setAmount(drained);
            if (action.execute())
                storedFluid.shrink(drained);
            return drainedFluid;
        }
        return FluidStack.EMPTY;
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, @Nonnull FluidAction action) {
        if (!storedFluid.isEmpty()) {
            int drained = Math.min(storedFluid.getAmount(), maxDrain);
            FluidStack drainedFluid = storedFluid.copy();
            drainedFluid.setAmount(drained);
            if (action.execute())
                storedFluid.shrink(drained);
            return drainedFluid;
        }
        return FluidStack.EMPTY;
    }
}
