package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;


public class SUItemTags extends ItemTagsProvider {

    public SUItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, SimplyUtilities.MODID, existingFileHelper);
    }



    @Override
    protected void registerTags() {
        ITag.INamedTag<Item> fSiliconTag = ItemTags.makeWrapperTag(new ResourceLocation("forge", "silicon").toString());
        ITag.INamedTag<Item> ae2SiliconTag = ItemTags.makeWrapperTag(new ResourceLocation("appliedenergistics2", "silicon").toString());

        this.getOrCreateBuilder(fSiliconTag).addOptional(new ResourceLocation("appliedenergistics2", "silicon"));
        this.getOrCreateBuilder(ae2SiliconTag).addOptional(new ResourceLocation("refinedstorage", "silicon"));
    }
}
