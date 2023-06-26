package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SUBlocks;
import com.flanks255.simplyutilities.SUItems;
import com.flanks255.simplyutilities.SUTags;
import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.crafting.FluidIngredient;
import com.flanks255.simplyutilities.crafting.RightClickRecipe;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.AndCondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator generatorIn) {
        super(generatorIn.getPackOutput());
    }

    @Override
    protected void buildRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        ConditionalRecipe.builder()
            .addCondition(new BoolConfigCondition("craftLogsToSticks"))
            .addRecipe(
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STICK, 16)
                    .pattern("x")
                    .pattern("x")
                    .define('x', ItemTags.LOGS)
                    .showNotification(false)
                    .unlockedBy("has_logs", has(Items.OAK_LOG))::save)
            .generateAdvancement()
            .build(consumer, new ResourceLocation(SimplyUtilities.MODID, "stick_from_logs"));

        ConditionalRecipe.builder()
            .addCondition(new AndCondition(new NotCondition(new ModLoadedCondition("quark")), new BoolConfigCondition("craftLogsToChests")))
            .addRecipe(
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.CHEST, 4)
                    .pattern("xxx")
                    .pattern("x x")
                    .pattern("xxx")
                    .define('x', ItemTags.LOGS)
                    .showNotification(false)
                    .unlockedBy("has_logs", has(Items.OAK_LOG))::save)
            .generateAdvancement()
            .build(consumer, new ResourceLocation(SimplyUtilities.MODID, "chests_from_logs"));

        ConditionalRecipe.builder()
            .addCondition(new BoolConfigCondition("smeltFleshIntoLeather"))
            .addRecipe(
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.ROTTEN_FLESH), RecipeCategory.MISC, Items.LEATHER, 0.0F, 400)
                    .unlockedBy("has_flesh", has(Items.ROTTEN_FLESH))::save)
            .generateAdvancement()
            .build(consumer, new ResourceLocation(SimplyUtilities.MODID, "leather_from_flesh"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STICKY_PISTON)
            .pattern("x")
            .pattern("y")
            .define('x', Tags.Items.SLIMEBALLS)
            .define('y', Items.PISTON)
            .showNotification(false)
            .unlockedBy("hasSlimeBall", has(Items.SLIME_BALL))
            .save(consumer,new ResourceLocation(SimplyUtilities.MODID, "sticky_piston"));

        // Ender inhibitor
        ConditionalRecipe.builder()
            .addCondition(new BoolConfigCondition("enableEnderInhibitor"))
            .addRecipe(
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SUBlocks.ENDER_INHIBITOR.getItem())
                    .pattern(" x ")
                    .pattern(" b ")
                    .pattern("aca")
                    .define('x', Items.ENDER_PEARL)
                    .define('b', Tags.Items.STORAGE_BLOCKS_IRON)
                    .define('c', Items.ANVIL)
                    .define('a', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                    .showNotification(false)
                    .unlockedBy("has_pearl", has(Items.ENDER_PEARL))::save)
            .generateAdvancement()
            .build(consumer, new ResourceLocation(SimplyUtilities.MODID, "ender_inhibitor"));

        // Exoskeleton Leggings
        ConditionalRecipe.builder()
            .addCondition(new BoolConfigCondition("exoleggings"))
            .addRecipe(
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SUItems.EXOLEGGINGS.get())
                    .pattern("iai")
                    .pattern("ixi")
                    .pattern("i i")
                    .define('x', Items.LEATHER_LEGGINGS)
                    .define('i', Items.IRON_BARS)
                    .define('a', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                    .showNotification(false)
                    .unlockedBy("has_leather_pants", has(Items.LEATHER_LEGGINGS))::save)
            .generateAdvancement()
            .build(consumer, new ResourceLocation(SimplyUtilities.MODID, "exoskeleton_leggings"));

        ConditionalRecipe.builder()
            .addCondition(new BoolConfigCondition("online_detector"))
            .addRecipe(
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SUBlocks.ONLINE_DETECTOR.getItem())
                    .pattern("GBG")
                    .pattern("RER")
                    .pattern("HHH")
                    .define('G', Tags.Items.INGOTS_GOLD)
                    .define('B', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                    .define('E', Items.ENDER_EYE)
                    .define('R', Items.REPEATER)
                    .define('H', Items.WARPED_HYPHAE)
                    .showNotification(false)
                    .unlockedBy("", has(Items.AIR))::save)
            .generateAdvancement()
            .build(consumer, new ResourceLocation(SimplyUtilities.MODID, "online_detector"));

        ConditionalRecipe.builder()
            .addCondition(new NotCondition(new ModLoadedCondition("mekanism")))
            .addRecipe(
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SUBlocks.CHARCOAL_BLOCK.getItem())
                    .pattern("CCC")
                    .pattern("CCC")
                    .pattern("CCC")
                    .define('C', Items.CHARCOAL)
                    .showNotification(false)
                    .unlockedBy("", has(Items.AIR))::save)
            .generateAdvancement()
            .build(consumer, new ResourceLocation(SimplyUtilities.MODID, "charcoal_block"));

        ConditionalRecipe.builder()
            .addCondition(new NotCondition(new ModLoadedCondition("mekanism")))
            .addRecipe(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.CHARCOAL, 9)
                    .requires(SUTags.STORAGE_BLOCKS_CHARCOAL)
                    .unlockedBy("has_charcoal_block", has(SUBlocks.CHARCOAL_BLOCK.getItem()))::save)
                .generateAdvancement()
                .build(consumer, new ResourceLocation(SimplyUtilities.MODID, "charcoal_from_block"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SUBlocks.ENDER_PEARL_BLOCK.getItem())
            .pattern("PP")
            .pattern("PP")
            .define('P', Tags.Items.ENDER_PEARLS)
            .showNotification(false)
            .unlockedBy("has_ender_pearl", has(Items.ENDER_PEARL))
            .save(consumer, new ResourceLocation(SimplyUtilities.MODID, "ender_pearl_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.ENDER_PEARL, 4)
            .requires(SUTags.STORAGE_BLOCKS_ENDER_PEARL).unlockedBy("has_ender_pearl_block", has(SUBlocks.ENDER_PEARL_BLOCK.getItem()))
            .save(consumer, new ResourceLocation(SimplyUtilities.MODID, "ender_pearl"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SUItems.MINICOAL.get(), 8)
                .requires(Items.COAL).unlockedBy("has_coal", has(Items.COAL))
                .save(consumer, new ResourceLocation(SimplyUtilities.MODID, "mini_coal"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SUItems.MINICHARCOAL.get(), 8)
                .requires(Items.CHARCOAL).unlockedBy("has_charcoal", has(Items.CHARCOAL))
                .save(consumer, new ResourceLocation(SimplyUtilities.MODID, "mini_charcoal"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.COAL)
                .pattern("MMM")
                .pattern("M M")
                .pattern("MMM")
                .define('M', SUItems.MINICOAL.get())
                .unlockedBy("has_minicoal", has(SUItems.MINICOAL.get()))
                .showNotification(false)
                .save(consumer, new ResourceLocation(SimplyUtilities.MODID, "minicoal_to_coal"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.CHARCOAL)
                .pattern("MMM")
                .pattern("M M")
                .pattern("MMM")
                .define('M', SUItems.MINICHARCOAL.get())
                .showNotification(false)
                .unlockedBy("has_minicoal", has(SUItems.MINICHARCOAL.get()))
                .save(consumer, new ResourceLocation(SimplyUtilities.MODID, "minicharcoal_to_coal"));

/*
        consumer.accept(new RightClickRecipe.FinishedRecipe(new ResourceLocation(SimplyUtilities.MODID, "test_rightclick"), new ItemStack(Items.DIAMOND), Ingredient.of(Items.EMERALD), Blocks.ANVIL));

        ShapelessRecipeBuilder.shapeless(Items.MAGMA_BLOCK)
            .requires(Tags.Items.COBBLESTONE)
            .requires(new FluidIngredient(FluidTags.LAVA, false))
            .unlockedBy("", has(Items.AIR)).save(consumer, new ResourceLocation(SimplyUtilities.MODID, "test_fluidingredient"));*/
    }

    @Override
    protected CompletableFuture<?> saveAdvancement(@Nonnull CachedOutput cachedOutput, @Nonnull FinishedRecipe finishedRecipe, @Nonnull JsonObject object) {
        // Nope, don't want none of this...
        return null;
    }
}
