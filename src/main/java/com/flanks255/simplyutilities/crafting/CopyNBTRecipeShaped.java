package com.flanks255.simplyutilities.crafting;

import com.flanks255.simplyutilities.SUCrafting;
import com.flanks255.simplyutilities.SimplyUtilities;
import com.google.gson.JsonObject;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CopyNBTRecipeShaped extends ShapedRecipe {
    public static String NAME = "copy_nbt";
    public CopyNBTRecipeShaped(final String group, CraftingBookCategory category, ShapedRecipePattern pattern, final ItemStack recipeOutput) {
        super(group, category, pattern, recipeOutput);
    }

    public CopyNBTRecipeShaped(ShapedRecipe shapedRecipe) {
        super(shapedRecipe.getGroup(), shapedRecipe.category(), shapedRecipe.pattern, shapedRecipe.getResultItem(RegistryAccess.EMPTY));
    }
    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull CraftingInput inv, @Nonnull HolderLookup.Provider provider) {
        final ItemStack craftingResult = super.assemble(inv, provider);
        TargetNBTIngredient donorIngredient = null;
        ItemStack datasource = ItemStack.EMPTY;
        NonNullList<Ingredient> ingredients = getIngredients();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getCustomIngredient() instanceof TargetNBTIngredient) {
                donorIngredient = (TargetNBTIngredient) ingredient.getCustomIngredient();
                break;
            }
        }
        if (donorIngredient != null && !inv.isEmpty()) {
            for (int i = 0; i < inv.size(); i++) {
                final ItemStack item = inv.getItem(i);
                if (!item.isEmpty() && donorIngredient.test(item)) {
                    datasource = item;
                    break;
                }
            }
        }

        if (!datasource.isEmpty() && datasource.has(DataComponents.CUSTOM_DATA))
            craftingResult.set(DataComponents.CUSTOM_DATA, datasource.get(DataComponents.CUSTOM_DATA));

        return craftingResult;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return SUCrafting.COPYRECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<CopyNBTRecipeShaped> {
        private static final MapCodec<CopyNBTRecipeShaped> CODEC = ShapedRecipe.Serializer.CODEC.xmap(CopyNBTRecipeShaped::new, $ -> $);
        private static final StreamCodec<RegistryFriendlyByteBuf, CopyNBTRecipeShaped> STREAM_CODEC = ShapedRecipe.Serializer.STREAM_CODEC.map(CopyNBTRecipeShaped::new, CopyNBTRecipeShaped::new);
        @Override
        public @NotNull MapCodec<CopyNBTRecipeShaped> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CopyNBTRecipeShaped> streamCodec() {
            return STREAM_CODEC;
        }

    }
}
