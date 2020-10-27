package com.flanks255.simplyutilities.items;

import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class ExoMaterial implements IArmorMaterial {
    @Override
    public int getDurability(EquipmentSlotType slotIn) {
        return 256;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return 2;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromItems(Items.IRON_INGOT);
    }

    @Override
    public String getName() {
        return SimplyUtilities.MODID + ":exo";
    }

    @Override
    public float getToughness() {
        return 1.0f;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
