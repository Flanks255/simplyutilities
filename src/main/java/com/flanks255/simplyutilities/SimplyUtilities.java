package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.blocks.EnderInhibitor;
import com.flanks255.simplyutilities.commands.MyCommands;
import com.flanks255.simplyutilities.configuration.CommonConfiguration;
import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.flanks255.simplyutilities.configuration.ServerConfiguration;
import com.flanks255.simplyutilities.data.BoolConfigCondition;
import com.flanks255.simplyutilities.data.Generator;
import com.flanks255.simplyutilities.items.ExoLeggings;
import com.flanks255.simplyutilities.items.SUBlockItem;
import com.flanks255.simplyutilities.tweaks.DoubleDoorFix;
import com.flanks255.simplyutilities.network.SUNetwork;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("simplyutilities")
public class SimplyUtilities
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "simplyutilities";
    public static SimpleChannel NETWORK;

    public static boolean isQuarkLoaded = false;

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    //Items with 1 basic texture.
    public static final DeferredRegister<Item> SIMPLEITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final RegistryObject<Item> EXOLEGGINGS = SIMPLEITEMS.register("exoleggings", ExoLeggings::new);


    public static final RegistryObject<Block> ENDER_INHIBITOR = BLOCKS.register("ender_inhibitor", EnderInhibitor::new);
    public static final RegistryObject<Item> ENDER_INHIBITOR_ITEM = ITEMS.register("ender_inhibitor", () -> new SUBlockItem(ENDER_INHIBITOR.get(), new Item.Properties().maxStackSize(64).group(ItemGroup.MISC)));

    //public static final RegistryObject<Item> CANISTER = SIMPLEITEMS.register("canister", FluidCanister::new);

    public SimplyUtilities() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfiguration.SERVER_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfiguration.COMMON_CONFIG);

        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        SIMPLEITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.addListener(this::onCommandsRegister);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, DoubleDoorFix::playerInteraction);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onConfigReload);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(Generator::gatherData);

        MinecraftForge.EVENT_BUS.addListener(EnderInhibitor::TeleportEvent);
        MinecraftForge.EVENT_BUS.addListener(ExoLeggings::onEntityHurt);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        DeferredWorkQueue.runLater(() -> {
            CraftingHelper.register(BoolConfigCondition.Serializer.INSTANCE);
        });
        NETWORK = SUNetwork.getNetworkChannel();
        isQuarkLoaded = ModList.get().isLoaded("quark");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
    }

    private void onCommandsRegister(RegisterCommandsEvent event) {
        MyCommands.register(event.getDispatcher());
    }

    private void onConfigReload(ModConfig.ModConfigEvent event) {
        ConfigCache.RefreshCache();
    }


}
