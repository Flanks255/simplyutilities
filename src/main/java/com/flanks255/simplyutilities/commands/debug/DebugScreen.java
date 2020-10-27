package com.flanks255.simplyutilities.commands.debug;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DebugScreen extends Screen {
    public DebugScreen(ItemStack serverStack) {
        super(new StringTextComponent("SU Debug Screen"));
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
