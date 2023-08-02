package dot.funky.nacsworkshop.common;

import dot.funky.nacsworkshop.NacsWorkshop;
import dot.funky.nacsworkshop.common.enchantments.MercyCurseEnchantment;
import dot.funky.nacsworkshop.common.enchantments.TemperanceEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class EnchantementRegistry {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, NacsWorkshop.MODID);

    public static final RegistryObject<TemperanceEnchantment> TEMPERANCE = ENCHANTMENTS.register("temperance", TemperanceEnchantment::new);
    public static final RegistryObject<MercyCurseEnchantment> MERCY = ENCHANTMENTS.register("mercy_curse", MercyCurseEnchantment::new);




}
