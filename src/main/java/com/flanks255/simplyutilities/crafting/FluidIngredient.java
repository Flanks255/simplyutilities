package com.flanks255.simplyutilities.crafting;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FluidIngredient extends Ingredient {
    private final Boolean bucketsOnly;
    private final List<Fluid> matchingFluids;
    private final ResourceLocation specificFluid;
    private final ITag<Fluid> fluidITag;
    private final int fluidAmount;
    private ItemStack[] bucketCache;


    protected FluidIngredient(Stream<? extends IItemList> itemLists) {
        super(itemLists);
    }

    public static FluidIngredient fromTag(ITag<Fluid> tagIn, int amount, boolean bucketOnly) {

    }

    @Nonnull
    @Override
    public ItemStack[] getMatchingStacks() {
        if (bucketCache == null) {
            List<ItemStack> tmp = new ArrayList<>();
            matchingFluids.forEach((fluid -> {
                ItemStack newBucket = FluidUtil.getFilledBucket(new FluidStack(fluid, 1000));
                if (!newBucket.isEmpty())
                    tmp.add(newBucket);
            }));
            bucketCache = tmp.toArray(tmp.toArray(new ItemStack[0]));
        }
        return bucketCache;
    }

    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return SERIALIZER;
    }
    public static Serializer SERIALIZER = new Serializer();
    public static class Serializer implements IIngredientSerializer<FluidIngredient> {
        public static ResourceLocation NAME = new ResourceLocation(SimplyUtilities.MODID, "fluid");
        @Override
        public FluidIngredient parse(PacketBuffer buffer) {
            return null;
        }

        @Override
        public FluidIngredient parse(JsonObject json) {
            int fluidAmount = JSONUtils.getInt(json, "amount");
            boolean bucketOnly = !json.has("bucketsOnly") || JSONUtils.getBoolean(json, "bucketsOnly");
            if (json.has("tag")) {
                ResourceLocation tagRes = new ResourceLocation(JSONUtils.getString(json, "tag"));
                ITag<Fluid> fluidTag = TagCollectionManager.getManager().getFluidTags().get(tagRes);
                if (fluidTag != null)
                    return FluidIngredient.fromTag(fluidTag, fluidAmount, bucketOnly);
                else
                    throw new JsonSyntaxException("invalid fluid tag: " + tagRes);
            }
            else if (json.has("fluid")) {

            }
            else {
                throw new JsonSyntaxException("invalid FluidIngredient, must specify either a fluid, or a fluid tag.");
            }

            return null; //TODO temp
        }

        @Override
        public void write(PacketBuffer buffer, FluidIngredient ingredient) {

        }
    }

    @Override
    public boolean test(@Nullable ItemStack p_test_1_) {
        return super.test(p_test_1_);
    }
}
