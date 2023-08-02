package dot.funky.nacsworkshop.common;

import dot.funky.nacsworkshop.NacsWorkshop;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NacsWorkshop.MODID);
   // public static final RegistryObject<Block> MOD_BLOCK = BLOCKS.register("test",() -> new Block( BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).sound(SoundType.DEEPSLATE_TILES).strength(4.0F, 4.0F)));
}
