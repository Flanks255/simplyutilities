package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStates extends BlockStateProvider {
    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, SimplyUtilities.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(SimplyUtilities.ENDER_INHIBITOR.get(), models().getExistingFile(modLoc("ender_inhibitor")));
    }
}
