package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.utils.MiscUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;

public class SUMaterials {
    public static final DeferredRegister<ArmorMaterial> MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, SimplyUtilities.MODID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> EXO = MATERIALS.register("exo", () -> new ArmorMaterial(
            Map.of(
                    ArmorItem.Type.BODY, 2,
                    ArmorItem.Type.BOOTS, 2,
                    ArmorItem.Type.LEGGINGS, 2,
                    ArmorItem.Type.HELMET, 2,
                    ArmorItem.Type.CHESTPLATE, 2),
            0, SoundEvents.ARMOR_EQUIP_GENERIC,
            () -> Ingredient.of(Items.IRON_INGOT),
            List.of(new ArmorMaterial.Layer(MiscUtils.rl("exo"))),
            1.0f, 0.0f
    ));

    public static void init(IEventBus eventBus) {
        MATERIALS.register(eventBus);
    }
}
