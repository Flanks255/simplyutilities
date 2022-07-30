package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SUBlocks;
import com.flanks255.simplyutilities.SUTags;
import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;


public class SUItemTags extends ItemTagsProvider {

    public SUItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, SimplyUtilities.MODID, existingFileHelper);
    }



    @Override
    protected void addTags() {
        TagKey<Item> fSiliconTag = ItemTags.create(new ResourceLocation("forge", "silicon"));
        TagKey<Item> ae2SiliconTag = ItemTags.create(new ResourceLocation("appliedenergistics2", "silicon"));
        this.tag(fSiliconTag).addOptional(new ResourceLocation("appliedenergistics2", "silicon"));
        this.tag(ae2SiliconTag).addOptional(new ResourceLocation("refinedstorage", "silicon"));

        tag(SUTags.STORAGE_BLOCKS_CHARCOAL).add(SUBlocks.CHARCOAL_BLOCK.getItem());
        tag(SUTags.STORAGE_BLOCKS_ENDER_PEARL).add(SUBlocks.ENDER_PEARL_BLOCK.getItem());
    }
}
