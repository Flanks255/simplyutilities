package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.blocks.EnderInhibitor;
import com.flanks255.simplyutilities.blocks.OnlineDetector;
import com.flanks255.simplyutilities.items.SUBlockItem;
import com.flanks255.simplyutilities.render.OnlineDetectorItemStackRender;
import com.flanks255.simplyutilities.tile.BEOnlineDetector;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class SUBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SimplyUtilities.MODID);
    public static DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, SimplyUtilities.MODID);

    public static final SUBlockReg<EnderInhibitor, SUBlockItem, ?> ENDER_INHIBITOR = new SUBlockReg<>("ender_inhibitor", EnderInhibitor::new,
        (b) -> new SUBlockItem(b,new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MISC)));

    public static final SUBlockReg<OnlineDetector, SUBlockItem, BEOnlineDetector> ONLINE_DETECTOR = new SUBlockReg<>("online_detector", OnlineDetector::new,
        (b) -> new SUBlockItem(b, new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MISC)) {
            @Override
            public void initializeClient(Consumer<IItemRenderProperties> consumer) {
                consumer.accept(new IItemRenderProperties() {
                    @Override
                    public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                        return new OnlineDetectorItemStackRender(null, null);
                    }
                });
            }
        }, BEOnlineDetector::new);

    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        TILE_ENTITIES.register(eventBus);
    }
}
