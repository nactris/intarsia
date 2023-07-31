package dot.funky.nacsworkshop.core;

import dot.funky.nacsworkshop.NacsWorkshop;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class NacItems {

     public static final CreativeModeTab TAB = new CreativeModeTab(NacsWorkshop.MODID) {
        @Override
        public ItemStack makeIcon() {
            return Items.BUDDING_AMETHYST.getDefaultInstance();
        }
    };
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NacsWorkshop.MODID);
    //public static final RegistryObject<Item> MOD_BLOCK = ITEMS.register("test", () -> new BlockItem(NacsBlocks.MOD_BLOCK.get(), new Item.Properties().tab(TAB)));
    //public static final RegistryObject<Item> SPAWN_EGG = ITEMS.register("spawn_egg", ()-> new SpawnEggItem(NacEntities.AMETHYST_GOLEM.get(), new Color(79, 75, 79).getRGB(),new Color(166, 120, 241).getRGB(), new Item.Properties().tab(TAB)));

}
