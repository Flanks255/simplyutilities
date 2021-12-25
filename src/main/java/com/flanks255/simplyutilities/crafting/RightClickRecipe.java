package com.flanks255.simplyutilities.crafting;

import com.flanks255.simplyutilities.SUCrafting;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.List;

public class RightClickRecipe implements IRecipe<IInventory> {
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
        return input.test(itemInput) && block.matchesBlock(blockInput);
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return false; //Nah
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return output;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SUCrafting.RIGHT_CLICK_RECIPE.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return SUCrafting.Types.RIGHT_CLICK;
    }


    public static void RightClickEvent(PlayerInteractEvent.RightClickBlock event) {
        if(!event.getWorld().isRemote && event.getPlayer() != null) {
            List<RightClickRecipe> rightClickRecipes = ServerLifecycleHooks.getCurrentServer().getRecipeManager().getRecipesForType(SUCrafting.Types.RIGHT_CLICK);
            final ItemStack held = event.getPlayer().getHeldItem(event.getHand());
            final Block block = event.getWorld().getBlockState(event.getPos()).getBlock();

            for (IRecipe<?> recipe : rightClickRecipes) {
                if (recipe instanceof RightClickRecipe)
                    if (((RightClickRecipe) recipe).matches(held, block)) {
                        held.shrink(1);
                        ItemHandlerHelper.giveItemToPlayer(event.getPlayer(), recipe.getRecipeOutput().copy());
                        event.setCanceled(true);
                        break;
                    }
            }
        }
    }


    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RightClickRecipe> {

        @Override
        public RightClickRecipe read(ResourceLocation recipeId, JsonObject json) {
            Ingredient ingredient = Ingredient.deserialize(json.getAsJsonObject("input"));
            String blockname = json.get("block").getAsString();
            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockname));
            ItemStack result = new ItemStack(JSONUtils.getItem(json, "output"));

            return new RightClickRecipe(recipeId, result, ingredient, block);
        }

        @Nullable
        @Override
        public RightClickRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient ingredient = Ingredient.read(buffer);
            String blockname = buffer.readString(32767);
            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockname));
            ItemStack result = buffer.readItemStack();

            return new RightClickRecipe(recipeId, result, ingredient, block);
        }

        @Override
        public void write(PacketBuffer buffer, RightClickRecipe recipe) {
            recipe.input.write(buffer);
            buffer.writeString(recipe.block.getRegistryName().toString());
            buffer.writeItemStack(recipe.output);
        }
    }

    public static class FinishedRecipe implements IFinishedRecipe {
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
        public void serialize(JsonObject json) {
            json.add("input", input.serialize());
            json.addProperty("block", block.getRegistryName().toString());
            json.addProperty("output", output.getItem().getRegistryName().toString());
        }

        @Override
        public ResourceLocation getID() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return SUCrafting.RIGHT_CLICK_RECIPE.get();
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return null;
        }
    }
}
