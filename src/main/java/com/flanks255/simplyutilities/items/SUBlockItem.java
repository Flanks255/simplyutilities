package com.flanks255.simplyutilities.items;

import net.minecraft.world.level.block.Block;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
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
        return I18n.exists(key);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (Screen.hasShiftDown()) {
            tooltip.add(new TranslatableComponent(block.getDescriptionId() + ".info"));
            if (hasTranslation(block.getDescriptionId()+".info2"))
                tooltip.add(new TranslatableComponent(block.getDescriptionId() + ".info2"));
            if (hasTranslation(block.getDescriptionId()+".info3"))
                tooltip.add(new TranslatableComponent(block.getDescriptionId() + ".info3"));
        }
        else {
            tooltip.add(new TranslatableComponent("su.moreinfo", new TranslatableComponent("su.key.shift").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC)));
        }
    }
}
