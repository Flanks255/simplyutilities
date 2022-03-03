//package com.flanks255.simplyutilities;
// //TODO disabled for now till JEI updates.
//import mezz.jei.api.IModPlugin;
//import mezz.jei.api.JeiPlugin;
//import mezz.jei.api.constants.VanillaTypes;
//import mezz.jei.api.registration.IRecipeRegistration;
//import net.minecraft.client.resources.language.I18n;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.resources.ResourceLocation;
//
//import javax.annotation.Nonnull;
//
//@JeiPlugin
//public class JEIPlugin implements IModPlugin {
//    public static final ResourceLocation ID = new ResourceLocation(SimplyUtilities.MODID, "jei_plugin");
//    @Nonnull
//    @Override
//    public ResourceLocation getPluginUid() {
//        return ID;
//    }
//
//    @Override
//    public void registerRecipes(@Nonnull IRecipeRegistration registration) {
//        SUItems.ITEMS.getEntries().forEach((item) -> {
//            String key = item.get().getDescriptionId()+".jei.info";
//            if (I18n.exists(key)) {
//                registration.addIngredientInfo(new ItemStack(item.get()), VanillaTypes.ITEM, new TextComponent(I18n.get(key)));            }
//        });
//    }
//}
