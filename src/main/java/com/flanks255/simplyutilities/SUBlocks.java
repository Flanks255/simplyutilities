package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.blocks.EnderInhibitor;
import com.flanks255.simplyutilities.blocks.OnlineDetector;
import com.flanks255.simplyutilities.items.SUBlockItem;
import com.flanks255.simplyutilities.render.OnlineDetectorItemStackRender;
import com.flanks255.simplyutilities.tile.BEOnlineDetector;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.function.Consumer;


public class SUBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SimplyUtilities.MODID);
    public static DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SimplyUtilities.MODID);

    public static final SUBlockReg<EnderInhibitor, SUBlockItem, ?> ENDER_INHIBITOR = new SUBlockReg<>("ender_inhibitor", EnderInhibitor::new,
        (b) -> new SUBlockItem(b,new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MISC)));

    public static final SUBlockReg<OnlineDetector, SUBlockItem, BEOnlineDetector> ONLINE_DETECTOR = new SUBlockReg<>("online_detector", OnlineDetector::new,
        (b) -> new SUBlockItem(b, new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MISC)) {
            @Override
            public void initializeClient(@Nonnull Consumer<IClientItemExtensions> consumer) {
                consumer.accept(new IClientItemExtensions() {
                    @Override
                    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                        return new OnlineDetectorItemStackRender(null, null);
                    }
                });
            }
        }, BEOnlineDetector::new);

    public static final SUBlockReg<Block, SUBlockItem, ?> CHARCOAL_BLOCK = new SUBlockReg<>("charcoal_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(5.0F, 6.0F)),
        (b) -> new SUBlockItem(b, new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_BUILDING_BLOCKS)) {
            @Override
            public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                return 16000;
            }
        });

    public static final SUBlockReg<Block, SUBlockItem, ?> ENDER_PEARL_BLOCK = new SUBlockReg<>("ender_pearl_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(5.0F, 6.0F)),
        (b) -> new SUBlockItem(b, new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        TILE_ENTITIES.register(eventBus);
    }
}
