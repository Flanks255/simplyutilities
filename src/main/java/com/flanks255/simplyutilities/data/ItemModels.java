package com.flanks255.simplyutilities.data;

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
        //singleTexture("canister", mcLoc("item/handheld"), "layer0", modLoc("item/canister"));


        for (RegistryObject<Item> item : SimplyUtilities.SIMPLEITEMS.getEntries()) {
            String name = item.get().getRegistryName().getPath();
            singleTexture(name, mcLoc("item/handheld"), "layer0", modLoc("item/" + name));
        }

        registerBlockItem(SimplyUtilities.ENDER_INHIBITOR.get());
    }


    private void registerBlockItem(Block block) {
        String path = block.getRegistryName().getPath();
        getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc("block/"+path)));
    }

}
