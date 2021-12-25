package com.flanks255.simplyutilities.crafting;

import com.flanks255.simplyutilities.SUCrafting;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.server.ServerLifecycleHooks;


import javax.annotation.Nullable;
import java.util.List;

public class RightClickRecipe implements Recipe<Inventory> {
    public static String NAME = "right_click";
    private ResourceLocation id;
    private Ingredient input;
    private Block block;
    private ItemStack output;

    public RightClickRecipe(ResourceLocation recipeId, ItemStack result, Ingredient ingredient, Block blockIn) {
        id = recipeId;
        input = ingredient;
        output = result;
        this.block = blockIn;
    }


    public boolean matches(ItemStack itemInput, Block blockInput) {
        //return input.test(itemInput) && block.matchesBlock(blockInput);
        return false;
    }

    @Override
    public boolean matches(Inventory inv, Level worldIn) {
        return false; //Nah
    }

    @Override
    public ItemStack assemble(Inventory inv) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SUCrafting.RIGHT_CLICK_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return SUCrafting.Types.RIGHT_CLICK;
    }


    public static void RightClickEvent(PlayerInteractEvent.RightClickBlock event) {
        if(!event.getWorld().isClientSide && event.getPlayer() != null) {
            List<RightClickRecipe> rightClickRecipes = ServerLifecycleHooks.getCurrentServer().getRecipeManager().getAllRecipesFor(SUCrafting.Types.RIGHT_CLICK);
            final ItemStack held = event.getPlayer().getItemInHand(event.getHand());
            final Block block = event.getWorld().getBlockState(event.getPos()).getBlock();

            for (Recipe<?> recipe : rightClickRecipes) {
                if (recipe instanceof RightClickRecipe)
                    if (((RightClickRecipe) recipe).matches(held, block)) {
                        held.shrink(1);
                        ItemHandlerHelper.giveItemToPlayer(event.getPlayer(), recipe.getResultItem().copy());
                        event.setCanceled(true);
                        break;
                    }
            }
        }
    }


    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<RightClickRecipe> {

        @Override
        public RightClickRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(json.getAsJsonObject("input"));
            String blockname = json.get("block").getAsString();
            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockname));
            ItemStack result = new ItemStack(GsonHelper.getAsItem(json, "output"));

            return new RightClickRecipe(recipeId, result, ingredient, block);
        }

        @Nullable
        @Override
        public RightClickRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            String blockname = buffer.readUtf(32767);
            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockname));
            ItemStack result = buffer.readItem();

            return new RightClickRecipe(recipeId, result, ingredient, block);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, RightClickRecipe recipe) {
            recipe.input.toNetwork(buffer);
            buffer.writeUtf(recipe.block.getRegistryName().toString());
            buffer.writeItem(recipe.output);
        }
    }

    public static class FinishedRecipe implements net.minecraft.data.recipes.FinishedRecipe {
        private ResourceLocation id;
        private Ingredient input;
        private Block block;
        private ItemStack output;

        public FinishedRecipe(ResourceLocation id, ItemStack output, Ingredient input, Block block) {
            this.id = id;
            this.input = input;
            this.block = block;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("input", input.toJson());
            json.addProperty("block", block.getRegistryName().toString());
            json.addProperty("output", output.getItem().getRegistryName().toString());
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return SUCrafting.RIGHT_CLICK_RECIPE.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
