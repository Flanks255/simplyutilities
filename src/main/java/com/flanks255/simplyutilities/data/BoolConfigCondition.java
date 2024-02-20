package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.configuration.CommonConfiguration;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public record BoolConfigCondition(String configOption) implements ICondition {
    public static final Codec<BoolConfigCondition> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            com.mojang.serialization.Codec.STRING.fieldOf("config_name").forGetter(BoolConfigCondition::configOption)
    ).apply(instance, BoolConfigCondition::new));
    @Nonnull
    @Override
    public Codec<? extends ICondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@NotNull IContext context) {
        return switch (configOption) {
            default -> true;
            case "smeltFleshIntoLeather" -> CommonConfiguration.RECIPE_FLESH_LEATHER.get();
            case "craftLogsToSticks" -> CommonConfiguration.RECIPE_LOG_STICK.get();
            case "craftLogsToChests" -> CommonConfiguration.RECIPE_LOG_CHESTS.get();
            case "enableEnderInhibitor" -> CommonConfiguration.ENDERINHIBITOR_ENABLE.get();
            case "exoleggings" -> CommonConfiguration.EXO_LEGGINGS.get();
            case "online_detector" -> CommonConfiguration.ONLINE_DETECTOR.get();
        };
    }
}
