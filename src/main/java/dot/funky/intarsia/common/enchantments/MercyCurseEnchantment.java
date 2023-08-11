package dot.funky.intarsia.common.enchantments;

import dot.funky.intarsia.common.EnchantementRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class MercyCurseEnchantment extends Enchantment {



    public MercyCurseEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND,EquipmentSlot.FEET, EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS , EquipmentSlot.OFFHAND});
    }

    public void preventKill(LivingHurtEvent event) {
        Entity ent = event.getSource().getEntity();
        if (ent instanceof LivingEntity && EnchantmentHelper.getEnchantmentLevel(EnchantementRegistry.MERCY.get(), (LivingEntity) ent) > 0) {
            if (event.getEntity().getHealth() <= event.getAmount()) {
                event.setAmount(event.getEntity().getHealth() - 1);
            }
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }


}
