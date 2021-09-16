package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.blocks.EnderInhibitor;
import com.flanks255.simplyutilities.commands.MyCommands;
import com.flanks255.simplyutilities.configuration.ClientConfiguration;
import com.flanks255.simplyutilities.configuration.CommonConfiguration;
import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.flanks255.simplyutilities.configuration.ServerConfiguration;
import com.flanks255.simplyutilities.crafting.FluidIngredient;
import com.flanks255.simplyutilities.data.BoolConfigCondition;
import com.flanks255.simplyutilities.data.Generator;
import com.flanks255.simplyutilities.items.ExoLeggings;
import com.flanks255.simplyutilities.network.ClientNetProxy;
import com.flanks255.simplyutilities.network.CommonNetProxy;
import com.flanks255.simplyutilities.render.OnlineDetectorRender;
import com.flanks255.simplyutilities.tweaks.DoubleDoorFix;
import com.flanks255.simplyutilities.network.SUNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.client.registry.ClientRegistry;
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

@Mod("simplyutilities")
public class SimplyUtilities
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "simplyutilities";
    public static SimpleChannel NETWORK;

    public static CommonNetProxy NETPROXY;

    public static boolean isQuarkLoaded = false;

    private final NonNullList<KeyBinding> keyBinds = NonNullList.create();

    public SimplyUtilities() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        SUBlocks.init(modBus);
        SUItems.init(modBus);

        DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);



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
        MinecraftForge.EVENT_BUS.addListener(ExoLeggings::onEntityHurt);
        MinecraftForge.EVENT_BUS.addListener(SimplyUtilities::registerIngredients);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, DoubleDoorFix::playerInteraction);

        NETPROXY = DistExecutor.safeRunForDist(() -> ClientNetProxy::new, () -> CommonNetProxy::new);
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
        MinecraftForge.EVENT_BUS.addListener(this::onRenderViewEvent);

        ClientRegistry.bindTileEntityRenderer(SUBlocks.ONLINE_DETECTOR.getTileEntityType(), OnlineDetectorRender::new);

        keyBinds.add(0, new KeyBinding("key.simplyutilities.zoom.desc", -1, "key.simplyutilities.category"));
        ClientRegistry.registerKeyBinding(keyBinds.get(0));
    }

    private void onCommandsRegister(RegisterCommandsEvent event) {
        MyCommands.register(event.getDispatcher());
    }

    private void onConfigReload(ModConfig.ModConfigEvent event) {
        ConfigCache.RefreshCache();
    }

    @OnlyIn(Dist.CLIENT)
    private void onRenderViewEvent(EntityViewRenderEvent.FOVModifier event) {
        if (keyBinds.get(0).isKeyDown()) {
            event.setFOV(ConfigCache.zoom_fov);
            if (ConfigCache.zoom_smooth)
                Minecraft.getInstance().gameSettings.smoothCamera = true;
        }
        else {
            if (ConfigCache.zoom_smooth)
                Minecraft.getInstance().gameSettings.smoothCamera = false;
        }
    }

    private static void registerIngredients( RegistryEvent.Register<IRecipeSerializer<?>> evt) {
        CraftingHelper.register(FluidIngredient.Serializer.NAME, FluidIngredient.SERIALIZER);
    }

}