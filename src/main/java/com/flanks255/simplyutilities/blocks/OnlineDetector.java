package com.flanks255.simplyutilities.blocks;

import com.flanks255.simplyutilities.tile.BEOnlineDetector;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.UUID;

public class OnlineDetector extends Block implements EntityBlock {
    public OnlineDetector() {
        super(BlockBehaviour.Properties.of(Material.METAL)
        .strength(2.0f)
            .noOcclusion());

        registerDefaultState(getStateDefinition().any().setValue(ON, false));
    }

    public static final BooleanProperty ON = BooleanProperty.create("on");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ON, BlockStateProperties.FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(BlockStateProperties.FACING, context.getClickedFace()).setValue(ON, false);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);

        BlockEntity te = worldIn.getBlockEntity(pos);
        if (te instanceof BEOnlineDetector && placer instanceof Player) {
            ((BEOnlineDetector) te).setPlayer((Player) placer);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if(!worldIn.isClientSide) {
            BlockEntity te = worldIn.getBlockEntity(pos);
            if (te instanceof BEOnlineDetector) {
                UUID uuid = ((BEOnlineDetector) te).getPlayerUUID();
                if (uuid != null) {
                    player.sendMessage(new TextComponent("Monitoring: " + ((BEOnlineDetector) te).getPlayerName()), Util.NIL_UUID);
                }
            }

            return super.use(state, worldIn, pos, player, handIn, hit);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return blockState.getValue(ON)?15:0;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BEOnlineDetector(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState blockState, BlockEntityType<T> entityType) {
        return world.isClientSide? BEOnlineDetector::clientTick : BEOnlineDetector::serverTick;
    }

    /*
    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, @Nullable Direction side) {
        return true;
    }
     */ //TODO later...
}
