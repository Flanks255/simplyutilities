package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SUBlocks;
import com.flanks255.simplyutilities.SUItems;
import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class ItemModels extends ItemModelProvider {


    public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), SimplyUtilities.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(SUItems.EXOLEGGINGS.get());

        registerBlockItem(SUBlocks.ENDER_INHIBITOR.get());
        registerBlockItem(SUBlocks.CHARCOAL_BLOCK.get());
        registerBlockItem(SUBlocks.ENDER_PEARL_BLOCK.get());
        registerBlockISTER(SUBlocks.ONLINE_DETECTOR.get());

        simpleItem(SUItems.MINICOAL.get());
        simpleItem(SUItems.MINICHARCOAL.get());
    }

    private String getRegPath(Item item) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
    }
    private String getRegPath(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }


    private void simpleItem(Item item) {
        String name = getRegPath(item);
        singleTexture(name, mcLoc("item/handheld"), "layer0", modLoc("item/" + name));
    }


    private void registerBlockItem(Block block) {
        String path = getRegPath(block);
        getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc("block/"+path)));
    }

    private void registerBlockISTER(Block block) {
        String path = getRegPath(block);
        getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc("block/entity")))
            .transforms().transform(ItemTransforms.TransformType.GUI)
            .rotation(30f ,45f ,0f)
            .translation(0f,0f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND)
            .rotation(0f,0f,0f)
            .translation(0f,0f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND)
            .rotation(0f,0f,0f)
            .translation(0f,0f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND)
            .rotation(0f,0f,0f)
            .translation(0f,0f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND)
            .rotation(0f,0f,0f)
            .translation(0f,0f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ItemTransforms.TransformType.GROUND)
            .rotation(0f,0f,0f)
            .translation(0f,3f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ItemTransforms.TransformType.FIXED)
            .rotation(0f,0f,0f)
            .translation(0f,0f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ItemTransforms.TransformType.HEAD)
            .rotation(0f,0f,0f)
            .translation(0f,0f,0f)
            .scale(0.5f, 0.5f, 0.5f)
            .end();
    }

}
