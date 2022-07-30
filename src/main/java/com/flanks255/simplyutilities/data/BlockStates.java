package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SUBlocks;
import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStates extends BlockStateProvider {
    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, SimplyUtilities.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(SUBlocks.ENDER_INHIBITOR.get(), models().getExistingFile(modLoc("ender_inhibitor")));
        ModelFile blank = models().getExistingFile(modLoc("entity"));

//        ResourceLocation placeholderTex = modLoc("block/placeholder");
//        ModelFile placeholderCube = models().cubeAll("placeholder", placeholderTex);

        simpleBlock(SUBlocks.ONLINE_DETECTOR.getBlock(), blank);

        simpleBlock(SUBlocks.CHARCOAL_BLOCK.getBlock());
        simpleBlock(SUBlocks.ENDER_PEARL_BLOCK.getBlock());
    }
}
