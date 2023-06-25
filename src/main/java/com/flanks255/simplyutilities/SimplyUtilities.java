package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.blocks.EnderInhibitor;
import com.flanks255.simplyutilities.commands.MyCommands;
import com.flanks255.simplyutilities.configuration.ClientConfiguration;
import com.flanks255.simplyutilities.configuration.CommonConfiguration;
import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.flanks255.simplyutilities.configuration.ServerConfiguration;
import com.flanks255.simplyutilities.crafting.FluidIngredient;
import com.flanks255.simplyutilities.crafting.TargetNBTIngredient;
import com.flanks255.simplyutilities.data.BoolConfigCondition;
import com.flanks255.simplyutilities.data.Generator;
import com.flanks255.simplyutilities.items.ExoLeggings;
import com.flanks255.simplyutilities.network.SUNetwork;
import com.flanks255.simplyutilities.render.ModelLayers;
import com.flanks255.simplyutilities.tweaks.MobGriefProtection;
import com.flanks255.simplyutilities.tweaks.DoubleDoorFix;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.simple.SimpleChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("simplyutilities")
public class SimplyUtilities
{
    public static final String MODID = "simplyutilities";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static SimpleChannel NETWORK;

    public static boolean isQuarkLoaded = false;

    private final NonNullList<KeyMapping> keyBinds = NonNullList.create();

    public SimplyUtilities() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        SUTags.init();
        SUBlocks.init(modBus);
        SUItems.init(modBus);
        SUCrafting.init(modBus);

        modBus.addListener(ModelLayers::registerLayerDefinitions);
        modBus.addListener(ModelLayers::registerEntityRenderers);

        // Configs
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfiguration.SERVER_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfiguration.COMMON_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfiguration.CLIENT_CONFIG);
        modBus.addListener(ConfigCache::listen);

        // Commands
        MinecraftForge.EVENT_BUS.addListener(this::onCommandsRegister);

        // Data Generators
        modBus.addListener(Generator::gatherData);

        // Misc Event Hooks
        modBus.addListener(this::setup);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modBus.addListener(this::doClientStuff);
            modBus.addListener(this::registerKeyBinding);
            modBus.addListener(this::creativeTabEvent);
        }
        modBus.addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.addListener(EnderInhibitor::TeleportEvent);
        MinecraftForge.EVENT_BUS.addListener(EnderInhibitor::PearlTeleportEvent);
        MinecraftForge.EVENT_BUS.addListener(ExoLeggings::onEntityHurt);
        //MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, RightClickRecipe::RightClickEvent);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, DoubleDoorFix::playerInteraction);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, MobGriefProtection::mobGriefingEvent);

    }

    private void setup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            CraftingHelper.register(BoolConfigCondition.Serializer.INSTANCE);
            CraftingHelper.register(FluidIngredient.Serializer.NAME, FluidIngredient.SERIALIZER);
            CraftingHelper.register(TargetNBTIngredient.Serializer.NAME, TargetNBTIngredient.SERIALIZER);
        });
        NETWORK = SUNetwork.getNetworkChannel();
        isQuarkLoaded = ModList.get().isLoaded("quark");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.addListener(this::onViewportEvent);
    }
    @OnlyIn(Dist.CLIENT)
    private void registerKeyBinding(final RegisterKeyMappingsEvent event) {
        this.keyBinds.add(0, new KeyMapping("key.simplyutilities.zoom.desc", -1, "key.simplyutilities.category"));
        event.register(keyBinds.get(0));
    }

    private void onCommandsRegister(RegisterCommandsEvent event) {
        MyCommands.register(event.getDispatcher());
    }

    @OnlyIn(Dist.CLIENT)
    private void onViewportEvent(ViewportEvent.ComputeFov event) {
        if (keyBinds.get(0).isDown()) {
            event.setFOV(ConfigCache.zoom_fov);
            if (ConfigCache.zoom_smooth)
                Minecraft.getInstance().options.smoothCamera = true;
        }
        else {
            if (ConfigCache.zoom_smooth)
                Minecraft.getInstance().options.smoothCamera = false;
        }
    }

    private void creativeTabEvent(final CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(SUBlocks.ENDER_INHIBITOR.get());
            event.accept(SUBlocks.ONLINE_DETECTOR.get());
        }
        else if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
            event.accept(SUItems.MINICHARCOAL.get());
            event.accept(SUItems.MINICOAL.get());
        }
        else if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(SUBlocks.CHARCOAL_BLOCK.get());
            event.accept(SUBlocks.ENDER_PEARL_BLOCK.get());
        }
        else if (event.getTab() == CreativeModeTabs.COMBAT)
            event.accept(SUItems.EXOLEGGINGS.get());
    }
}