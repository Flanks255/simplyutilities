package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SUBlocks;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;

public class SULootTables extends BlockLootSubProvider {
    protected SULootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    public static LootTableProvider getProvider(PackOutput output) {
        return new LootTableProvider(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(SULootTables::new, LootContextParamSets.BLOCK)));
    }

    @Override
    protected void generate() {
/*        for(RegistryObject<Block> block : SUBlocks.BLOCKS.getEntries()) {
            this.dropSelf(block.get());
        }*/

        this.dropSelf(SUBlocks.ENDER_INHIBITOR.get());
        this.dropSelf(SUBlocks.ONLINE_DETECTOR.get());
        this.dropSelf(SUBlocks.CHARCOAL_BLOCK.get());
        this.dropSelf(SUBlocks.ENDER_PEARL_BLOCK.get());
    }

    @Nonnull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return SUBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
    }
}
