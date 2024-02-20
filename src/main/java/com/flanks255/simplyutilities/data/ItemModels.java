package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SUBlocks;
import com.flanks255.simplyutilities.SUItems;
import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

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
        return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)).getPath();
    }
    private String getRegPath(Block block) {
        return Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getPath();
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
            .transforms().transform(ItemDisplayContext.GUI)
            .rotation(30f ,45f ,0f)
            .translation(0f,0f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
            .rotation(0f,0f,0f)
            .translation(0f,0f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
            .rotation(0f,0f,0f)
            .translation(0f,0f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
            .rotation(0f,0f,0f)
            .translation(0f,0f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
            .rotation(0f,0f,0f)
            .translation(0f,0f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ItemDisplayContext.GROUND)
            .rotation(0f,0f,0f)
            .translation(0f,3f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ItemDisplayContext.FIXED)
            .rotation(0f,0f,0f)
            .translation(0f,0f,0f)
            .scale(0.625f, 0.625f, 0.625f)
            .end()
            .transform(ItemDisplayContext.HEAD)
            .rotation(0f,0f,0f)
            .translation(0f,0f,0f)
            .scale(0.5f, 0.5f, 0.5f)
            .end();
    }

}
