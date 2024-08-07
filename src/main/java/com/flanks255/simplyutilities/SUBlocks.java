package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.blocks.EnderInhibitor;
import com.flanks255.simplyutilities.blocks.OnlineDetector;
import com.flanks255.simplyutilities.items.SUBlockItem;
import com.flanks255.simplyutilities.tile.BEOnlineDetector;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nullable;


public class SUBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SimplyUtilities.MODID);
    public static DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, SimplyUtilities.MODID);

    public static final SUBlockReg<EnderInhibitor, SUBlockItem, ?> ENDER_INHIBITOR = new SUBlockReg<>("ender_inhibitor", EnderInhibitor::new,
        (b) -> new SUBlockItem(b,new Item.Properties().stacksTo(64)));

    public static final SUBlockReg<OnlineDetector, SUBlockItem, BEOnlineDetector> ONLINE_DETECTOR = new SUBlockReg<>("online_detector", OnlineDetector::new,
        (b) -> new SUBlockItem(b, new Item.Properties().stacksTo(64)), BEOnlineDetector::new);

    public static final SUBlockReg<Block, SUBlockItem, ?> CHARCOAL_BLOCK = new SUBlockReg<>("charcoal_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(5.0F, 6.0F)),
        (b) -> new SUBlockItem(b, new Item.Properties().stacksTo(64)) {
            @Override
            public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                return 16000;
            }
        });

    public static final SUBlockReg<Block, SUBlockItem, ?> ENDER_PEARL_BLOCK = new SUBlockReg<>("ender_pearl_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(5.0F, 6.0F)),
        (b) -> new SUBlockItem(b, new Item.Properties().stacksTo(64)));

    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        TILE_ENTITIES.register(eventBus);
    }
}
