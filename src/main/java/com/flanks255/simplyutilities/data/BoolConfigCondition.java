package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.configuration.CommonConfiguration;
import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class BoolConfigCondition implements ICondition {
    private static final ResourceLocation NAME = new ResourceLocation(SimplyUtilities.MODID, "bool_config_condition");
    private final String boolConfig;

    public BoolConfigCondition(String config) {
        this.boolConfig = config;
    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        return switch (boolConfig) {
            default -> true;
            case "smeltFleshIntoLeather" -> CommonConfiguration.RECIPE_FLESH_LEATHER.get();
            case "craftLogsToSticks" -> CommonConfiguration.RECIPE_LOG_STICK.get();
            case "craftLogsToChests" -> CommonConfiguration.RECIPE_LOG_CHESTS.get();
            case "enableEnderInhibitor" -> ConfigCache.EnderInhibitorEnabled;
            case "exoleggings" -> CommonConfiguration.EXO_LEGGINGS.get();
            case "online_detector" -> CommonConfiguration.ONLINE_DETECTOR.get();
        };
    }

    public static class Serializer implements IConditionSerializer<BoolConfigCondition> {
        public static final BoolConfigCondition.Serializer INSTANCE = new BoolConfigCondition.Serializer();

        @Override
        public void write(JsonObject json, BoolConfigCondition value) {
            json.addProperty("config_name", value.boolConfig);
        }

        @Override
        public BoolConfigCondition read(JsonObject json) {
            return new BoolConfigCondition(GsonHelper.getAsString(json, "config_name"));
        }

        @Override
        public ResourceLocation getID() {
            return BoolConfigCondition.NAME;
        }
    }
}
