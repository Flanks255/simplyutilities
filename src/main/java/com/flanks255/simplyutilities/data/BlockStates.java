package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SUBlocks;
import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
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

        ResourceLocation placeholderTex = modLoc("block/placeholder");
        ModelFile placeholderCube = models().cubeAll("online_detector", placeholderTex);

        simpleBlock(SUBlocks.ONLINE_DETECTOR.getBlock(), placeholderCube);
    }
}
