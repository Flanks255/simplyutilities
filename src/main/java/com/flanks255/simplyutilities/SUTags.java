package com.flanks255.simplyutilities;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SUTags {
    public static void init() {}


    public static final TagKey<Item> STORAGE_BLOCKS_CHARCOAL = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", "storage_blocks/charcoal"));
    public static final TagKey<Item> STORAGE_BLOCKS_ENDER_PEARL = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", "storage_blocks/ender_pearl"));
}
