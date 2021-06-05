package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.blocks.EnderInhibitor;
import com.flanks255.simplyutilities.blocks.OnlineDetector;
import com.flanks255.simplyutilities.items.SUBlockItem;
import com.flanks255.simplyutilities.tile.TEOnlineDetector;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SUBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SimplyUtilities.MODID);
    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, SimplyUtilities.MODID);

    public static final SUBlockReg<EnderInhibitor, SUBlockItem, ?> ENDER_INHIBITOR = new SUBlockReg<>("ender_inhibitor", EnderInhibitor::new,
        (b) -> new SUBlockItem(b,new Item.Properties().maxStackSize(64).group(ItemGroup.MISC)));

    public static final SUBlockReg<OnlineDetector, SUBlockItem, TEOnlineDetector> ONLINE_DETECTOR = new SUBlockReg<>("online_detector", OnlineDetector::new,
        (b) -> new SUBlockItem(b, new Item.Properties().maxStackSize(64).group(ItemGroup.MISC)), TEOnlineDetector::new);

    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        TILE_ENTITIES.register(eventBus);
    }
}
