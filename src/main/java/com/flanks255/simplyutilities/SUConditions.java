package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.data.BoolConfigCondition;
import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class SUConditions {
    public static void init(IEventBus bus) {
        CONDITIONS.register(bus);
    }

    public static final DeferredRegister<Codec<? extends ICondition>> CONDITIONS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, SimplyUtilities.MODID);

    public static final DeferredHolder<Codec<? extends ICondition>, Codec<BoolConfigCondition>> BOOL_CONFIG = CONDITIONS.register("bool_config", () -> BoolConfigCondition.CODEC);
}
