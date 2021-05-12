package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.blocks.EnderInhibitor;
import net.minecraft.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SUBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SimplyUtilities.MODID);

    public static final RegistryObject<Block> ENDER_INHIBITOR = BLOCKS.register("ender_inhibitor", EnderInhibitor::new);

    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
