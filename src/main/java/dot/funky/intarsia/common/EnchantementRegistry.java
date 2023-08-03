package dot.funky.intarsia.common;

import dot.funky.intarsia.Intarsia;
import dot.funky.intarsia.common.enchantments.MercyCurseEnchantment;
import dot.funky.intarsia.common.enchantments.TemperanceEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class EnchantementRegistry {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Intarsia.MODID);

    public static final RegistryObject<TemperanceEnchantment> TEMPERANCE = ENCHANTMENTS.register("temperance", TemperanceEnchantment::new);
    public static final RegistryObject<MercyCurseEnchantment> MERCY = ENCHANTMENTS.register("mercy_curse", MercyCurseEnchantment::new);




}
