package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.crafting.CopyNBTRecipeShaped;
import com.flanks255.simplyutilities.crafting.RightClickRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SUCrafting {
    public static DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SimplyUtilities.MODID);

    public static final RegistryObject<IRecipeSerializer<?>> COPYRECIPE = RECIPES.register(CopyNBTRecipeShaped.NAME, CopyNBTRecipeShaped.Serializer::new);

    public static final RegistryObject<IRecipeSerializer<?>> RIGHT_CLICK_RECIPE = RECIPES.register(RightClickRecipe.NAME, RightClickRecipe.Serializer::new);




    public static void init(IEventBus bus) {
        RECIPES.register(bus);
    }

    public static class Types {
        public static final IRecipeType<RightClickRecipe> RIGHT_CLICK = IRecipeType.register(SimplyUtilities.MODID + ":right_click");
    }
}
