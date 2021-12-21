package com.flanks255.simplyutilities;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class SUBlockReg <B extends Block, I extends Item, T extends BlockEntity> implements Supplier<B>{
    private String name;
    private final RegistryObject<B> block;
    private final RegistryObject<I> item;
    private RegistryObject<BlockEntityType<T>> tile;

    @Override
    public B get() {
        return block.get();
    }
    public String getName() {
        return name;
    }

    public SUBlockReg(String name, Supplier<B> blockSupplier, Function<B, I> itemSupplier) {
        this.name = name;
        block = SUBlocks.BLOCKS.register(name, blockSupplier);
        item = SUItems.ITEMS.register(name, () -> itemSupplier.apply(block.get()));
    }

    public SUBlockReg(String name, Supplier<B> blockSupplier, Function<B, I> itemSupplier, BlockEntityType.BlockEntitySupplier<T> tileSupplier) {
        this.name = name;
        block = SUBlocks.BLOCKS.register(name, blockSupplier);
        item = SUItems.ITEMS.register(name, () -> itemSupplier.apply(block.get()));
        tile = SUBlocks.TILE_ENTITIES.register(name, () -> BlockEntityType.Builder.of(tileSupplier, block.get()).build(null));
    }

    public B getBlock() {
        return block.get();
    }

    public I getItem() {
        return item.get();
    }

    @Nonnull
    public BlockEntityType<T> getBlockEntityType() {
        return Objects.requireNonNull(tile).get();
    }
}
