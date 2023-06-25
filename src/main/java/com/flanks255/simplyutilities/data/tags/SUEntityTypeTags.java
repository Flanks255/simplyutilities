package com.flanks255.simplyutilities.data.tags;

import com.flanks255.simplyutilities.SUTags;
import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class SUEntityTypeTags extends EntityTypeTagsProvider {
    public SUEntityTypeTags(DataGenerator pGenerator, CompletableFuture<HolderLookup.Provider> something, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator.getPackOutput(), something, SimplyUtilities.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(@Nonnull HolderLookup.Provider something) {
        getOrCreateRawBuilder(SUTags.NO_GRIEFING_KEY);
    }
}
