package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SUBlocks;
import com.flanks255.simplyutilities.SUItems;
import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModels extends ItemModelProvider {


    public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, SimplyUtilities.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(SUItems.EXOLEGGINGS.get());

        registerBlockItem(SUBlocks.ENDER_INHIBITOR.get());
        registerBlockISTER(SUBlocks.ONLINE_DETECTOR.get());
    }

    private void simpleItem(Item item) {
        String name = item.getRegistryName().getPath();
        singleTexture(name, mcLoc("item/handheld"), "layer0", modLoc("item/" + name));
    }


    private void registerBlockItem(Block block) {
        String path = block.getRegistryName().getPath();
        getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc("block/"+path)));
    }

    private void registerBlockISTER(Block block) {
        String path = block.getRegistryName().getPath();
        getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc("block/entity")))
            .transforms().transform(ModelBuilder.Perspective.GUI)
            .rotation(30f ,45f ,0f)
            .translation(0f,0f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ModelBuilder.Perspective.FIRSTPERSON_LEFT)
            .rotation(0f,0f,0f)
            .translation(0f,0f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ModelBuilder.Perspective.FIRSTPERSON_RIGHT)
            .rotation(0f,0f,0f)
            .translation(0f,0f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ModelBuilder.Perspective.THIRDPERSON_LEFT)
            .rotation(0f,0f,0f)
            .translation(0f,0f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ModelBuilder.Perspective.THIRDPERSON_RIGHT)
            .rotation(0f,0f,0f)
            .translation(0f,0f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ModelBuilder.Perspective.GROUND)
            .rotation(0f,0f,0f)
            .translation(0f,3f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ModelBuilder.Perspective.FIXED)
            .rotation(0f,0f,0f)
            .translation(0f,0f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ModelBuilder.Perspective.HEAD)
            .rotation(0f,0f,0f)
            .translation(0f,0f,0f)
            .scale(0.5f, 0.5f, 0.5f)
            .end();
    }

}
