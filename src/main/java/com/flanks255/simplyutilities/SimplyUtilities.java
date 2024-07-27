package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.blocks.EnderInhibitor;
import com.flanks255.simplyutilities.commands.MyCommands;
import com.flanks255.simplyutilities.configuration.ClientConfiguration;
import com.flanks255.simplyutilities.configuration.CommonConfiguration;
import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.flanks255.simplyutilities.configuration.ServerConfiguration;
import com.flanks255.simplyutilities.crafting.RightClickRecipe;
import com.flanks255.simplyutilities.data.Generator;
import com.flanks255.simplyutilities.items.ExoLeggings;
import com.flanks255.simplyutilities.network.SUNetwork;
import com.flanks255.simplyutilities.render.ModelLayers;
import com.flanks255.simplyutilities.tweaks.DoubleDoorFix;
import com.flanks255.simplyutilities.tweaks.MobGriefProtection;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("simplyutilities")
public class SimplyUtilities
{
    public static final String MODID = "simplyutilities";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static boolean isQuarkLoaded = false;

    public static final NonNullList<KeyMapping> keyBinds = NonNullList.create();

    public SimplyUtilities(IEventBus modBus, ModContainer container, Dist dist) {
        IEventBus neoBus = NeoForge.EVENT_BUS;

        SUTags.init();
        SUBlocks.init(modBus);
        SUItems.init(modBus);
        SUCrafting.init(modBus);
        SUConditions.init(modBus);
        SUMaterials.init(modBus);

        modBus.addListener(ModelLayers::registerLayerDefinitions);
        modBus.addListener(ModelLayers::registerEntityRenderers);

        // Configs
        container.registerConfig(ModConfig.Type.SERVER, ServerConfiguration.SERVER_CONFIG);
        container.registerConfig(ModConfig.Type.COMMON, CommonConfiguration.COMMON_CONFIG);
        container.registerConfig(ModConfig.Type.CLIENT, ClientConfiguration.CLIENT_CONFIG);
        modBus.addListener(ConfigCache::listen);

        // Commands
        neoBus.addListener(this::onCommandsRegister);

        modBus.addListener(SUNetwork::register);

        // Data Generators
        modBus.addListener(Generator::gatherData);

        // Misc Event Hooks
        modBus.addListener(this::setup);
        if (dist == Dist.CLIENT) {
            modBus.addListener(this::doClientStuff);
            modBus.addListener(SUClientEvents::registerKeyBinding);
            modBus.addListener(this::creativeTabEvent);
            modBus.addListener(SUClientEvents::onClientExtensions);
        }
        neoBus.addListener(EnderInhibitor::TeleportEvent);
        neoBus.addListener(EnderInhibitor::PearlTeleportEvent);
        neoBus.addListener(ExoLeggings::onEntityHurt);
        neoBus.addListener(EventPriority.LOWEST, RightClickRecipe::RightClickEvent);
        neoBus.addListener(EventPriority.LOWEST, DoubleDoorFix::playerInteraction);
        neoBus.addListener(EventPriority.LOWEST, MobGriefProtection::mobGriefingEvent);

    }

    private void setup(final FMLCommonSetupEvent event)
    {
        isQuarkLoaded = ModList.get().isLoaded("quark");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.addListener(SUClientEvents::onViewportEvent);
    }

    private void onCommandsRegister(RegisterCommandsEvent event) {
        MyCommands.register(event.getDispatcher());
    }


    private void creativeTabEvent(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(SUBlocks.ENDER_INHIBITOR.get());
            event.accept(SUBlocks.ONLINE_DETECTOR.get());
        }
        else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(SUItems.MINICHARCOAL.get());
            event.accept(SUItems.MINICOAL.get());
        }
        else if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(SUBlocks.CHARCOAL_BLOCK.get());
            event.accept(SUBlocks.ENDER_PEARL_BLOCK.get());
        }
        else if (event.getTabKey() == CreativeModeTabs.COMBAT)
            event.accept(SUItems.EXOLEGGINGS.get());
    }
}