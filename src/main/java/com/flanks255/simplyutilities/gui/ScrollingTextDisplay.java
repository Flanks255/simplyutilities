package com.flanks255.simplyutilities.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;

public class ScrollingTextDisplay extends AbstractWidget {
    public ScrollingTextDisplay(int x, int y, int width, int height, Component title) {
        super(x, y, width, height, title);
    }

    @Override
    public void renderWidget(GuiGraphics p_268228_, int p_268034_, int p_268009_, float p_268085_) {

    }

    @Override
    protected void updateWidgetNarration(@Nonnull NarrationElementOutput pNarrationElementOutput) {

    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (visible) {


            return true;
        }
        else
            return super.mouseScrolled(mouseX,mouseY,scrollX, scrollY);
    }
}
