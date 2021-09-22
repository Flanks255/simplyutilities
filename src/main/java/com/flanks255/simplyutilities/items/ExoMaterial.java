package com.flanks255.simplyutilities.items;

import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import javax.annotation.Nonnull;

public class ExoMaterial implements ArmorMaterial {
    @Override
    public int getDurabilityForSlot(@Nonnull EquipmentSlot slotIn) {
        return 256;
    }

    @Override
    public int getDefenseForSlot(@Nonnull EquipmentSlot slotIn) {
        return 2;
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Nonnull
    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }

    @Nonnull
    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Items.IRON_INGOT);
    }

    @Nonnull
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
