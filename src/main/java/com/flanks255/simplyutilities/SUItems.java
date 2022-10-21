package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.items.ExoLeggings;
import com.flanks255.simplyutilities.items.SUBlockItem;
import com.flanks255.simplyutilities.items.SUItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class SUItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimplyUtilities.MODID);

    public static final RegistryObject<Item> EXOLEGGINGS = ITEMS.register("exoleggings", ExoLeggings::new);
    public static final RegistryObject<Item> MINICOAL = ITEMS.register("mini_coal", () -> new SUItem(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MISC)){
        @Override
        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 200;
        }
    });
    public static final RegistryObject<Item> MINICHARCOAL = ITEMS.register("mini_charcoal", () -> new SUItem(new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MISC)){
        @Override
        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 200;
        }
    });

     //public static final RegistryObject<Item> CANISTER = SIMPLEITEMS.register("canister", FluidCanister::new);

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
