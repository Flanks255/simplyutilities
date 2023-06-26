package com.flanks255.simplyutilities.data.tags;

import com.flanks255.simplyutilities.SUBlocks;
import com.flanks255.simplyutilities.SUTags;
import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;


public class SUItemTags extends ItemTagsProvider {

    public SUItemTags(DataGenerator dataGenerator, CompletableFuture<HolderLookup.Provider> thingIDontUse, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator.getPackOutput(), thingIDontUse, blockTagProvider.contentsGetter(), SimplyUtilities.MODID, existingFileHelper);
    }



    @Override
    protected void addTags(@Nonnull HolderLookup.Provider useless) {
        TagKey<Item> fSiliconTag = ItemTags.create(new ResourceLocation("forge", "silicon"));
        TagKey<Item> ae2SiliconTag = ItemTags.create(new ResourceLocation("appliedenergistics2", "silicon"));
        this.tag(fSiliconTag).addOptional(new ResourceLocation("appliedenergistics2", "silicon"));
        this.tag(ae2SiliconTag).addOptional(new ResourceLocation("refinedstorage", "silicon"));

        tag(SUTags.STORAGE_BLOCKS_CHARCOAL).add(SUBlocks.CHARCOAL_BLOCK.getItem());
        tag(SUTags.STORAGE_BLOCKS_ENDER_PEARL).add(SUBlocks.ENDER_PEARL_BLOCK.getItem());
    }
}
