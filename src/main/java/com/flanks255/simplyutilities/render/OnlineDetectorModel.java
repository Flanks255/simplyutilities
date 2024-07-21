package com.flanks255.simplyutilities.render;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class OnlineDetectorModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SimplyUtilities.MODID, "onlinedetector"), "main");
    public final ModelPart ArmAndStand;
    public final ModelPart Base;
    public final ModelPart Eye;
    public final ModelPart bone;
    public final ModelPart Ring1;
    public final ModelPart Ring2;

    public OnlineDetectorModel(ModelPart root) {
        this.ArmAndStand = root.getChild("ArmAndStand");
        this.Base = root.getChild("Base");
        this.Eye = root.getChild("Eye");
        this.bone = root.getChild("bone");
        this.Ring1 = root.getChild("Ring1");
        this.Ring2 = root.getChild("Ring2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition ArmAndStand = partdefinition.addOrReplaceChild("ArmAndStand", CubeListBuilder.create().texOffs(30, 30).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(0, 48).addBox(-1.0F, -10.0F, -8.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(42, 42).addBox(-1.0F, -8.0F, 0.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(36, 42).addBox(-1.0F, -10.0F, 4.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(26, 42).addBox(-1.0F, -8.0F, -6.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition ArmStand_r1 = ArmAndStand.addOrReplaceChild("ArmStand_r1", CubeListBuilder.create().texOffs(42, 0).addBox(-1.0F, -8.0F, 0.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(0, 21).addBox(-1.0F, -10.0F, 4.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(16, 40).addBox(-1.0F, -8.0F, -6.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0).addBox(-1.0F, -10.0F, -8.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition Base = partdefinition.addOrReplaceChild("Base", CubeListBuilder.create().texOffs(0, 28).addBox(-5.0F, -4.0F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(0, 15).addBox(-6.0F, -2.0F, -6.0F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0).addBox(-7.0F, -1.0F, -7.0F, 14.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition Eye = partdefinition.addOrReplaceChild("Eye", CubeListBuilder.create().texOffs(44, 24).addBox(-2.0F, -9.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition Eye_r1 = Eye.addOrReplaceChild("Eye_r1", CubeListBuilder.create().texOffs(0, 40).addBox(-2.0F, -13.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(36, 15).addBox(-3.0F, -15.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(42, 8).addBox(-2.0F, -16.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition Ring1 = partdefinition.addOrReplaceChild("Ring1", CubeListBuilder.create(), PartPose.offset(1.0F, 10.0F, 0.0F));

        PartDefinition bone_r1 = Ring1.addOrReplaceChild("bone_r1", CubeListBuilder.create().texOffs(0, 12).addBox(-7.0057F, -0.0086F, -2.8696F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0436F, 0.0F));

        PartDefinition bone_r2 = Ring1.addOrReplaceChild("bone_r2", CubeListBuilder.create().texOffs(0, 13).addBox(-7.1F, -0.0086F, -2.9161F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.8727F, 0.0F));

        PartDefinition bone_r3 = Ring1.addOrReplaceChild("bone_r3", CubeListBuilder.create().texOffs(0, 11).addBox(-7.1294F, -0.0086F, -3.017F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 1.4399F, 3.1416F));

        PartDefinition bone_r4 = Ring1.addOrReplaceChild("bone_r4", CubeListBuilder.create().texOffs(0, 7).addBox(-6.887F, -0.0086F, -3.0653F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition bone_r5 = Ring1.addOrReplaceChild("bone_r5", CubeListBuilder.create().texOffs(0, 10).addBox(-6.9717F, -0.0086F, -3.1274F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, -0.2182F, 3.1416F));

        PartDefinition bone_r6 = Ring1.addOrReplaceChild("bone_r6", CubeListBuilder.create().texOffs(0, 9).addBox(-7.0749F, -0.0086F, -3.1069F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.6109F, 3.1416F));

        PartDefinition bone_r7 = Ring1.addOrReplaceChild("bone_r7", CubeListBuilder.create().texOffs(0, 14).addBox(-6.9077F, -0.0086F, -2.9077F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition Ring2 = partdefinition.addOrReplaceChild("Ring2", CubeListBuilder.create(), PartPose.offset(-1.0F, 10.0F, 0.0F));

        PartDefinition bone_r8 = Ring2.addOrReplaceChild("bone_r8", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0057F, -0.0086F, -2.8696F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0436F, 0.0F));

        PartDefinition bone_r9 = Ring2.addOrReplaceChild("bone_r9", CubeListBuilder.create().texOffs(0, 1).addBox(-7.1F, -0.0086F, -2.9161F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.8727F, 0.0F));

        PartDefinition bone_r10 = Ring2.addOrReplaceChild("bone_r10", CubeListBuilder.create().texOffs(0, 2).addBox(-7.1294F, -0.0086F, -3.017F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 1.4399F, 3.1416F));

        PartDefinition bone_r11 = Ring2.addOrReplaceChild("bone_r11", CubeListBuilder.create().texOffs(0, 3).addBox(-6.887F, -0.0086F, -3.0653F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition bone_r12 = Ring2.addOrReplaceChild("bone_r12", CubeListBuilder.create().texOffs(0, 4).addBox(-6.9717F, -0.0086F, -3.1274F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, -0.2182F, 3.1416F));

        PartDefinition bone_r13 = Ring2.addOrReplaceChild("bone_r13", CubeListBuilder.create().texOffs(0, 5).addBox(-7.0749F, -0.0086F, -3.1069F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.6109F, 3.1416F));

        PartDefinition bone_r14 = Ring2.addOrReplaceChild("bone_r14", CubeListBuilder.create().texOffs(0, 6).addBox(-6.9077F, -0.0086F, -2.9077F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        ArmAndStand.render(poseStack, buffer, packedLight, packedOverlay);
        Base.render(poseStack, buffer, packedLight, packedOverlay);
        Eye.render(poseStack, buffer, packedLight, packedOverlay);
        bone.render(poseStack, buffer, packedLight, packedOverlay);
        Ring1.render(poseStack, buffer, packedLight, packedOverlay);
        Ring2.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
