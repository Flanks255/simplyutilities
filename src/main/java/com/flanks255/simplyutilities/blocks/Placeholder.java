package com.flanks255.simplyutilities.blocks;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.items.SUBlockItem;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Placeholder extends Block {
    public Placeholder() {
        super(AbstractBlock.Properties.create(Material.WOOD).zeroHardnessAndResistance().setAllowsSpawn((a,b,c,d) -> false));
    }


    public static class PlaceHolderItem extends SUBlockItem {
        public PlaceHolderItem(Block blockIn) {
            super(blockIn, new Item.Properties().group(ItemGroup.MISC).maxStackSize(64));
        }


        @Override
        public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
            BlockPos targetPos = new BlockPos(playerIn.getEyePosition(0).add(playerIn.getLookVec())); //hopefully 1 block away in the direction the eyes are looking.
            BlockState targetState = worldIn.getBlockState(targetPos);

            if (targetState.getBlock() instanceof AirBlock || targetState.getBlock() instanceof FlowingFluidBlock) {
                if (!worldIn.setBlockState(targetPos, getBlock().getDefaultState(), 11))
                    return ActionResult.resultFail(playerIn.getHeldItem(handIn));

                if (!playerIn.abilities.isCreativeMode)
                    playerIn.getHeldItem(handIn).shrink(1);
                return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
            }
            return ActionResult.resultFail(playerIn.getHeldItem(handIn));
        }
    }
}
