package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SUBlocks;
import com.flanks255.simplyutilities.SUTags;
import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SUBlockTags extends BlockTagsProvider {
    public SUBlockTags(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, SimplyUtilities.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(SUBlocks.ONLINE_DETECTOR.getBlock());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(SUBlocks.ENDER_INHIBITOR.getBlock());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(SUBlocks.CHARCOAL_BLOCK.getBlock());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(SUBlocks.ENDER_PEARL_BLOCK.getBlock());
    }
}
