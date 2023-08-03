package dot.funky.intarsia.client.models;// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dot.funky.intarsia.client.animations.AmethystGolemAnimation;
import dot.funky.intarsia.common.entities.AmethystGolem;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;


// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class AmethystGolemModel<T extends AmethystGolem> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "amethystgolem"), "main");
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart left_back_leg;
    private final ModelPart left_front_leg;
    private final ModelPart right_front_leg;
    private final ModelPart right_back_leg;
    private final ModelPart bud;
    private final ModelPart base;


    public AmethystGolemModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.base = body.getChild("base");
        this.head = this.body.getChild("head");
        this.left_back_leg = this.base.getChild("left_back_leg");
        this.left_front_leg = this.base.getChild("left_front_leg");
        this.right_front_leg = this.base.getChild("right_front_leg");
        this.right_back_leg = this.base.getChild("right_back_leg");
        this.bud = this.head.getChild("bud");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 0.0F));

        PartDefinition base = body.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, -6.7F, -5.5F, 11.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition left_front_leg = base.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 56).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 0.0F, -5.0F));

        PartDefinition right_back_leg = base.addOrReplaceChild("right_back_leg", CubeListBuilder.create().texOffs(48, 56).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 0.0F, 5.0F));

        PartDefinition left_back_leg = base.addOrReplaceChild("left_back_leg", CubeListBuilder.create().texOffs(48, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 0.0F, 5.0F));

        PartDefinition right_front_leg = base.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 0.0F, -5.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 48).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition bud = head.addOrReplaceChild("bud", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition crystal_left_r1 = bud.addOrReplaceChild("crystal_left_r1", CubeListBuilder.create().texOffs(16, 16).mirror().addBox(0.0F, -16.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition crystal_right_r1 = bud.addOrReplaceChild("crystal_right_r1", CubeListBuilder.create().texOffs(16, 16).addBox(0.0F, -16.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 80);
    }
    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.body.getAllParts().forEach(ModelPart::resetPose);


        this.bud.xScale = (14F / 16F) * Mth.sqrt(2);
        this.bud.yScale = Mth.sqrt(2);
        this.bud.zScale = (14F / 16F) * Mth.sqrt(2);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.z = -this.head.xRot*7 ;
        this.body.yRot = netHeadYaw * ((float) Math.PI / 180F);

        this.head.zRot = Mth.cos(limbSwing * 0.6662F) * -0.2F * limbSwingAmount;

        this.right_back_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.left_back_leg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.right_front_leg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.left_front_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        this.animate(entity.danceAnimationState, AmethystGolemAnimation.DANCE, ageInTicks);
        this.animate(entity.sitAnimationState, AmethystGolemAnimation.SIT, ageInTicks);
        this.animate(entity.standAnimationState, AmethystGolemAnimation.STAND, ageInTicks);
        this.animate(entity.singAnimationState, AmethystGolemAnimation.SWING, ageInTicks);


    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);

    }

    @Override
    public ModelPart root() {
        return this.root;
    }


}