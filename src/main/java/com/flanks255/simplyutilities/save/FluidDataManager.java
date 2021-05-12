package com.flanks255.simplyutilities.save;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.capabilities.SUFluidHandler;
import com.flanks255.simplyutilities.items.FluidCanister;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FluidDataManager extends WorldSavedData {
    public FluidDataManager() {
        super(NAME);
    }
    private static final String NAME = SimplyUtilities.MODID + "_fluiddata";
    private static final Map<UUID, SUFluidHandler> handlers = new HashMap<>();

    public static FluidDataManager get(World world) {
        ServerWorld serverWorld = world.getServer().func_241755_D_();
        return serverWorld.getSavedData().getOrCreate(FluidDataManager::new, NAME);
    }

    //quick get handler, null if not found.
    @Nullable
    public SUFluidHandler getHandler(UUID uuid) {
        if (handlers.containsKey(uuid))
            return handlers.get(uuid);

        return null;
    }

    //get handler, create if not found.
    @Nonnull
    public SUFluidHandler getHandler(UUID uuid, int capacity) {
        if (handlers.containsKey(uuid))
            return handlers.get(uuid);

        SUFluidHandler newCan = new SUFluidHandler(uuid, capacity);
        handlers.put(uuid, newCan);
        return newCan;
    }

    public SUFluidHandler getHandler(ItemStack itemStack) {
        if (itemStack == null || itemStack.isEmpty() || !(itemStack.getItem() instanceof FluidCanister))
            return null;

        CompoundNBT nbt = itemStack.getOrCreateTag();
        UUID uuid;
        if (nbt.contains("UUID"))
            uuid = nbt.getUniqueId("UUID");
        else
        {
            uuid = UUID.randomUUID();
            nbt.putUniqueId("UUID", uuid);
            itemStack.setTag(nbt);
        }

        // replace this later with ability to get needed capacity from the item.
        return getHandler(uuid, 8000);
    }

    @Override
    public void read(CompoundNBT nbt) {
        if (nbt.contains("Handlers")) {
            ListNBT handlerNBT = nbt.getList("Handlers", Constants.NBT.TAG_COMPOUND);
            handlerNBT.forEach( (compound) -> SUFluidHandler.fromNBT((CompoundNBT)compound).ifPresent( (handler -> handlers.put(handler.getUuid(), handler)) ) );
        }
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        ListNBT handlersNBT = new ListNBT();

        handlers.forEach( (uuid, handlerData) -> handlersNBT.add(handlerData.toNBT()) );
        compound.put("Handlers", handlersNBT);

        return compound;
    }
}
