package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.crafting.CopyNBTRecipeShaped;
import com.flanks255.simplyutilities.crafting.ItemstackFluidIngredient;
import com.flanks255.simplyutilities.crafting.RightClickRecipe;
import com.flanks255.simplyutilities.crafting.TargetNBTIngredient;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class SUCrafting {
    public static DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, SimplyUtilities.MODID);
    public static DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, SimplyUtilities.MODID);
    public static DeferredRegister<IngredientType<?>> INGREDIENTS = DeferredRegister.create(NeoForgeRegistries.Keys.INGREDIENT_TYPES, SimplyUtilities.MODID);
    public static DeferredHolder<IngredientType<?>, IngredientType<TargetNBTIngredient>> TARGET_INGREDIENT = INGREDIENTS.register("target_nbt", () -> new IngredientType<>(TargetNBTIngredient.CODEC));
    public static DeferredHolder<IngredientType<?>, IngredientType<ItemstackFluidIngredient>> FLUID_INGREDIENT = INGREDIENTS.register("fluid", () -> new IngredientType<>(ItemstackFluidIngredient.CODEC));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> COPYRECIPE = RECIPES.register(CopyNBTRecipeShaped.NAME, CopyNBTRecipeShaped.Serializer::new);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> RIGHT_CLICK_RECIPE = RECIPES.register(RightClickRecipe.NAME, RightClickRecipe.Serializer::new);




    public static void init(IEventBus bus) {
        RECIPES.register(bus);
        RECIPE_TYPES.register(bus);
        INGREDIENTS.register(bus);
        Types.init();
    }

    public static class Types {
        public static void init(){}
        public static final DeferredHolder<RecipeType<?>, RecipeType<RightClickRecipe>> RIGHT_CLICK = RECIPE_TYPES.register("right_click", () -> new RecipeType<>() {} );
    }
}
