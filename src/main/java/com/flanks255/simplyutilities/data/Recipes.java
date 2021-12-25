package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SUBlocks;
import com.flanks255.simplyutilities.SUItems;
import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.crafting.FluidIngredient;
import com.flanks255.simplyutilities.crafting.RightClickRecipe;
import com.flanks255.simplyutilities.crafting.TargetNBTIngredient;
import com.flanks255.simplyutilities.crafting.WrappedRecipe;
import com.google.gson.JsonObject;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.AndCondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        ConditionalRecipe.builder()
            .addCondition(new BoolConfigCondition("craftLogsToSticks"))
            .addRecipe(
                ShapedRecipeBuilder.shapedRecipe(Items.STICK, 16)
                    .patternLine("x")
                    .patternLine("x")
                    .key('x', ItemTags.LOGS)
                    .addCriterion("has_logs", hasItem(Items.OAK_LOG))::build)
            .generateAdvancement()
            .build(consumer, new ResourceLocation(SimplyUtilities.MODID, "stick_from_logs"));

        ConditionalRecipe.builder()
            .addCondition(new AndCondition(new NotCondition(new ModLoadedCondition("quark")), new BoolConfigCondition("craftLogsToChests")))
            .addRecipe(
                ShapedRecipeBuilder.shapedRecipe(Items.CHEST, 4)
                    .patternLine("xxx")
                    .patternLine("x x")
                    .patternLine("xxx")
                    .key('x', ItemTags.LOGS)
                    .addCriterion("has_logs", hasItem(Items.OAK_LOG))::build)
            .generateAdvancement()
            .build(consumer, new ResourceLocation(SimplyUtilities.MODID, "chests_from_logs"));

        ConditionalRecipe.builder()
            .addCondition(new BoolConfigCondition("smeltFleshIntoLeather"))
            .addRecipe(
                CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.ROTTEN_FLESH), Items.LEATHER, 0.0F, 400)
                    .addCriterion("has_flesh", hasItem(Items.ROTTEN_FLESH))::build)
            .generateAdvancement()
            .build(consumer, new ResourceLocation(SimplyUtilities.MODID, "leather_from_flesh"));

        ShapedRecipeBuilder.shapedRecipe(Items.STICKY_PISTON)
            .patternLine("x")
            .patternLine("y")
            .key('x', Tags.Items.SLIMEBALLS)
            .key('y', Items.PISTON)
            .addCriterion("hasSlimeBall", hasItem(Items.SLIME_BALL))
            .build(consumer,new ResourceLocation(SimplyUtilities.MODID, "sticky_piston"));

        // Ender inhibitor
        ConditionalRecipe.builder()
            .addCondition(new BoolConfigCondition("enableEnderInhibitor"))
            .addRecipe(
                ShapedRecipeBuilder.shapedRecipe(SUBlocks.ENDER_INHIBITOR.getItem())
                    .patternLine(" x ")
                    .patternLine(" b ")
                    .patternLine("aca")
                    .key('x', Items.ENDER_PEARL)
                    .key('b', Tags.Items.STORAGE_BLOCKS_IRON)
                    .key('c', Items.ANVIL)
                    .key('a', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                    .addCriterion("has_pearl", hasItem(Items.ENDER_PEARL))::build)
            .generateAdvancement()
            .build(consumer, new ResourceLocation(SimplyUtilities.MODID, "ender_inhibitor"));

        // Exoskeleton Leggings
        ConditionalRecipe.builder()
            .addCondition(new BoolConfigCondition("exoleggings"))
            .addRecipe(
                ShapedRecipeBuilder.shapedRecipe(SUItems.EXOLEGGINGS.get())
                    .patternLine("iai")
                    .patternLine("ixi")
                    .patternLine("i i")
                    .key('x', Items.LEATHER_LEGGINGS)
                    .key('i', Items.IRON_BARS)
                    .key('a', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                    .addCriterion("has_leather_pants", hasItem(Items.LEATHER_LEGGINGS))::build)
            .generateAdvancement()
            .build(consumer, new ResourceLocation(SimplyUtilities.MODID, "exoskeleton_leggings"));

        ConditionalRecipe.builder()
            .addCondition(new BoolConfigCondition("online_detector"))
            .addRecipe(
                ShapedRecipeBuilder.shapedRecipe(SUBlocks.ONLINE_DETECTOR.getItem())
                    .patternLine("GBG")
                    .patternLine("RER")
                    .patternLine("HHH")
                    .key('G', Tags.Items.INGOTS_GOLD)
                    .key('B', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                    .key('E', Items.ENDER_EYE)
                    .key('R', Items.REPEATER)
                    .key('H', Items.WARPED_HYPHAE)
                    .addCriterion("", hasItem(Items.AIR))::build)
            .generateAdvancement()
            .build(consumer, new ResourceLocation(SimplyUtilities.MODID, "online_detector"));


    }

    @Override
    protected void saveRecipeAdvancement(@Nonnull DirectoryCache cache, @Nonnull JsonObject cache2, @Nonnull Path advancementJson) {
        // No thank you, good day sir.
    }
}
