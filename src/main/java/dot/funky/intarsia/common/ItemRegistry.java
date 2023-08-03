package dot.funky.intarsia.common;

import dot.funky.intarsia.Intarsia;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {

     public static final CreativeModeTab TAB = new CreativeModeTab(Intarsia.MODID) {
        @Override
        public ItemStack makeIcon() {
            return Items.BUDDING_AMETHYST.getDefaultInstance();
        }
    };
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Intarsia.MODID);
    //public static final RegistryObject<Item> MOD_BLOCK = ITEMS.register("test", () -> new BlockItem(NacsBlocks.MOD_BLOCK.get(), new Item.Properties().tab(TAB)));
    //public static final RegistryObject<Item> SPAWN_EGG = ITEMS.register("spawn_egg", ()-> new SpawnEggItem(NacEntities.AMETHYST_GOLEM.get(), new Color(79, 75, 79).getRGB(),new Color(166, 120, 241).getRGB(), new Item.Properties().tab(TAB)));

}
