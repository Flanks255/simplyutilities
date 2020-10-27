package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.configuration.CommonConfiguration;
import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.google.gson.JsonObject;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class BoolConfigCondition implements ICondition {
    private static final ResourceLocation NAME = new ResourceLocation(SimplyUtilities.MODID, "bool_config_condition");
    String boolconfig;

    public BoolConfigCondition(String config) {
        this.boolconfig = config;
    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test() {
        switch (boolconfig) {
            default:
                return true;
            case "smeltFleshIntoLeather":
                return CommonConfiguration.RECIPE_FLESH_LEATHER.get();
            case "craftLogsToSticks":
                return CommonConfiguration.RECIPE_LOG_STICK.get();
            case "craftLogsToChests":
                return CommonConfiguration.RECIPE_LOG_CHESTS.get();
            case "enableEnderInhibitor":
                return ConfigCache.EnderInhibitorEnabled;
            case "exoleggings":
                return CommonConfiguration.EXO_LEGGINGS.get();
        }
    }

    public static class Serializer implements IConditionSerializer<BoolConfigCondition> {
        public static final BoolConfigCondition.Serializer INSTANCE = new BoolConfigCondition.Serializer();

        @Override
        public void write(JsonObject json, BoolConfigCondition value) {
            json.addProperty("config_name", value.boolconfig);
        }

        @Override
        public BoolConfigCondition read(JsonObject json) {
            return new BoolConfigCondition(JSONUtils.getString(json, "config_name"));
        }

        @Override
        public ResourceLocation getID() {
            return BoolConfigCondition.NAME;
        }
    }
}
