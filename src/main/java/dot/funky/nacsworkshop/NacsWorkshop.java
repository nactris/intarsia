package dot.funky.nacsworkshop;


import com.mojang.logging.LogUtils;
import dot.funky.nacsworkshop.capabilities.CurioCompatibilityHandler;
import dot.funky.nacsworkshop.client.models.AmethystGolemModel;
import dot.funky.nacsworkshop.client.renderers.AmethystGolemRenderer;
import dot.funky.nacsworkshop.common.entities.AmethystGolem;
import dot.funky.nacsworkshop.core.NacEntities;
import dot.funky.nacsworkshop.events.AmethystGolemEventHandler;
import dot.funky.nacsworkshop.events.CurioKeyboardEventHandler;
import dot.funky.nacsworkshop.recipes.PhialFuseRecipe;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(NacsWorkshop.MODID)
public class NacsWorkshop {

    public static final String MODID = "nacsworkshop";
    public static final Logger LOGGER = LogUtils.getLogger();


    public NacsWorkshop() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        if (ModList.get().isLoaded("curios")) {
            modEventBus.addListener(CurioCompatibilityHandler::IMCCurios);
            modEventBus.addListener(CurioKeyboardEventHandler::registerKeyBindings);
            MinecraftForge.EVENT_BUS.addListener(CurioCompatibilityHandler::onGrantAdvancement);
            modEventBus.addListener(CurioCompatibilityHandler::commonCurioSetup);
            MinecraftForge.EVENT_BUS.register(CurioKeyboardEventHandler.class);
            MinecraftForge.EVENT_BUS.addListener(CurioCompatibilityHandler::clientTick);

        }

        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::bakeLayers);
        //   NacsBlocks.BLOCKS.register(modEventBus);
        //    NacItems.ITEMS.register(modEventBus);
        NacEntities.ENTITIES.register(modEventBus);
        modEventBus.addListener(this::registerRenderers);
        modEventBus.addListener(this::registerAttributes);
        MinecraftForge.EVENT_BUS.register(AmethystGolemEventHandler.class);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
    }



    private void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(NacEntities.AMETHYST_GOLEM.get(), AmethystGolem.createAttributes().build());
    }

    private void bakeLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AmethystGolemModel.LAYER_LOCATION, AmethystGolemModel::createBodyLayer);
    }




    private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(NacEntities.AMETHYST_GOLEM.get(), AmethystGolemRenderer::new);
    }

public void onCommonSetup(final FMLCommonSetupEvent evt){
    PhialFuseRecipe.initAnvilRecipes();
}

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

}

