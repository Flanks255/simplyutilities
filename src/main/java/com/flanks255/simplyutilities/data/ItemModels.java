package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SUBlocks;
import com.flanks255.simplyutilities.SUItems;
import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ItemModels extends ItemModelProvider {


    public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, SimplyUtilities.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(SUItems.EXOLEGGINGS.get());

        registerBlockItem(SUBlocks.ENDER_INHIBITOR.get());
        registerBlockItem(SUBlocks.ONLINE_DETECTOR.get());
    }

    private void simpleItem(Item item) {
        String name = item.getRegistryName().getPath();
        singleTexture(name, mcLoc("item/handheld"), "layer0", modLoc("item/" + name));
    }


    private void registerBlockItem(Block block) {
        String path = block.getRegistryName().getPath();
        getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc("block/"+path)));
    }

}
