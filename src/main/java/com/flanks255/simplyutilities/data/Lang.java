package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.function.Supplier;

public class Lang extends LanguageProvider {
    public Lang(DataGenerator gen) {
        super(gen, SimplyUtilities.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("su.moreinfo" ,"Press <%s> for info.");
        add("su.key.shift", "Shift");

        add("message.su.sethome", "Set new home point (%s)");
        add("message.su.home", "Warping to (%s)");
        add("message.su.maxhomes", "Unable to set new home, max %d homes allowed.");
        add("message.su.removehomesuccess", "Successfully removed home (%s)");
        add("message.su.removehomefail", "Failed to removed home (%s)");

        add("message.su.debug.hand.noitem", "No held item to debug.");

        add("message.su.inhibitororphans", "Removed %d orphaned inhibitors in this dimension.");

        //addItem(SimplyUtilities.CANISTER, "Fluid Canister");
        addBlock(SimplyUtilities.ENDER_INHIBITOR, "Ender Inhibitor");
        addBlockExtra(SimplyUtilities.ENDER_INHIBITOR, ".info", "Prevents Endermen around it from teleporting.");

        addItem(SimplyUtilities.EXOLEGGINGS, "Exoskeleton Leggings");
        addItemExtra(SimplyUtilities.EXOLEGGINGS, ".info", "Prevents fall damage.");
        addItemExtra(SimplyUtilities.EXOLEGGINGS, ".info2", "(Texture WIP)");
    }

    private void addBlockExtra(Supplier<? extends Block> key, String name, String text) {
        add(key.get().getTranslationKey()+name, text);
    }

    private void addItemExtra(Supplier<? extends Item> key, String name, String text) {
        add(key.get().getTranslationKey()+name, text);
    }
}
