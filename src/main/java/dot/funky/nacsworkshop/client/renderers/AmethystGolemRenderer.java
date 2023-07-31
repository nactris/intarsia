package dot.funky.nacsworkshop.client.renderers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dot.funky.nacsworkshop.NacsWorkshop;
import dot.funky.nacsworkshop.client.models.AmethystGolemModel;
import dot.funky.nacsworkshop.common.entities.AmethystGolem;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;


public class AmethystGolemRenderer extends MobRenderer<AmethystGolem, AmethystGolemModel<AmethystGolem>> {


    public AmethystGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new AmethystGolemModel<>(context.bakeLayer(AmethystGolemModel.LAYER_LOCATION)), 1F);
        this.addLayer(new BudLayer(this,context.getModelSet()));
       // this.addLayer(new GlowLayer(this,context.getModelSet()));
        this.shadowRadius = 0.5F;
    }

    public ResourceLocation getTextureLocation(AmethystGolem entity) {
        return  entity.isSinging() ? new ResourceLocation(NacsWorkshop.MODID, "textures/entity/amethyst_golem/amethyst_golem_sing.png") : new ResourceLocation(NacsWorkshop.MODID, "textures/entity/amethyst_golem/amethyst_golem.png");
    }

    private static class BudLayer extends RenderLayer<AmethystGolem, AmethystGolemModel<AmethystGolem>>{
        private static final ResourceLocation[] TEXTURE_LOCATION = new ResourceLocation[]{new ResourceLocation(NacsWorkshop.MODID,"textures/entity/amethyst_golem/bud/small.png"), new ResourceLocation(NacsWorkshop.MODID,"textures/entity/amethyst_golem/bud/medium.png"), new ResourceLocation(NacsWorkshop.MODID,"textures/entity/amethyst_golem/bud/large.png"), new ResourceLocation(NacsWorkshop.MODID,"textures/entity/amethyst_golem/bud/full.png")};
        private final AmethystGolemModel<AmethystGolem> model;
        public BudLayer(RenderLayerParent<AmethystGolem, AmethystGolemModel<AmethystGolem>> parent, EntityModelSet modelSet) {
            super(parent);
            this.model = new AmethystGolemModel<>(modelSet.bakeLayer(AmethystGolemModel.LAYER_LOCATION));
        }
        @Override
        public void render(PoseStack pose, MultiBufferSource buffer, int light, AmethystGolem entity, float limbSwing, float limbSwingAmount, float p_117238_, float ageInTicks, float netHeadYaw, float headPitch) {
            int bud = entity.getBud();
            ResourceLocation resourcelocation;
            if (bud > 0 && bud < 5) {
                resourcelocation = TEXTURE_LOCATION[bud-1];
            } else {
                return;
            }
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(resourcelocation));
            this.model.renderToBuffer(pose, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        }
    }
/*

    private static class GlowLayer extends RenderLayer<AmethystGolem, AmethystGolemModel<AmethystGolem>>{
        private static final ResourceLocation[] TEXTURE_LOCATION = new ResourceLocation[]{new ResourceLocation(NacsWorkshop.MODID,"textures/entity/amethyst_golem/bud/small.png"), new ResourceLocation(NacsWorkshop.MODID,"textures/entity/amethyst_golem/bud/medium.png"), new ResourceLocation(NacsWorkshop.MODID,"textures/entity/amethyst_golem/bud/large.png"), new ResourceLocation(NacsWorkshop.MODID,"textures/entity/amethyst_golem/bud/full.png")};
        private final AmethystGolemModel<AmethystGolem> model;
        public GlowLayer(RenderLayerParent<AmethystGolem, AmethystGolemModel<AmethystGolem>> parent, EntityModelSet modelSet) {
            super(parent);
            this.model = new AmethystGolemModel<>(modelSet.bakeLayer(AmethystGolemModel.LAYER_LOCATION));
        }
        @Override
        public void render(PoseStack pose, MultiBufferSource buffer, int light, AmethystGolem entity, float limbSwing, float limbSwingAmount, float p_117238_, float ageInTicks, float netHeadYaw, float headPitch) {
            int bud = entity.getBud();
            ResourceLocation resourcelocation;
            if (bud > 0 && bud < 5) {
                resourcelocation = TEXTURE_LOCATION[bud-1];
            } else {
                return;
            }
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer glow = buffer.getBuffer(RenderType.entityTranslucentEmissive(resourcelocation));
            this.model.renderToBuffer(pose, glow, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        }
    }


 */

}
