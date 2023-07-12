package com.flanks255.simplyutilities.commands.debug;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class DebugScreen extends Screen {
    public DebugScreen(ItemStack serverStack) {
        super(Component.literal("SU Debug Screen")); stack = serverStack;
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

        Button.OnPress tabClick = button -> ((ViewButton)button).clickTab();

        addWidget(new ViewButton(this,guiLeft + 20, guiTop + 3, 56, 10, Component.literal("Basic"), viewTab.BASIC, tabClick));
        addWidget(new ViewButton(this,guiLeft + 76, guiTop + 3, 56, 10, Component.literal("Tags"), viewTab.TAGS, tabClick));
        addWidget(new ViewButton(this,guiLeft + 132, guiTop + 3, 56, 10, Component.literal("NBT Data"), viewTab.NBT, tabClick));
        addWidget(new ViewButton(this,guiLeft + 188, guiTop + 3, 56, 10, Component.literal("Class info"), viewTab.CLASSES, tabClick));
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void renderBackground(GuiGraphics gg) {
        super.renderBackground(gg);

        gg.blit(GUI, guiLeft, guiTop, 0,0, 256, 220, 256, 256);

    }

    @Override
    public void render(GuiGraphics gg, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(gg);

        children().forEach((child) -> {
            if (child instanceof AbstractWidget)
                ((AbstractWidget) child).render(gg, mouseX, mouseY, partialTicks);
        });

        switch (currentTab) {
            case BASIC:
                gg.drawString(font, "Display Name: " + stack.getHoverName().getString() ,guiLeft + 8, guiTop + 24, 0xFFFFFF);
                gg.drawString(font,"Registry Name: " + ForgeRegistries.ITEMS.getKey(stack.getItem()) ,guiLeft + 8, guiTop + 34, 0xFFFFFF);
                break;
            case TAGS:
                var tags = stack.getTags().toList();
                int y = guiTop + 24;
                for (TagKey<Item> tag : tags) {
                    gg.drawString(font, tag.location().toString() ,guiLeft + 8, y, 0xFFFFFF);
                    y+=10;
                }
                break;
            case NBT:
                if (stack.hasTag()) {
/*                    String lineList[] = stack.getTag().getPrettyDisplay(" ", 1).getString().split("\n");
                    int y = guiTop + 24;
                    for (String line : lineList) {
                        drawString(matrixStack, font,new TextComponent(line), guiLeft + 8, y, 0xFFFFFF);
                        y += 10;
                    }*/
                }
                else
                    gg.drawString(font, "NBT: None" ,guiLeft + 8, guiTop + 24, 0xFFFFFF);
                break;
            case CLASSES:
                break;
        }


        //draw the item
        gg.pose().pushPose();
        RenderSystem.enableDepthTest();
        //Lighting.turnBackOn();
        gg.renderItem(stack, guiLeft + 3,guiTop + 3);
        //Lighting.turnOff();
        gg.pose().popPose();
    }

    class ViewButton extends Button {
        public ViewButton(DebugScreen parentIn, int x, int y, int width, int height, Component title, viewTab tab, OnPress action) {
            super(x, y, width, height, title, action, DEFAULT_NARRATION);
            parent = parentIn;
            myTab = tab;
        }
        viewTab myTab;
        DebugScreen parent;

        public void clickTab() {
            currentTab = myTab;
        }

        @Override
        public void renderWidget(GuiGraphics gg, int mouseX, int mouseY, float partialTicks) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, GUI);
            if (parent.currentTab == myTab)
                gg.blit(GUI, getX(), getY(), 0, 232, 56, 12, 256 ,256);
            else
                gg.blit(GUI, getX(), getY(), 0, 220, 56, 12, 256 ,256);

            gg.drawCenteredString(font, this.getMessage(), getX() + this.width / 2, getY() + (this.height - 6) / 2, 0xFFFFFF);
        }
    }
}
