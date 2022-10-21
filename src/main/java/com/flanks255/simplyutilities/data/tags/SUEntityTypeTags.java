package com.flanks255.simplyutilities.data.tags;

import com.flanks255.simplyutilities.SUTags;
import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class SUEntityTypeTags extends EntityTypeTagsProvider {
    public SUEntityTypeTags(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, SimplyUtilities.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        getOrCreateRawBuilder(SUTags.NO_GRIEFING_KEY);
    }
}
