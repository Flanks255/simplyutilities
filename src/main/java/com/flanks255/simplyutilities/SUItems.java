package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.items.ExoLeggings;
import com.flanks255.simplyutilities.items.SUBlockItem;
import com.flanks255.simplyutilities.items.SUItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SUItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SimplyUtilities.MODID);

    public static final DeferredItem<Item> EXOLEGGINGS = ITEMS.register("exoleggings", ExoLeggings::new);
    public static final DeferredItem<Item> MINICOAL = ITEMS.register("mini_coal", () -> new SUItem(new Item.Properties().stacksTo(64)){
        @Override
        public int getBurnTime(@Nonnull ItemStack itemStack, RecipeType<?> recipeType) {
            return 200;
        }
    });
    public static final DeferredItem<Item> MINICHARCOAL = ITEMS.register("mini_charcoal", () -> new SUItem(new Item.Properties().stacksTo(64)){
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
