package com.flanks255.simplyutilities.crafting;

import com.flanks255.simplyutilities.SUCrafting;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Stream;

public class FluidIngredient extends Ingredient {
    public static final Codec<FluidIngredient> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(FluidValue.CODEC.fieldOf("value")
                            .forGetter($ -> $.value),
                    Codec.BOOL.fieldOf("advanced")
                            .forGetter($ -> $.advanced))
            .apply(instance, FluidIngredient::new));
    private final Boolean advanced;
    private final List<Fluid> matchingFluids = new ArrayList<>();
    private ItemStack[] bucketCache = null;
    private IntList matchingStacksPacked;
    public final FluidValue value;

    private List<Fluid> getMatchingFluids() {
        if (!matchingFluids.isEmpty())
            return matchingFluids;

        if (value instanceof FluidTagValue fluidTagValue)
            BuiltInRegistries.FLUID.getTagOrEmpty(fluidTagValue.tag).forEach(fluid -> matchingFluids.add(fluid.value()));

        return matchingFluids;
    }


    public FluidIngredient(TagKey<Fluid> tagIn, int amount, boolean advancedIn) {
        super(Stream.of(), SUCrafting.FLUID_INGREDIENT);

        advanced = advancedIn;
        value = new FluidTagValue(tagIn, amount);
    }
    public FluidIngredient(TagKey<Fluid> tagIn) {
        this(tagIn, 1000, false);
    }
    public FluidIngredient(Fluid fluidIn, int amount, boolean advancedIn) {
        super(Stream.of(), SUCrafting.FLUID_INGREDIENT);

        value = new SpecificFluidValue(new FluidStack(fluidIn, amount));
        advanced = advancedIn;
        matchingFluids.add(fluidIn);
    }
    public FluidIngredient(Fluid fluidIn) {
        this(fluidIn, 1000, false);
    }

    public FluidIngredient(FluidValue value, boolean advancedIn) {
        super(Stream.of(), SUCrafting.FLUID_INGREDIENT);
        this.value = value;
        advanced = advancedIn;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Nonnull
    @Override
    public IntList getStackingIds() {
        if (this.matchingStacksPacked == null) {
            this.matchingStacksPacked = new IntArrayList(this.getItems().length);
            for(ItemStack itemstack : this.bucketCache) {
                this.matchingStacksPacked.add(StackedContents.getStackingIndex(itemstack));
            }
            this.matchingStacksPacked.sort(IntComparators.NATURAL_COMPARATOR);
        }
        return this.matchingStacksPacked;
    }

    @Nonnull
    @Override
    public ItemStack[] getItems() {
        if (bucketCache == null) {
            List<ItemStack> tmp = new ArrayList<>();
            getMatchingFluids().forEach((fluid -> {
                ItemStack newBucket = FluidUtil.getFilledBucket(new FluidStack(fluid, 1000));
                if (!newBucket.isEmpty())
                    tmp.add(newBucket);
            }));
            bucketCache = tmp.toArray(tmp.toArray(new ItemStack[0]));
        }
        return bucketCache;
    }

    public interface FluidValue {
        Codec<FluidValue> CODEC = ExtraCodecs.xor(SpecificFluidValue.CODEC, FluidTagValue.CODEC)
                .xmap($ -> $.map(value1 -> value1, value2 -> value2), fluidValue -> {
                    if (fluidValue instanceof FluidTagValue fluidTagValue) {
                        return Either.right(fluidTagValue);
                    } else if (fluidValue instanceof SpecificFluidValue specificFluidValue) {
                        return Either.left(specificFluidValue);
                    } else {
                        throw new UnsupportedOperationException("This is neither a fluid nor a fluid tag.");
                    }
                });
        int getAmount();
        Collection<FluidStack> getFluids();
    }

    public record SpecificFluidValue(FluidStack fluidStack) implements FluidValue {
        public static final Codec<SpecificFluidValue> CODEC = RecordCodecBuilder.create((p_300958_) -> p_300958_
                .group(FluidStack.CODEC.fieldOf("fluid")
                        .forGetter((p_300960_) -> p_300960_.fluidStack))
                .apply(p_300958_, SpecificFluidValue::new));

        @Override
        public int getAmount() {
            return fluidStack.getAmount();
        }

        @Override
        public Collection<FluidStack> getFluids() {
            return Collections.singleton(fluidStack);
        }
    }

    public record FluidTagValue(TagKey<Fluid> tag, int amount) implements FluidValue {
        public static final Codec<FluidTagValue> CODEC = RecordCodecBuilder.create((p_300962_) -> p_300962_
                .group(TagKey.codec(Registries.FLUID).fieldOf("Tag")
                                .forGetter((p_300964_) -> p_300964_.tag),
                        Codec.INT.fieldOf("Amount")
                                .forGetter((p_300966_) -> p_300966_.amount))
                .apply(p_300962_, FluidTagValue::new));

        @Override
        public int getAmount() {
            return amount;
        }

        @Override
        public Collection<FluidStack> getFluids() {
            List<FluidStack> list = new ArrayList<>();

            for (Holder<Fluid> holder : BuiltInRegistries.FLUID.getTagOrEmpty(tag)) {
                list.add(new FluidStack(holder.value(), 1000));
            }

            if (list.isEmpty())
                list.add(FluidStack.EMPTY);

            return list;
        }
    }

    @Override
    public boolean test(@Nullable ItemStack stack) {
        if (stack == null)
            return false;
        Optional<IFluidHandlerItem> cap = Optional.ofNullable(stack.getCapability(Capabilities.FluidHandler.ITEM));

        if (cap.isPresent()) {
            if (!advanced && !(stack.getItem() instanceof BucketItem))
                return false;

            FluidStack fluid = cap.get().getFluidInTank(0);
            return getMatchingFluids().contains(fluid.getFluid()) && fluid.getAmount() >= value.getAmount();
        }

        return false;
    }
}
