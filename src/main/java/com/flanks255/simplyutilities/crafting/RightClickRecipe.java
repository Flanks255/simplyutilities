package com.flanks255.simplyutilities.crafting;

import com.flanks255.simplyutilities.SUCrafting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
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

public record RightClickRecipe(Ingredient input, Block block, ItemStack output) implements Recipe<EmptyInput> {
    public static String NAME = "right_click";

    public boolean matches(ItemStack itemInput, Block blockInput) {
        return input.test(itemInput) && (block == blockInput);
    }

    @Override
    public boolean matches(@Nonnull EmptyInput inv, @Nonnull Level worldIn) {
        return false; //Nah
    }

    @Nonnull
    @Override
    public ItemStack assemble(EmptyInput input, HolderLookup.Provider registries) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem(@Nonnull HolderLookup.Provider registries) {
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
        public static final MapCodec<RightClickRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.fieldOf("input").forGetter(recipe -> recipe.input),
                BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(recipe -> recipe.block),
                ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output)
        ).apply(instance, RightClickRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, RightClickRecipe> STREAM_CODEC = StreamCodec.of(
                RightClickRecipe.Serializer::toNetwork,
                RightClickRecipe.Serializer::fromNetwork);

        @Nonnull
        public static RightClickRecipe fromNetwork(@Nonnull RegistryFriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            String blockName = buffer.readUtf(32767);
            Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(blockName));
            ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
            return new RightClickRecipe(ingredient, block, result);
        }

        @Nonnull
        @Override
        public MapCodec<RightClickRecipe> codec() {
            return CODEC;
        }

        @Nonnull
        @Override
        public StreamCodec<RegistryFriendlyByteBuf, RightClickRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static void toNetwork(@Nonnull RegistryFriendlyByteBuf buffer, RightClickRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input);
            buffer.writeUtf(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(recipe.block)).toString());
            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
        }
    }
}
