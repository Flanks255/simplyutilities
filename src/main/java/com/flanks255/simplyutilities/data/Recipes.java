package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SUBlocks;
import com.flanks255.simplyutilities.SUItems;
import com.flanks255.simplyutilities.SUTags;
import com.flanks255.simplyutilities.utils.MiscUtils;
import com.flanks255.simplyutilities.utils.NoAdvRecipeOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.AndCondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator generatorIn, CompletableFuture<HolderLookup.Provider> registries) {
        super(generatorIn.getPackOutput(), registries);
    }

    @Override
    protected void buildRecipes(@Nonnull RecipeOutput theirOutput) {
        var output = new NoAdvRecipeOutput(theirOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STICK, 16)
                .pattern("x")
                .pattern("x")
                .define('x', ItemTags.LOGS)
                .showNotification(false)
                .unlockedBy("has_logs", has(Items.OAK_LOG))
                .save(output.withConditions(new BoolConfigCondition("craftLogsToSticks")),
                        MiscUtils.rl("stick_from_logs"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.CHEST, 4)
            .pattern("xxx")
            .pattern("x x")
            .pattern("xxx")
            .define('x', ItemTags.LOGS)
            .showNotification(false)
            .unlockedBy("has_logs", has(Items.OAK_LOG))
            .save(output.withConditions(new AndCondition(List.of(new NotCondition(new ModLoadedCondition("quark")), new BoolConfigCondition("craftLogsToChests")))),
                    MiscUtils.rl("chests_from_logs"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.ROTTEN_FLESH), RecipeCategory.MISC, Items.LEATHER, 0.0F, 400)
            .unlockedBy("has_flesh", has(Items.ROTTEN_FLESH))
            .save(output.withConditions(new BoolConfigCondition("smeltFleshIntoLeather")), MiscUtils.rl("leather_from_flesh"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STICKY_PISTON)
            .pattern("x")
            .pattern("y")
            .define('x', Tags.Items.SLIME_BALLS)
            .define('y', Items.PISTON)
            .showNotification(false)
            .unlockedBy("hasSlimeBall", has(Items.SLIME_BALL))
            .save(output, MiscUtils.rl("sticky_piston"));

        // Ender inhibitor
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SUBlocks.ENDER_INHIBITOR.getItem())
            .pattern(" x ")
            .pattern(" b ")
            .pattern("aca")
            .define('x', Items.ENDER_PEARL)
            .define('b', Tags.Items.STORAGE_BLOCKS_IRON)
            .define('c', Items.ANVIL)
            .define('a', Tags.Items.STORAGE_BLOCKS_DIAMOND)
            .showNotification(false)
            .unlockedBy("has_pearl", has(Items.ENDER_PEARL))
            .save(output.withConditions(new BoolConfigCondition("enableEnderInhibitor")), MiscUtils.rl("ender_inhibitor"));

        // Exoskeleton Leggings
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SUItems.EXOLEGGINGS.get())
            .pattern("iai")
            .pattern("ixi")
            .pattern("i i")
            .define('x', Items.LEATHER_LEGGINGS)
            .define('i', Items.IRON_BARS)
            .define('a', Tags.Items.STORAGE_BLOCKS_DIAMOND)
            .showNotification(false)
            .unlockedBy("has_leather_pants", has(Items.LEATHER_LEGGINGS))
            .save(output.withConditions(new BoolConfigCondition("exoleggings")), MiscUtils.rl("exoskeleton_leggings"));

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
            .unlockedBy("", has(Items.AIR))
            .save(output.withConditions(new BoolConfigCondition("online_detector")), MiscUtils.rl("online_detector"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SUBlocks.CHARCOAL_BLOCK.getItem())
            .pattern("CCC")
            .pattern("CCC")
            .pattern("CCC")
            .define('C', Items.CHARCOAL)
            .showNotification(false)
            .unlockedBy("", has(Items.AIR))
            .save(output.withConditions(new NotCondition(new ModLoadedCondition("mekanism"))), MiscUtils.rl("charcoal_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.CHARCOAL, 9)
            .requires(SUTags.STORAGE_BLOCKS_CHARCOAL)
            .unlockedBy("has_charcoal_block", has(SUBlocks.CHARCOAL_BLOCK.getItem()))
            .save(output.withConditions(new NotCondition(new ModLoadedCondition("mekanism"))), MiscUtils.rl("charcoal_from_block"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SUBlocks.ENDER_PEARL_BLOCK.getItem())
            .pattern("PP")
            .pattern("PP")
            .define('P', Tags.Items.ENDER_PEARLS)
            .showNotification(false)
            .unlockedBy("has_ender_pearl", has(Items.ENDER_PEARL))
            .save(output, MiscUtils.rl("ender_pearl_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.ENDER_PEARL, 4)
            .requires(SUTags.STORAGE_BLOCKS_ENDER_PEARL).unlockedBy("has_ender_pearl_block", has(SUBlocks.ENDER_PEARL_BLOCK.getItem()))
            .save(output, MiscUtils.rl("ender_pearl"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SUItems.MINICOAL.get(), 8)
                .requires(Items.COAL).unlockedBy("has_coal", has(Items.COAL))
                .save(output, MiscUtils.rl("mini_coal"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SUItems.MINICHARCOAL.get(), 8)
                .requires(Items.CHARCOAL).unlockedBy("has_charcoal", has(Items.CHARCOAL))
                .save(output, MiscUtils.rl("mini_charcoal"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.COAL)
                .pattern("MMM")
                .pattern("M M")
                .pattern("MMM")
                .define('M', SUItems.MINICOAL.get())
                .unlockedBy("has_minicoal", has(SUItems.MINICOAL.get()))
                .showNotification(false)
                .save(output, MiscUtils.rl("minicoal_to_coal"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.CHARCOAL)
                .pattern("MMM")
                .pattern("M M")
                .pattern("MMM")
                .define('M', SUItems.MINICHARCOAL.get())
                .showNotification(false)
                .unlockedBy("has_minicoal", has(SUItems.MINICHARCOAL.get()))
                .save(output, MiscUtils.rl("minicharcoal_to_coal"));

/*        output.accept(rl("test_rightclick"), new RightClickRecipe(Ingredient.of(Items.EMERALD), Blocks.ANVIL, new ItemStack(Items.DIAMOND)), null);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.MAGMA_BLOCK)
            .requires(Tags.Items.COBBLESTONE)
            //.requires(new ItemstackFluidIngredient(FluidTags.LAVA))
            .requires(new ItemstackFluidIngredient(Fluids.LAVA))
            .unlockedBy("", has(Items.AIR)).save(output, rl("test_fluidingredient"));*/
    }
}
