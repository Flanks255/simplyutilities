package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.crafting.CopyNBTRecipeShaped;
import com.flanks255.simplyutilities.crafting.RightClickRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SUCrafting {
    public static DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SimplyUtilities.MODID);

    public static final RegistryObject<RecipeSerializer<?>> COPYRECIPE = RECIPES.register(CopyNBTRecipeShaped.NAME, CopyNBTRecipeShaped.Serializer::new);

    public static final RegistryObject<RecipeSerializer<?>> RIGHT_CLICK_RECIPE = RECIPES.register(RightClickRecipe.NAME, RightClickRecipe.Serializer::new);




    public static void init(IEventBus bus) {
        RECIPES.register(bus);
    }

    public static class Types {
        public static final RecipeType<RightClickRecipe> RIGHT_CLICK = RecipeType.register(SimplyUtilities.MODID + ":right_click");
    }
}
