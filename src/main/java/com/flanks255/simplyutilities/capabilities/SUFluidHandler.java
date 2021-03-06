package com.flanks255.simplyutilities.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
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

    public CompoundNBT toNBT() {
        CompoundNBT nbt = new CompoundNBT();

        nbt.putUniqueId("UUID", uuid);
        nbt.putInt("Capacity", capacity);
        if (!storedFluid.isEmpty()) {
            CompoundNBT fluidnbt = new CompoundNBT();
            storedFluid.writeToNBT(fluidnbt);
            nbt.put("Fluid", fluidnbt);
        }

        return nbt;
    }

    public static LazyOptional<SUFluidHandler> fromNBT(CompoundNBT nbt) {
        if(nbt.contains("UUID") && nbt.contains("Capacity")) {
            SUFluidHandler canHandler = new SUFluidHandler(nbt.getUniqueId("UUID"), nbt.getInt("Capacity"));
            canHandler.setStoredFluid(FluidStack.loadFluidStackFromNBT(nbt.getCompound("Fluid")));
            return LazyOptional.of(() -> canHandler);
        }
        return LazyOptional.empty();
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
    public int fill(FluidStack resource, FluidAction action) {
        int filled = 0;
        if (storedFluid.isEmpty()) {
            filled = Math.min(capacity, resource.getAmount());
            if (action.execute()) {
                storedFluid = resource.copy();
                storedFluid.setAmount(filled);
            }
        }
        else if (resource.isFluidEqual(storedFluid)) {
            filled = Math.min(resource.getAmount() + storedFluid.getAmount(), capacity) - storedFluid.getAmount();
            if (action.execute())
                storedFluid.grow(filled);
        }
        return filled;
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (!storedFluid.isEmpty() && resource.isFluidEqual(storedFluid)) {
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
    public FluidStack drain(int maxDrain, FluidAction action) {
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
