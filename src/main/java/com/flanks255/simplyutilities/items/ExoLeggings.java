package com.flanks255.simplyutilities.items;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import javax.annotation.Nullable;
import java.util.List;

public class ExoLeggings extends ArmorItem {
    public ExoLeggings() {
        super(new ExoMaterial(), EquipmentSlotType.LEGS,  (new Item.Properties()).group(ItemGroup.COMBAT));
    }

    @Override
    public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
        return EquipmentSlotType.LEGS;
    }

    private boolean hasTranslation(String key) {
        return !I18n.format(key).equals(key);
    }

    private String fallbackString(String key, String fallback) {
        if(hasTranslation(key))
            return I18n.format(key);
        else
            return fallback;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent(this.getTranslationKey() + ".info"));
            if (hasTranslation(this.getTranslationKey()+".info2"))
                tooltip.add(new TranslationTextComponent(this.getTranslationKey() + ".info2"));
            if (hasTranslation(this.getTranslationKey()+".info3"))
                tooltip.add(new TranslationTextComponent(this.getTranslationKey() + ".info3"));
        }
        else {
            tooltip.add(new TranslationTextComponent("su.moreinfo", new TranslationTextComponent("su.key.shift").mergeStyle(TextFormatting.GOLD, TextFormatting.ITALIC)));
        }
    }

    public static void onEntityHurt(LivingFallEvent event) {
        if (event.getEntity() instanceof PlayerEntity && event.getDistance() > 3) {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntity();
            ItemStack pants = playerEntity.getItemStackFromSlot(EquipmentSlotType.LEGS);
            if (pants.getItem() instanceof ExoLeggings)
                event.setCanceled(true);
        }
    }
}
