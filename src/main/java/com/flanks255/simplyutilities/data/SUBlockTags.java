package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SUBlockTags extends BlockTagsProvider {
    public SUBlockTags(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, SimplyUtilities.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
    }
}
