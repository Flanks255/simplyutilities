package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.data.tags.SUBlockTags;
import com.flanks255.simplyutilities.data.tags.SUEntityTypeTags;
import com.flanks255.simplyutilities.data.tags.SUItemTags;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class Generator {
    public static void gatherData(GatherDataEvent event) {
        CraftingHelper.register(BoolConfigCondition.Serializer.INSTANCE);

        DataGenerator generator = event.getGenerator();
        generator.addProvider(new ItemModels(generator, event.getExistingFileHelper()));
        generator.addProvider(new Recipes(generator));
        generator.addProvider(new Lang(generator));
        generator.addProvider(new BlockStates(generator, event.getExistingFileHelper()));
        generator.addProvider(new SULootTables(generator));
        SUBlockTags blockTags = new SUBlockTags(generator, event.getExistingFileHelper());
        generator.addProvider(blockTags);
        generator.addProvider(new SUItemTags(generator, blockTags, event.getExistingFileHelper()));
        generator.addProvider(new SUEntityTypeTags(generator, event.getExistingFileHelper()));
        generator.addProvider(blockTags);
        generator.addProvider(new SUItemTags(generator, blockTags, event.getExistingFileHelper()));
    }
}
