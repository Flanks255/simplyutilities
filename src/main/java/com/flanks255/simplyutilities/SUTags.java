package com.flanks255.simplyutilities;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class SUTags {
    public static void init() {}

    public static final TagKey<Item> STORAGE_BLOCKS_CHARCOAL = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/charcoal"));
    public static final TagKey<Item> STORAGE_BLOCKS_ENDER_PEARL = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/ender_pearl"));
    public static final TagKey<EntityType<?>> NO_GRIEFING = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(SimplyUtilities.MODID, "no_griefing"));
}
