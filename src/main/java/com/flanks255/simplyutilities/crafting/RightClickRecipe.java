package com.flanks255.simplyutilities.crafting;

import com.flanks255.simplyutilities.SUCrafting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public record RightClickRecipe(Ingredient input, Block block, ItemStack output) implements Recipe<Inventory> {
    public static String NAME = "right_click";

    public boolean matches(ItemStack itemInput, Block blockInput) {
        return input.test(itemInput) && (block == blockInput);
    }

    @Override
    public boolean matches(@Nonnull Inventory inv, @Nonnull Level worldIn) {
        return false; //Nah
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull Inventory inv, @Nonnull RegistryAccess thing) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem(@Nonnull RegistryAccess thing) {
        return output;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return SUCrafting.RIGHT_CLICK_RECIPE.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return SUCrafting.Types.RIGHT_CLICK.get();
    }


    public static void RightClickEvent(PlayerInteractEvent.RightClickBlock event) {
        if(!event.getLevel().isClientSide && event.getEntity() != null) {
            List<RecipeHolder<RightClickRecipe>> rightClickRecipes = ServerLifecycleHooks.getCurrentServer().getRecipeManager().getAllRecipesFor(SUCrafting.Types.RIGHT_CLICK.get());
            final ItemStack held = event.getEntity().getItemInHand(event.getHand());
            final Block block = event.getLevel().getBlockState(event.getPos()).getBlock();

            for (RecipeHolder<RightClickRecipe> recipe : rightClickRecipes) {
                if (recipe != null)
                    if (recipe.value().matches(held, block)) {
                        held.shrink(1);
                        ItemHandlerHelper.giveItemToPlayer(event.getEntity(), recipe.value().getResultItem(RegistryAccess.EMPTY).copy());
                        event.setCanceled(true);
                        break;
                    }
            }
        }
    }


    public static class Serializer implements RecipeSerializer<RightClickRecipe> {
        public static final Codec<RightClickRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Ingredient.CODEC.fieldOf("input").forGetter(recipe -> recipe.input),
                BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(recipe -> recipe.block),
                ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output)
        ).apply(instance, RightClickRecipe::new));

        @Nonnull
        @Override
        public RightClickRecipe fromNetwork(@Nonnull FriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            String blockName = buffer.readUtf(32767);
            Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(blockName));
            ItemStack result = buffer.readItem();
            return new RightClickRecipe(ingredient, block, result);
        }

        @Nonnull
        @Override
        public Codec<RightClickRecipe> codec() {
            return CODEC;
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buffer, RightClickRecipe recipe) {
            recipe.input.toNetwork(buffer);
            buffer.writeUtf(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(recipe.block)).toString());
            buffer.writeItem(recipe.output);
        }
    }
}
