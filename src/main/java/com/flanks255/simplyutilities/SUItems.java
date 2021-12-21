package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.items.ExoLeggings;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SUItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimplyUtilities.MODID);

    public static final RegistryObject<Item> EXOLEGGINGS = ITEMS.register("exoleggings", ExoLeggings::new);

     //public static final RegistryObject<Item> CANISTER = SIMPLEITEMS.register("canister", FluidCanister::new);

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
