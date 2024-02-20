package com.flanks255.simplyutilities.items;

import net.minecraft.world.level.block.Block;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class SUBlockItem extends BlockItem {
    public SUBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
        this.block = blockIn;
    }

    private final Block block;
    private boolean hasTranslation(String key) {
        return I18n.exists(key);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        boolean hasAny = hasTranslation(block.getDescriptionId() + ".info");
        if (Screen.hasShiftDown() && hasAny) {
            tooltip.add(Component.translatable(block.getDescriptionId() + ".info"));
            if (hasTranslation(block.getDescriptionId()+".info2"))
                tooltip.add(Component.translatable(block.getDescriptionId() + ".info2"));
            if (hasTranslation(block.getDescriptionId()+".info3"))
                tooltip.add(Component.translatable(block.getDescriptionId() + ".info3"));
        }
        else {
            if (hasAny)
                tooltip.add(Component.translatable("su.moreinfo", Component.translatable("su.key.shift").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC)));
        }
    }
}
