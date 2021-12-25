package com.flanks255.simplyutilities.crafting;

import com.flanks255.simplyutilities.SUCrafting;
import com.flanks255.simplyutilities.SimplyUtilities;
import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

public class CopyNBTRecipeShaped extends ShapedRecipe {
    public static String NAME = "copy_nbt";
    public static final Logger LOGGER = LogManager.getLogger();
    public CopyNBTRecipeShaped(ResourceLocation idIn, String groupIn, int recipeWidthIn, int recipeHeightIn, NonNullList<Ingredient> recipeItemsIn, ItemStack recipeOutputIn) {
        super(idIn, groupIn, recipeWidthIn, recipeHeightIn, recipeItemsIn, recipeOutputIn);
    }

    public CopyNBTRecipeShaped(ShapedRecipe shapedRecipe) {
        super(shapedRecipe.getId(), shapedRecipe.getGroup(), shapedRecipe.getRecipeWidth(), shapedRecipe.getRecipeHeight(), shapedRecipe.getIngredients(), shapedRecipe.getRecipeOutput());
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        final ItemStack craftingResult = super.getCraftingResult(inv);
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
            for (int i = 0; i < inv.getSizeInventory(); i++) {
                final ItemStack item = inv.getStackInSlot(i);
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
    public IRecipeSerializer<?> getSerializer() {
        return SUCrafting.COPYRECIPE.get();
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CopyNBTRecipeShaped> {
        @Nullable
        @Override
        public CopyNBTRecipeShaped read(ResourceLocation recipeId, PacketBuffer buffer) {
            return new CopyNBTRecipeShaped(IRecipeSerializer.CRAFTING_SHAPED.read(recipeId, buffer));
        }

        @Override
        public CopyNBTRecipeShaped read(ResourceLocation recipeId, JsonObject json) {
            try {
                return new CopyNBTRecipeShaped(IRecipeSerializer.CRAFTING_SHAPED.read(recipeId, json));
            }
            catch (Exception exception) {
                LOGGER.info("Error reading "+ NAME +" Recipe from packet: ", exception);
                throw exception;
            }
        }

        @Override
        public void write(PacketBuffer buffer, CopyNBTRecipeShaped recipe) {
            try {
                IRecipeSerializer.CRAFTING_SHAPED.write(buffer, recipe);
            }
            catch (Exception exception) {
                LOGGER.info("Error writing "+ NAME +" Recipe to packet: ", exception);
                throw exception;
            }
        }
    }
}
