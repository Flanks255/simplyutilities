package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SUBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SULootTables extends BlockLootSubProvider {
    protected SULootTables(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    public static LootTableProvider getProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future) {
        return new LootTableProvider(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(SULootTables::new, LootContextParamSets.BLOCK)), future);
    }

    @Override
    protected void generate() {
        this.dropSelf(SUBlocks.ENDER_INHIBITOR.get());
        this.dropSelf(SUBlocks.ONLINE_DETECTOR.get());
        this.dropSelf(SUBlocks.CHARCOAL_BLOCK.get());
        this.dropSelf(SUBlocks.ENDER_PEARL_BLOCK.get());
    }

    @Nonnull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return SUBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toList());
    }
}
