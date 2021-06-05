package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.items.ExoLeggings;
import com.flanks255.simplyutilities.items.SUBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SUItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimplyUtilities.MODID);

    public static final RegistryObject<Item> EXOLEGGINGS = ITEMS.register("exoleggings", ExoLeggings::new);

     //public static final RegistryObject<Item> CANISTER = SIMPLEITEMS.register("canister", FluidCanister::new);

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
