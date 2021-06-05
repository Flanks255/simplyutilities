package com.flanks255.simplyutilities;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class SUBlockReg <B extends Block, I extends Item, T extends TileEntity> implements Supplier<B>{
    private String name;
    private final RegistryObject<B> block;
    private final RegistryObject<I> item;
    private RegistryObject<TileEntityType<T>> tile;

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

    public SUBlockReg(String name, Supplier<B> blockSupplier, Function<B, I> itemSupplier, Supplier<T> tileSupplier) {
        this.name = name;
        block = SUBlocks.BLOCKS.register(name, blockSupplier);
        item = SUItems.ITEMS.register(name, () -> itemSupplier.apply(block.get()));
        tile = SUBlocks.TILE_ENTITIES.register(name, () -> TileEntityType.Builder.create(tileSupplier, block.get()).build(null));
    }

    public B getBlock() {
        return block.get();
    }

    public I getItem() {
        return item.get();
    }

    @Nonnull
    public TileEntityType<T> getTileEntityType() {
        return Objects.requireNonNull(tile).get();
    }
}
