package com.flanks255.simplyutilities;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    public static final ResourceLocation ID = new ResourceLocation(SimplyUtilities.MODID, "jei_plugin");
    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration) {
        SUItems.ITEMS.getEntries().forEach((item) -> {
            String key = item.get().getTranslationKey()+".jei.info";
            if (I18n.hasKey(key)) {
                String langEntry = TextFormatting.getTextWithoutFormattingCodes(I18n.format(key));
                if (langEntry != null)
                    registration.addIngredientInfo(new ItemStack(item.get()), VanillaTypes.ITEM, StringUtils.splitByWholeSeparator(langEntry, "//n"));
            }
        });
    }
}
