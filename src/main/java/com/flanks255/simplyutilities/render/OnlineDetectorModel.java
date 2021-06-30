package com.flanks255.simplyutilities.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class OnlineDetectorModel extends EntityModel<Entity> {
    public ModelRenderer ArmAndStand;
    private ModelRenderer ArmStand_r1;
    public ModelRenderer Base;
    public ModelRenderer Eye;
    private ModelRenderer Eye_r1;
    public ModelRenderer bone;
    public ModelRenderer Ring1;
    private ModelRenderer bone_r1;
    private ModelRenderer bone_r2;
    private ModelRenderer bone_r3;
    private ModelRenderer bone_r4;
    private ModelRenderer bone_r5;
    private ModelRenderer bone_r6;
    private ModelRenderer bone_r7;
    public ModelRenderer Ring2;
    private ModelRenderer bone_r8;
    private ModelRenderer bone_r9;
    private ModelRenderer bone_r10;
    private ModelRenderer bone_r11;
    private ModelRenderer bone_r12;
    private ModelRenderer bone_r13;
    private ModelRenderer bone_r14;

    public OnlineDetectorModel() {
        textureWidth = 128;
        textureHeight = 128;

        ArmAndStand = new ModelRenderer(this);
        ArmAndStand.setRotationPoint(0.0F, 24.0F, 0.0F);
        ArmAndStand.setTextureOffset(30, 30).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 2.0F, 10.0F, 0.0F, false);
        ArmAndStand.setTextureOffset(0, 48).addBox(-1.0F, -10.0F, -8.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
        ArmAndStand.setTextureOffset(42, 42).addBox(-1.0F, -8.0F, 0.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
        ArmAndStand.setTextureOffset(36, 42).addBox(-1.0F, -10.0F, 4.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
        ArmAndStand.setTextureOffset(26, 42).addBox(-1.0F, -8.0F, -6.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);

        ArmStand_r1 = new ModelRenderer(this);
        ArmStand_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        ArmAndStand.addChild(ArmStand_r1);
        setRotationAngle(ArmStand_r1, 0.0F, 1.5708F, 0.0F);
        ArmStand_r1.setTextureOffset(42, 0).addBox(-1.0F, -8.0F, 0.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
        ArmStand_r1.setTextureOffset(0, 21).addBox(-1.0F, -10.0F, 4.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
        ArmStand_r1.setTextureOffset(16, 40).addBox(-1.0F, -8.0F, -6.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
        ArmStand_r1.setTextureOffset(0, 0).addBox(-1.0F, -10.0F, -8.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);

        Base = new ModelRenderer(this);
        Base.setRotationPoint(0.0F, 24.0F, 0.0F);
        Base.setTextureOffset(0, 28).addBox(-5.0F, -4.0F, -5.0F, 10.0F, 2.0F, 10.0F, 0.0F, false);
        Base.setTextureOffset(0, 15).addBox(-6.0F, -2.0F, -6.0F, 12.0F, 1.0F, 12.0F, 0.0F, false);
        Base.setTextureOffset(0, 0).addBox(-7.0F, -1.0F, -7.0F, 14.0F, 1.0F, 14.0F, 0.0F, false);

        Eye = new ModelRenderer(this);
        Eye.setRotationPoint(0.0F, 24.0F, 0.0F);
        Eye.setTextureOffset(44, 24).addBox(-2.0F, -9.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);

        Eye_r1 = new ModelRenderer(this);
        Eye_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        Eye.addChild(Eye_r1);
        setRotationAngle(Eye_r1, 0.0F, 0.7854F, 0.0F);
        Eye_r1.setTextureOffset(0, 40).addBox(-2.0F, -13.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 24.0F, 0.0F);
        bone.setTextureOffset(36, 15).addBox(-3.0F, -15.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, false);
        bone.setTextureOffset(42, 8).addBox(-2.0F, -16.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);

        Ring1 = new ModelRenderer(this);
        Ring1.setRotationPoint(0.0f, 10.0F, 0.0F);
        //setRotationAngle(Ring1, -0.1309F, 0.0F, 0.0F);
        setRotationAngle(Ring1, 0, 0.0F, 0.0F);


        bone_r1 = new ModelRenderer(this);
        bone_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        Ring1.addChild(bone_r1);
        setRotationAngle(bone_r1, 0.0F, 0.0436F, 0.0F);
        bone_r1.setTextureOffset(0, 12).addBox(-7.0057F, -0.0086F, -2.8696F, 0.0F, 1.0F, 6.0F, 0.0F, false);

        bone_r2 = new ModelRenderer(this);
        bone_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
        Ring1.addChild(bone_r2);
        setRotationAngle(bone_r2, 0.0F, 0.8727F, 0.0F);
        bone_r2.setTextureOffset(0, 13).addBox(-7.1F, -0.0086F, -2.9161F, 0.0F, 1.0F, 6.0F, 0.0F, false);

        bone_r3 = new ModelRenderer(this);
        bone_r3.setRotationPoint(0.0F, 0.0F, 0.0F);
        Ring1.addChild(bone_r3);
        setRotationAngle(bone_r3, -3.1416F, 1.4399F, 3.1416F);
        bone_r3.setTextureOffset(0, 11).addBox(-7.1294F, -0.0086F, -3.017F, 0.0F, 1.0F, 6.0F, 0.0F, false);

        bone_r4 = new ModelRenderer(this);
        bone_r4.setRotationPoint(0.0F, 0.0F, 0.0F);
        Ring1.addChild(bone_r4);
        setRotationAngle(bone_r4, -3.1416F, -1.0472F, 3.1416F);
        bone_r4.setTextureOffset(0, 7).addBox(-6.887F, -0.0086F, -3.0653F, 0.0F, 1.0F, 6.0F, 0.0F, false);

        bone_r5 = new ModelRenderer(this);
        bone_r5.setRotationPoint(0.0F, 0.0F, 0.0F);
        Ring1.addChild(bone_r5);
        setRotationAngle(bone_r5, -3.1416F, -0.2182F, 3.1416F);
        bone_r5.setTextureOffset(0, 10).addBox(-6.9717F, -0.0086F, -3.1274F, 0.0F, 1.0F, 6.0F, 0.0F, false);

        bone_r6 = new ModelRenderer(this);
        bone_r6.setRotationPoint(0.0F, 0.0F, 0.0F);
        Ring1.addChild(bone_r6);
        setRotationAngle(bone_r6, -3.1416F, 0.6109F, 3.1416F);
        bone_r6.setTextureOffset(0, 9).addBox(-7.0749F, -0.0086F, -3.1069F, 0.0F, 1.0F, 6.0F, 0.0F, false);

        bone_r7 = new ModelRenderer(this);
        bone_r7.setRotationPoint(0.0F, 0.0F, 0.0F);
        Ring1.addChild(bone_r7);
        setRotationAngle(bone_r7, 0.0F, -0.7854F, 0.0F);
        bone_r7.setTextureOffset(0, 14).addBox(-6.9077F, -0.0086F, -2.9077F, 0.0F, 1.0F, 6.0F, 0.0F, false);

        Ring2 = new ModelRenderer(this);
        Ring2.setRotationPoint(0.0f, 10.0F, 0.0F);
        //setRotationAngle(Ring2, -0.1309F, 3.1416F, 0.0F);
        setRotationAngle(Ring2, 0, 0, 0.0F);


        bone_r8 = new ModelRenderer(this);
        bone_r8.setRotationPoint(0.0F, 0.0F, 0.0F);
        Ring2.addChild(bone_r8);
        setRotationAngle(bone_r8, 0.0F, 0.0436F, 0.0F);
        bone_r8.setTextureOffset(0, 0).addBox(-7.0057F, -0.0086F, -2.8696F, 0.0F, 1.0F, 6.0F, 0.0F, false);

        bone_r9 = new ModelRenderer(this);
        bone_r9.setRotationPoint(0.0F, 0.0F, 0.0F);
        Ring2.addChild(bone_r9);
        setRotationAngle(bone_r9, 0.0F, 0.8727F, 0.0F);
        bone_r9.setTextureOffset(0, 1).addBox(-7.1F, -0.0086F, -2.9161F, 0.0F, 1.0F, 6.0F, 0.0F, false);

        bone_r10 = new ModelRenderer(this);
        bone_r10.setRotationPoint(0.0F, 0.0F, 0.0F);
        Ring2.addChild(bone_r10);
        setRotationAngle(bone_r10, -3.1416F, 1.4399F, 3.1416F);
        bone_r10.setTextureOffset(0, 2).addBox(-7.1294F, -0.0086F, -3.017F, 0.0F, 1.0F, 6.0F, 0.0F, false);

        bone_r11 = new ModelRenderer(this);
        bone_r11.setRotationPoint(0.0F, 0.0F, 0.0F);
        Ring2.addChild(bone_r11);
        setRotationAngle(bone_r11, -3.1416F, -1.0472F, 3.1416F);
        bone_r11.setTextureOffset(0, 3).addBox(-6.887F, -0.0086F, -3.0653F, 0.0F, 1.0F, 6.0F, 0.0F, false);

        bone_r12 = new ModelRenderer(this);
        bone_r12.setRotationPoint(0.0F, 0.0F, 0.0F);
        Ring2.addChild(bone_r12);
        setRotationAngle(bone_r12, -3.1416F, -0.2182F, 3.1416F);
        bone_r12.setTextureOffset(0, 4).addBox(-6.9717F, -0.0086F, -3.1274F, 0.0F, 1.0F, 6.0F, 0.0F, false);

        bone_r13 = new ModelRenderer(this);
        bone_r13.setRotationPoint(0.0F, 0.0F, 0.0F);
        Ring2.addChild(bone_r13);
        setRotationAngle(bone_r13, -3.1416F, 0.6109F, 3.1416F);
        bone_r13.setTextureOffset(0, 5).addBox(-7.0749F, -0.0086F, -3.1069F, 0.0F, 1.0F, 6.0F, 0.0F, false);

        bone_r14 = new ModelRenderer(this);
        bone_r14.setRotationPoint(0.0F, 0.0F, 0.0F);
        Ring2.addChild(bone_r14);
        setRotationAngle(bone_r14, 0.0F, -0.7854F, 0.0F);
        bone_r14.setTextureOffset(0, 6).addBox(-6.9077F, -0.0086F, -2.9077F, 0.0F, 1.0F, 6.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        ArmAndStand.render(matrixStack, buffer, packedLight, packedOverlay);
        Base.render(matrixStack, buffer, packedLight, packedOverlay);
        Eye.render(matrixStack, buffer, packedLight, packedOverlay);
        bone.render(matrixStack, buffer, packedLight, packedOverlay);
        Ring1.render(matrixStack, buffer, packedLight, packedOverlay);
        Ring2.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
