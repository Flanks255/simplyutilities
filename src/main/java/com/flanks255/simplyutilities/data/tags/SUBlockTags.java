package com.flanks255.simplyutilities.data.tags;

import com.flanks255.simplyutilities.SUBlocks;
import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class SUBlockTags extends BlockTagsProvider {
    public SUBlockTags(DataGenerator generatorIn, CompletableFuture<HolderLookup.Provider> thingIDontUse, ExistingFileHelper existingFileHelper) {
        super(generatorIn.getPackOutput(), thingIDontUse, SimplyUtilities.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(@Nonnull HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(SUBlocks.ONLINE_DETECTOR.getBlock());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(SUBlocks.ENDER_INHIBITOR.getBlock());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(SUBlocks.CHARCOAL_BLOCK.getBlock());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(SUBlocks.ENDER_PEARL_BLOCK.getBlock());
    }
}
