package com.flanks255.simplyutilities.crafting;

import com.flanks255.simplyutilities.SUCrafting;
import com.flanks255.simplyutilities.SimplyUtilities;
import com.google.gson.JsonObject;

import com.mojang.serialization.Codec;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
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
    public ItemStack assemble(@Nonnull CraftingContainer inv, RegistryAccess thing) {
        final ItemStack craftingResult = super.assemble(inv, thing);
        TargetNBTIngredient donorIngredient = null;
        ItemStack datasource = ItemStack.EMPTY;
        NonNullList<Ingredient> ingredients = getIngredients();
        for (Ingredient ingredient : ingredients) {
            if (ingredient instanceof TargetNBTIngredient) {
                donorIngredient = (TargetNBTIngredient) ingredient;
                break;
            }
        }
        if (donorIngredient != null && !inv.isEmpty()) {
            for (int i = 0; i < inv.getContainerSize(); i++) {
                final ItemStack item = inv.getItem(i);
                if (!item.isEmpty() && donorIngredient.test(item)) {
                    datasource = item;
                    break;
                }
            }
        }

        if (!datasource.isEmpty() && datasource.hasTag())
            craftingResult.setTag(datasource.getTag().copy());

        return craftingResult;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return SUCrafting.COPYRECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<CopyNBTRecipeShaped> {
        /*        private static final Codec<CopyBackpackDataRecipe> CODEC = RecordCodecBuilder.create($ -> $.group( // :(
                        ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(ShapedRecipe::getGroup),
                        CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(ShapedRecipe::category),
                        ShapedRecipePattern.MAP_CODEC.forGetter(a -> a.pattern),
                        ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(c -> c.getResultItem(RegistryAccess.EMPTY)),
                        ExtraCodecs.strictOptionalField(Codec.BOOL, "show_notification", false).forGetter(ShapedRecipe::showNotification)
                ).apply($, CopyBackpackDataRecipe::new));*/
        private static final Codec<CopyNBTRecipeShaped> CODEC = ShapedRecipe.Serializer.CODEC.xmap(CopyNBTRecipeShaped::new, $ -> $);
        @Override
        public @NotNull Codec<CopyNBTRecipeShaped> codec() {
            return CODEC;
        }

        @Override
        public @NotNull CopyNBTRecipeShaped fromNetwork(@NotNull FriendlyByteBuf pBuffer) {
            return new CopyNBTRecipeShaped(RecipeSerializer.SHAPED_RECIPE.fromNetwork(pBuffer));
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buffer, @Nonnull CopyNBTRecipeShaped recipe) {
            try {
                RecipeSerializer.SHAPED_RECIPE.toNetwork(buffer, recipe);
            }
            catch (Exception exception) {
                SimplyUtilities.LOGGER.info("Error writing CopyNBTRecipeShaped Recipe to packet: ", exception);
                throw exception;
            }
        }
    }
}
