package com.flanks255.simplyutilities.items;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.capabilities.CapFluidProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class FluidCanister extends Item {
    public FluidCanister() {
        super(new Item.Properties().maxStackSize(1).group(ItemGroup.TOOLS));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return super.onItemRightClick(worldIn, playerIn, handIn);
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
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
        return new CapFluidProvider(stack);
    }
}
