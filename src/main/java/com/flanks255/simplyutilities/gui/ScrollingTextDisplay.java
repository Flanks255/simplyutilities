package com.flanks255.simplyutilities.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;

public class ScrollingTextDisplay extends AbstractWidget {
    public ScrollingTextDisplay(int x, int y, int width, int height, Component title) {
        super(x, y, width, height, title);
    }

    @Override
    public void renderWidget(PoseStack p_268228_, int p_268034_, int p_268009_, float p_268085_) {

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
