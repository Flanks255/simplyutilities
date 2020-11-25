package com.flanks255.simplyutilities.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class Generator {
    public static void gatherData(GatherDataEvent event) {
        CraftingHelper.register(BoolConfigCondition.Serializer.INSTANCE);

        DataGenerator generator = event.getGenerator();
        generator.addProvider(new ItemModels(generator, event.getExistingFileHelper()));
        generator.addProvider(new Recipes(generator));
        generator.addProvider(new Lang(generator));
        generator.addProvider(new BlockStates(generator, event.getExistingFileHelper()));
        generator.addProvider(new LootTables(generator));
        SUBlockTags blockTags = new SUBlockTags(generator, event.getExistingFileHelper());
        generator.addProvider(blockTags);
        generator.addProvider(new SUItemTags(generator, blockTags, event.getExistingFileHelper()));
    }
}
