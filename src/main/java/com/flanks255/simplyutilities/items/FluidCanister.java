package com.flanks255.simplyutilities.items;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.capabilities.CapFluidProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;

public class FluidCanister extends Item {
    public FluidCanister() {
        super(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_TOOLS));
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level worldIn, @Nonnull Player playerIn, @Nonnull InteractionHand handIn) {
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        SimplyUtilities.LOGGER.info("getDurability");
        return 0.5;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return new CapFluidProvider(stack);
    }
}
