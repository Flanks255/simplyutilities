package com.flanks255.simplyutilities.commands.debug;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DebugScreen extends Screen {
    public DebugScreen(ItemStack serverStack) {
        super(new StringTextComponent("SU Debug Screen")); stack = serverStack;
    }

    ItemStack stack;

    enum viewTab {
        BASIC,
        TAGS,
        NBT,
        CLASSES
    }
    viewTab currentTab = viewTab.BASIC;

    private ResourceLocation GUI = new ResourceLocation(SimplyUtilities.MODID, "textures/gui/debughand.png");
    private int guiLeft;
    private int guiTop;
    private int xSize = 256;
    private int ySize = 220;


    @Override
    protected void init() {
        super.init();

        guiLeft = (width - xSize) / 2;
        guiTop = (height - ySize) / 2;

        Button.IPressable tabClick = new Button.IPressable() {
            @Override
            public void onPress(Button p_onPress_1_) {
                ((ViewButton)p_onPress_1_).clickTab();
            }
        };

        addButton(new ViewButton(this,guiLeft + 20, guiTop + 3, 56, 10, new StringTextComponent("Basic"), viewTab.BASIC, tabClick));
        addButton(new ViewButton(this,guiLeft + 76, guiTop + 3, 56, 10, new StringTextComponent("Tags"), viewTab.TAGS, tabClick));
        addButton(new ViewButton(this,guiLeft + 132, guiTop + 3, 56, 10, new StringTextComponent("NBT Data"), viewTab.NBT, tabClick));
        addButton(new ViewButton(this,guiLeft + 188, guiTop + 3, 56, 10, new StringTextComponent("Class info"), viewTab.CLASSES, tabClick));
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void renderBackground(MatrixStack matrixStack) {
        super.renderBackground(matrixStack);

        this.getMinecraft().textureManager.bindTexture(GUI);
        blit(matrixStack, guiLeft, guiTop, 0,0, 256, 220, 256, 256);

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);

        children.forEach((child) -> {
            if (child instanceof Widget)
                ((Widget) child).render(matrixStack, mouseX, mouseY, partialTicks);
        });

        switch (currentTab) {
            case BASIC:
                drawString(matrixStack, font, "Display Name: " + stack.getDisplayName().getString() ,guiLeft + 8, guiTop + 24, 0xFFFFFF);
                drawString(matrixStack, font,"Registry Name: " + stack.getItem().getRegistryName() ,guiLeft + 8, guiTop + 34, 0xFFFFFF);
                break;
            case TAGS:
                drawString(matrixStack, font, "Tags: " + stack.getItem().getTags() ,guiLeft + 8, guiTop + 24, 0xFFFFFF);
                break;
            case NBT:
                if (stack.hasTag()) {

                    String lineList[] = stack.getTag().toFormattedComponent(" ", 1).getString().split("\n");
                    int y = guiTop + 24;
                    for (String line : lineList) {
                        drawString(matrixStack, font,new StringTextComponent(line), guiLeft + 8, y, 0xFFFFFF);
                        y += 10;
                    }

                }
                else
                    drawString(matrixStack, font, "NBT: None" ,guiLeft + 8, guiTop + 24, 0xFFFFFF);
                break;
            case CLASSES:
                break;
        }


        //draw the item
        matrixStack.push();
        RenderSystem.enableDepthTest();
        RenderHelper.enableStandardItemLighting();
        itemRenderer.renderItemAndEffectIntoGUI(stack, guiLeft + 3,guiTop + 3);
        RenderHelper.disableStandardItemLighting();
        matrixStack.pop();
    }

    class ViewButton extends Button {
        public ViewButton(DebugScreen parentIn, int x, int y, int width, int height, ITextComponent title, viewTab tab, IPressable action) {
            super(x, y, width, height, title, action);
            parent = parentIn;
            myTab = tab;
        }
        viewTab myTab;
        DebugScreen parent;

        public void clickTab() {
            currentTab = myTab;
        }

        @Override
        public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
            getMinecraft().textureManager.bindTexture(GUI);
            if (parent.currentTab == myTab)
                blit(matrixStack, x, y, 0, 232, 56, 12, 256 ,256);
            else
                blit(matrixStack, x, y, 0, 220, 56, 12, 256 ,256);

            drawCenteredString(matrixStack, font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 6) / 2, 0xFFFFFF);
        }
    }
}
