package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
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
        Tag.Named<Item> fSiliconTag = ItemTags.bind(new ResourceLocation("forge", "silicon").toString());
        Tag.Named<Item> ae2SiliconTag = ItemTags.bind(new ResourceLocation("appliedenergistics2", "silicon").toString());
        this.tag(fSiliconTag).addOptional(new ResourceLocation("appliedenergistics2", "silicon"));
        this.tag(ae2SiliconTag).addOptional(new ResourceLocation("refinedstorage", "silicon"));
    }
}
