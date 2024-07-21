package com.flanks255.simplyutilities.items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class SUItem extends Item {
    public SUItem(Properties pProperties) {
        super(pProperties);
    }

    private boolean hasTranslation(String key) {
        return I18n.exists(key);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nonnull TooltipContext context, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);
        boolean hasAny = hasTranslation(stack.getDescriptionId() + ".info");
        if (Screen.hasShiftDown() && hasAny) {
            tooltip.add(Component.translatable(stack.getDescriptionId() + ".info"));
            if (hasTranslation(stack.getDescriptionId()+".info2"))
                tooltip.add(Component.translatable(stack.getDescriptionId() + ".info2"));
            if (hasTranslation(stack.getDescriptionId()+".info3"))
                tooltip.add(Component.translatable(stack.getDescriptionId() + ".info3"));
        }
        else {
            if (hasAny)
                tooltip.add(Component.translatable("su.moreinfo", Component.translatable("su.key.shift").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC)));
        }
    }
}
