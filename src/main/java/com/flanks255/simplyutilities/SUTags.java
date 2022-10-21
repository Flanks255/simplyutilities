package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.utils.TagLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class SUTags {
    public static void init() {}


    public static final TagKey<Item> STORAGE_BLOCKS_CHARCOAL = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", "storage_blocks/charcoal"));
    public static final TagKey<Item> STORAGE_BLOCKS_ENDER_PEARL = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", "storage_blocks/ender_pearl"));
    public static final TagKey<EntityType<?>> NO_GRIEFING_KEY = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(SimplyUtilities.MODID, "no_griefing"));

    public static final TagLookup<EntityType<?>> NO_GRIEFING = new TagLookup<>(ForgeRegistries.ENTITIES, NO_GRIEFING_KEY);
}
