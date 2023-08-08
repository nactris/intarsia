package dot.funky.intarsia;


import com.mojang.logging.LogUtils;
import dot.funky.intarsia.capabilities.CurioCompatibilityHandler;
import dot.funky.intarsia.client.models.AmethystGolemModel;
import dot.funky.intarsia.client.renderers.AmethystGolemRenderer;
import dot.funky.intarsia.common.EnchantementRegistry;
import dot.funky.intarsia.common.EntityRegistry;
import dot.funky.intarsia.common.casting.Patterns;
import dot.funky.intarsia.common.entities.AmethystGolem;
import dot.funky.intarsia.common.recipes.PhialFuseRecipe;
import dot.funky.intarsia.events.AmethystGolemEventHandler;
import dot.funky.intarsia.events.CurioKeyboardEventHandler;
import dot.funky.intarsia.events.EnchantmentsEventHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import thedarkcolour.kotlinforforge.KotlinModLoadingContext;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Intarsia.MODID)
public class Intarsia {

    public static final String MODID = "intarsia";
    public static final Logger LOGGER = LogUtils.getLogger();


    public Intarsia() {
        IEventBus modEventBus = getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, IntarsiaConfig.CONFIG_SPEC);


        if (ModList.get().isLoaded("curios")) {
            modEventBus.addListener(CurioCompatibilityHandler::IMCCurios);

          // modEventBus.add(CurioKeyboardEventHandler::registerKeyBindings); //moved to class
            modEventBus.register(CurioKeyboardEventHandler.class);

            MinecraftForge.EVENT_BUS.addListener(CurioCompatibilityHandler::onGrantAdvancement);
            modEventBus.addListener(CurioCompatibilityHandler::init);
            MinecraftForge.EVENT_BUS.register(CurioKeyboardEventHandler.class);
            MinecraftForge.EVENT_BUS.addListener(CurioCompatibilityHandler::clientTick); //moved to class


        }

        if (ModList.get().isLoaded("hexcasting")) {
            modEventBus.addListener((FMLCommonSetupEvent evt) -> evt.enqueueWork(Patterns::registerPatterns));
            //if (IntarsiaConfig.get().phial_phusion.get()) modEventBus.addListener(this::registerHexcastingRecipes);


        }


        modEventBus.addListener(this::bakeLayers);
        modEventBus.addListener(this::registerRenderers);
        modEventBus.addListener(this::registerAttributes);

        //BlockRegistry.BLOCKS.register(modEventBus);
        //ItemRegistry.ITEMS.register(modEventBus);
        EnchantementRegistry.ENCHANTMENTS.register(modEventBus);
        EntityRegistry.ENTITIES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(AmethystGolemEventHandler.class);
        MinecraftForge.EVENT_BUS.register(EnchantmentsEventHandler.class);


        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
    }


    private static IEventBus getModEventBus() {
        return KotlinModLoadingContext.Companion.get().getKEventBus();
    }

    private void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityRegistry.AMETHYST_GOLEM.get(), AmethystGolem.createAttributes().build());
    }

    private void bakeLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AmethystGolemModel.LAYER_LOCATION, AmethystGolemModel::createBodyLayer);
    }

    private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.AMETHYST_GOLEM.get(), AmethystGolemRenderer::new);
    }

    public void registerHexcastingRecipes(final FMLCommonSetupEvent evt) {
        PhialFuseRecipe.initAnvilRecipes();
    }


}

