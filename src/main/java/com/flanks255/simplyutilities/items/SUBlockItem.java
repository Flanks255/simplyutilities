package com.flanks255.simplyutilities.items;

import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
        return I18n.hasKey(key);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent(block.getTranslationKey() + ".info"));
            if (hasTranslation(block.getTranslationKey()+".info2"))
                tooltip.add(new TranslationTextComponent(block.getTranslationKey() + ".info2"));
            if (hasTranslation(block.getTranslationKey()+".info3"))
                tooltip.add(new TranslationTextComponent(block.getTranslationKey() + ".info3"));
        }
        else {
            tooltip.add(new TranslationTextComponent("su.moreinfo", new TranslationTextComponent("su.key.shift").mergeStyle(TextFormatting.GOLD, TextFormatting.ITALIC)));
        }
    }
}
