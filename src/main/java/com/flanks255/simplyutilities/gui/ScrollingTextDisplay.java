package com.flanks255.simplyutilities.gui;

import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;

public class ScrollingTextDisplay extends Widget {
    public ScrollingTextDisplay(int x, int y, int width, int height, ITextComponent title) {
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
}
