package com.flanks255.simplyutilities.gui;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class ScrollingTextDisplay extends AbstractWidget {
    public ScrollingTextDisplay(int x, int y, int width, int height, Component title) {
        super(x, y, width, height, title);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (visible) {


            return true;
        }
        else
            return super.mouseScrolled(mouseX,mouseY,delta);
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {
        //Wut is this?
    }
}
