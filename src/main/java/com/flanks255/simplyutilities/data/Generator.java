package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.data.tags.SUBlockTags;
import com.flanks255.simplyutilities.data.tags.SUEntityTypeTags;
import com.flanks255.simplyutilities.data.tags.SUItemTags;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class Generator {
    public static void gatherData(GatherDataEvent event) {
        //CraftingHelper.register(BoolConfigCondition.Serializer.INSTANCE);

        DataGenerator generator = event.getGenerator();
        generator.addProvider(true, new ItemModels(generator, event.getExistingFileHelper()));
        generator.addProvider(true, new Recipes(generator));
        generator.addProvider(true, new Lang(generator));
        generator.addProvider(true, new BlockStates(generator, event.getExistingFileHelper()));
        generator.addProvider(true, SULootTables.getProvider(generator.getPackOutput()));
        SUBlockTags blockTags = new SUBlockTags(generator,event.getLookupProvider(), event.getExistingFileHelper());
        generator.addProvider(true, blockTags);
        generator.addProvider(true, new SUItemTags(generator, event.getLookupProvider(), blockTags, event.getExistingFileHelper()));
        generator.addProvider(true, new SUEntityTypeTags(generator, event.getLookupProvider(), event.getExistingFileHelper()));
    }
}
