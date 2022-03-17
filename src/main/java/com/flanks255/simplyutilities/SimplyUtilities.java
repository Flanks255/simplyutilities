package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.blocks.EnderInhibitor;
import com.flanks255.simplyutilities.commands.MyCommands;
import com.flanks255.simplyutilities.configuration.ClientConfiguration;
import com.flanks255.simplyutilities.configuration.CommonConfiguration;
import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.flanks255.simplyutilities.configuration.ServerConfiguration;
import com.flanks255.simplyutilities.crafting.FluidIngredient;
import com.flanks255.simplyutilities.crafting.RightClickRecipe;
import com.flanks255.simplyutilities.crafting.TargetNBTIngredient;
import com.flanks255.simplyutilities.data.BoolConfigCondition;
import com.flanks255.simplyutilities.data.Generator;
import com.flanks255.simplyutilities.items.ExoLeggings;
import com.flanks255.simplyutilities.network.SUNetwork;
import com.flanks255.simplyutilities.render.ModelLayers;
import com.flanks255.simplyutilities.tweaks.DoubleDoorFix;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("simplyutilities")
public class SimplyUtilities
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "simplyutilities";
    public static SimpleChannel NETWORK;

    public static boolean isQuarkLoaded = false;

    private final NonNullList<KeyMapping> keyBinds = NonNullList.create();

    public SimplyUtilities() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        SUBlocks.init(modBus);
        SUItems.init(modBus);
        SUCrafting.init(modBus);

        modBus.addListener(ModelLayers::registerLayerDefinitions);
        modBus.addListener(ModelLayers::registerEntityRenderers);

        modBus.addGenericListener(Item.class, this::onRecipeTypeRegister);

        // Configs
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfiguration.SERVER_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfiguration.COMMON_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfiguration.CLIENT_CONFIG);
        modBus.addListener(this::onConfigReload);

        // Commands
        MinecraftForge.EVENT_BUS.addListener(this::onCommandsRegister);

        // Data Generators
        modBus.addListener(Generator::gatherData);

        // Misc Event Hooks
        modBus.addListener(this::setup);
        modBus.addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.addListener(EnderInhibitor::TeleportEvent);
        MinecraftForge.EVENT_BUS.addListener(EnderInhibitor::PearlTeleportEvent);
        MinecraftForge.EVENT_BUS.addListener(ExoLeggings::onEntityHurt);
        //MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, RightClickRecipe::RightClickEvent);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, DoubleDoorFix::playerInteraction);

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
        MinecraftForge.EVENT_BUS.addListener(this::onRenderViewEvent);

        keyBinds.add(0, new KeyMapping("key.simplyutilities.zoom.desc", -1, "key.simplyutilities.category"));
        ClientRegistry.registerKeyBinding(keyBinds.get(0));
    }

    private void onCommandsRegister(RegisterCommandsEvent event) {
        MyCommands.register(event.getDispatcher());
    }

    private void onConfigReload(ModConfigEvent event) {
        ConfigCache.RefreshCache();
    }

    private void onRecipeTypeRegister(final RegistryEvent.Register<Item> event) {
        SUCrafting.Types.RIGHT_CLICK = RecipeType.register(SimplyUtilities.MODID + ":right_click");
    }

    @OnlyIn(Dist.CLIENT)
    private void onRenderViewEvent(EntityViewRenderEvent.FieldOfView event) {
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


}