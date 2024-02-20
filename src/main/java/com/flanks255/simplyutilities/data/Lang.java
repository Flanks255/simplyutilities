package com.flanks255.simplyutilities.data;

import com.flanks255.simplyutilities.SUBlocks;
import com.flanks255.simplyutilities.SUItems;
import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.function.Supplier;

public class Lang extends LanguageProvider {
    public Lang(DataGenerator gen) {
        super(gen.getPackOutput(), SimplyUtilities.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("key.simplyutilities.category", "Simply Utilities");

        add("su.moreinfo" ,"Press <%s> for info.");
        add("su.key.shift", "Shift");

        add("message.su.sethome", "Set new home point (%s)");
        add("message.su.home", "Warping to (%s)");
        add("message.su.maxhomes", "Unable to set new home, max %d homes allowed.");
        add("message.su.removehomesuccess", "Successfully removed home (%s)");
        add("message.su.removehomefail", "Failed to removed home (%s)");

        add("message.su.debug.hand.noitem", "No held item to debug.");

        add("key.simplyutilities.zoom.desc", "Zoom");
        add("message.su.zoom.setfov", "Setting zoom FOV to %d");
        add("message.su.zoom.setsmooth", "Setting zoom smoothcam to %s");

        add("message.su.inhibitororphans", "Removed %d orphaned inhibitors in this dimension.");

        addBlock(SUBlocks.ENDER_INHIBITOR, "Ender Inhibitor");
        addBlock(SUBlocks.ONLINE_DETECTOR, "Online Detector");
        addBlock(SUBlocks.CHARCOAL_BLOCK, "Block of Charcoal");
        addBlock(SUBlocks.ENDER_PEARL_BLOCK, "Block of Ender Pearls");
        addBlockExtra(SUBlocks.ENDER_INHIBITOR, ".info", "Prevents Endermen around it from teleporting.");
        addBlockExtra(SUBlocks.ONLINE_DETECTOR, ".info", "Emits a redstone signal when the player who placed the block is online.");

        addItem(SUItems.EXOLEGGINGS, "Exoskeleton Leggings");
        addItemExtra(SUItems.EXOLEGGINGS, ".info", "Prevents fall damage.");
        addItem(SUItems.MINICOAL, "Mini-coal");
        addItemExtra(SUItems.MINICOAL, ".info", "smelts 1 item.");
        addItemExtra(SUItems.MINICHARCOAL, ".info", "smelts 1 item.");
        addItem(SUItems.MINICHARCOAL, "Mini-charcoal");

        addJEITabBlock(SUBlocks.ONLINE_DETECTOR, "Outputs a redstone signal\nwhen the player who placed\nthe block is online.");
    }

    private void addBlockExtra(Supplier<? extends Block> key, String name, String text) {
        add(key.get().getDescriptionId()+name, text);
    }

    private void addItemExtra(Supplier<? extends Item> key, String name, String text) {
        add(key.get().getDescriptionId()+name, text);
    }

    private void addJEITab(Supplier<? extends Item> item, String text) {
        add(item.get().getDescriptionId()+".jei.info", text);
    }

    private void addJEITabBlock(Supplier<? extends Block> item, String text) {
        add(item.get().asItem().getDescriptionId()+".jei.info", text);
    }
}
