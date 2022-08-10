package com.flanks255.simplyutilities.items;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ExoLeggings extends ArmorItem {
    public ExoLeggings() {
        super(new ExoMaterial(), EquipmentSlot.LEGS,  (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT));
    }

    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.LEGS;
    }

    private boolean hasTranslation(String key) {
        return I18n.exists(key);
    }

    private String fallbackString(String key, String fallback) {
        if(hasTranslation(key))
            return I18n.get(key);
        else
            return fallback;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable(this.getDescriptionId() + ".info"));
            if (hasTranslation(this.getDescriptionId()+".info2"))
                tooltip.add(Component.translatable(this.getDescriptionId() + ".info2"));
            if (hasTranslation(this.getDescriptionId()+".info3"))
                tooltip.add(Component.translatable(this.getDescriptionId() + ".info3"));
        }
        else {
            tooltip.add(Component.translatable("su.moreinfo", Component.translatable("su.key.shift").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC)));
        }
    }

    public static void onEntityHurt(LivingFallEvent event) {
        if (event.getEntity() instanceof Player && event.getDistance() > 3) {
            Player playerEntity = (Player) event.getEntity();
            ItemStack pants = playerEntity.getItemBySlot(EquipmentSlot.LEGS);
            if (pants.getItem() instanceof ExoLeggings)
                event.setCanceled(true);
        }
    }
}
