package com.flanks255.simplyutilities.gui;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;

public class ScrollingTextDisplay extends AbstractWidget {
    public ScrollingTextDisplay(int x, int y, int width, int height, Component title) {
        super(x, y, width, height, title);
    }

    @Override
    protected void updateWidgetNarration(@Nonnull NarrationElementOutput pNarrationElementOutput) {

    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (visible) {


            return true;
        }
        else
            return super.mouseScrolled(mouseX,mouseY,delta);
    }
}
